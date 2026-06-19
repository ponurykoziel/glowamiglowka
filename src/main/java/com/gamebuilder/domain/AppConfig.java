package com.gamebuilder.domain;

import com.gamebuilder.utils.IdProvider;

import java.util.Objects;

public final class AppConfig {
    private final IdProvider idProvider;
    private int idLength;

    public AppConfig(int idLength) {
        if (idLength < 1) {
            throw new IllegalArgumentException("idLength must be >= 1");
        }
        this.idLength = idLength;
        this.idProvider = new IdProvider(this);
    }

    public IdProvider getIdProvider() {
        return idProvider;
    }

    public int getIdLength() {
        return idLength;
    }

    public void setIdLength(int idLength) {
        if (idLength < 1) {
            throw new IllegalArgumentException("idLength must be >= 1");
        }
        this.idLength = idLength;
    }
}
