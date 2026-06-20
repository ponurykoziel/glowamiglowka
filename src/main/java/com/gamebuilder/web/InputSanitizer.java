package com.gamebuilder.web;

import java.util.Map;
import java.util.Objects;

public final class InputSanitizer {

    private InputSanitizer() {
        // utility class
    }

    /**
     * Safely extracts a string value from a map body and sanitizes it.
     * Uses Objects.toString() to avoid ClassCastException when the JSON
     * value is a non-string type (number, boolean, null).
     */
    public static String sanitizeField(Map<String, ?> body, String key) {
        return sanitize(Objects.toString(body.get(key), null));
    }

    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (isAllowed(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static boolean isAllowed(char c) {
        return (c >= 'A' && c <= 'Z')
                || (c >= 'a' && c <= 'z')
                || (c >= '0' && c <= '9')
                || c == '!'
                || c == '?'
                || c == '.'
                || c == ','
                || c == '_'
                || c == '-'
                || c == '+'
                || c == '='
                || c == '\n'
                || c == ' ';
    }
}