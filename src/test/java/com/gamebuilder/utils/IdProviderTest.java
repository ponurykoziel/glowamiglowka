package com.gamebuilder.utils;

import com.gamebuilder.domain.AppConfig;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class IdProviderTest {

    private AppConfig appConfig(int length) {
        return new AppConfig(length);
    }

    @Test
    void firstIdIsOne() {
        IdProvider provider = appConfig(4).getIdProvider();
        assertEquals("0001", provider.getNextId());
    }

    @Test
    void idsAreSequential() {
        IdProvider provider = appConfig(4).getIdProvider();
        assertEquals("0001", provider.getNextId());
        assertEquals("0002", provider.getNextId());
        assertEquals("0003", provider.getNextId());
    }

    @Test
    void idsAreUppercaseHex() {
        IdProvider provider = appConfig(4).getIdProvider();
        for (int i = 0; i < 15; i++) {
            provider.getNextId();
        }
        assertEquals("0010", provider.getNextId());
    }

    @Test
    void respectsConfigLength() {
        IdProvider provider = appConfig(8).getIdProvider();
        assertEquals("00000001", provider.getNextId());
    }

    @Test
    void concurrentAccessProducesUniqueIds() throws InterruptedException {
        AppConfig config = appConfig(4);
        IdProvider provider = config.getIdProvider();
        int threads = 10;
        int perThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        Set<String> ids = ConcurrentHashMap.newKeySet();

        for (int t = 0; t < threads; t++) {
            executor.submit(() -> {
                try {
                    for (int i = 0; i < perThread; i++) {
                        ids.add(provider.getNextId());
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        assertEquals(threads * perThread, ids.size());
    }

    @Test
    void noLeadingPrefix() {
        IdProvider provider = appConfig(4).getIdProvider();
        String id = provider.getNextId();
        assertFalse(id.startsWith("0x"));
        assertFalse(id.startsWith("x"));
    }
}
