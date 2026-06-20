package com.gamebuilder.ai;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class OllamaClient {

    private static final Logger LOG = Logger.getLogger(OllamaClient.class);

    private final String host;
    private final String apiKey;
    private final String model;
    private final long cooldownMs;
    private final int requestCap;
    private final HttpClient httpClient;

    private final AtomicLong lastRequestTimestamp = new AtomicLong(0);
    private final AtomicInteger successfulRequestCounter = new AtomicInteger(0);

    @Inject
    public OllamaClient(
            @ConfigProperty(name = "ollama.host") String host,
            @ConfigProperty(name = "ollama.api.key") String apiKey,
            @ConfigProperty(name = "ollama.model") String model,
            @ConfigProperty(name = "ollama.cooldown.ms", defaultValue = "5000") long cooldownMs,
            @ConfigProperty(name = "ollama.request.cap", defaultValue = "2000") int requestCap) {
        this.host = host;
        this.apiKey = apiKey;
        this.model = model;
        this.cooldownMs = cooldownMs;
        this.requestCap = requestCap;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String chat(String prompt) {
        // Cooldown guard
        long now = System.currentTimeMillis();
        long last = lastRequestTimestamp.get();
        if (last > 0 && (now - last) < cooldownMs) {
            long remaining = cooldownMs - (now - last);
            LOG.warnf("Request rejected: cooldown active, %dms remaining", remaining);
            return "Request rejected: cooldown active. Wait " + remaining + "ms before retrying.";
        }

        // Request cap guard
        if (requestCap > 0 && successfulRequestCounter.get() >= requestCap) {
            LOG.warnf("Request rejected: request cap reached (%d)", requestCap);
            return "Request rejected: request cap of " + requestCap + " has been reached.";
        }

        // Mark the request timestamp before sending
        lastRequestTimestamp.set(now);

        String url = host + "/v1/chat/completions";
        String body = """
                {
                    "model": "%s",
                    "messages": [
                        {"role": "user", "content": "%s"}
                    ],
                    "stream": false
                }
                """.formatted(model, escapeJson(prompt));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(120))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        LOG.infof("Ollama request to %s model=%s", url, model);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            LOG.infof("Ollama response status=%d", response.statusCode());
            if (response.statusCode() == 200) {
                successfulRequestCounter.incrementAndGet();
                return extractContent(response.body());
            }
            return "Ollama error (HTTP " + response.statusCode() + "): " + response.body();
        } catch (IOException | InterruptedException e) {
            LOG.error("Ollama request failed", e);
            return "Ollama request failed: " + e.getMessage();
        }
    }

    private String extractContent(String responseBody) {
        // OpenAI-style response: {"choices":[{"message":{"content":"..."}}]}
        try {
            int contentIdx = responseBody.indexOf("\"content\":\"");
            if (contentIdx < 0) return responseBody;
            int start = contentIdx + 11;
            int end = start;
            while (end < responseBody.length()) {
                char c = responseBody.charAt(end);
                if (c == '"' && (end == start || responseBody.charAt(end - 1) != '\\')) {
                    break;
                }
                end++;
            }
            return responseBody.substring(start, end)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\");
        } catch (Exception e) {
            return responseBody;
        }
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
