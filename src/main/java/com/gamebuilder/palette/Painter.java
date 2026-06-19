package com.gamebuilder.palette;

import com.gamebuilder.utils.IdProvider;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.OperatorBuilderChooser;
import com.gamebuilder.operator.UnaryOperatorBuilder;
import com.gamebuilder.operator.BinaryOperatorBuilder;
import com.gamebuilder.operator.TernaryOperatorBuilder;

import java.util.Objects;

public class Painter {
    private final IdProvider idProvider;

    public Painter(IdProvider idProvider) {
        this.idProvider = Objects.requireNonNull(idProvider, "idProvider must not be null");
    }

    public Component component(String name, Realm realm) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(realm, "realm must not be null");
        return new Component("C" + idProvider.getNextId(), name, realm);
    }

    public Realm realm(String name) {
        Objects.requireNonNull(name, "name must not be null");
        return new Realm("R" + idProvider.getNextId(), name);
    }

    public OperatorBuilderChooser operator() {
        return new OperatorBuilderChooser(idProvider);
    }
}
