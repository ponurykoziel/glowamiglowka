package com.gamebuilder.operator;

import com.gamebuilder.utils.IdProvider;

import java.util.Objects;

public class OperatorBuilderChooser {
    private final IdProvider idProvider;

    public OperatorBuilderChooser(IdProvider idProvider) {
        this.idProvider = Objects.requireNonNull(idProvider, "idProvider must not be null");
    }

    public UnaryOperatorBuilder unary() {
        return new UnaryOperatorBuilder(idProvider);
    }

    public BinaryOperatorBuilder binary() {
        return new BinaryOperatorBuilder(idProvider);
    }

    public TernaryOperatorBuilder ternary() {
        return new TernaryOperatorBuilder(idProvider);
    }
}
