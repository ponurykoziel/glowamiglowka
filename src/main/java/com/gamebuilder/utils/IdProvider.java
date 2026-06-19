package com.gamebuilder.utils;

import com.gamebuilder.domain.AppConfig;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class IdProvider {
    private final AppConfig appConfig;
    private final AtomicLong counter = new AtomicLong(0);

    public IdProvider(AppConfig appConfig) {
        this.appConfig = Objects.requireNonNull(appConfig, "appConfig must not be null");
    }

    public String getNextId() {
        long value = counter.addAndGet(1);
        int length = appConfig.getIdLength();
        return String.format("%0" + length + "X", value);
    }
}
