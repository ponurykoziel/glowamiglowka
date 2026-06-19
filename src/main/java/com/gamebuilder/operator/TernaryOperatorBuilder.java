package com.gamebuilder.operator;

import com.gamebuilder.domain.Component;
import com.gamebuilder.utils.IdProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TernaryOperatorBuilder {
    private final IdProvider idProvider;
    private String name;
    private final List<Component> leftBuffer = new ArrayList<>();
    private final List<Component> middleBuffer = new ArrayList<>();
    private final List<Component> rightBuffer = new ArrayList<>();
    private OperatorContract contract;

    public TernaryOperatorBuilder(IdProvider idProvider) {
        this.idProvider = Objects.requireNonNull(idProvider, "idProvider must not be null");
    }

    public TernaryOperatorBuilder name(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        return this;
    }

    public TernaryOperatorBuilder addLeftOperands(List<Component> operands) {
        Objects.requireNonNull(operands, "operands must not be null");
        leftBuffer.addAll(operands);
        return this;
    }

    public TernaryOperatorBuilder addLeftOperands(Component... components) {
        Objects.requireNonNull(components, "components must not be null");
        leftBuffer.addAll(Arrays.asList(components));
        return this;
    }

    public TernaryOperatorBuilder addMiddleOperands(List<Component> operands) {
        Objects.requireNonNull(operands, "operands must not be null");
        middleBuffer.addAll(operands);
        return this;
    }

    public TernaryOperatorBuilder addMiddleOperands(Component... components) {
        Objects.requireNonNull(components, "components must not be null");
        middleBuffer.addAll(Arrays.asList(components));
        return this;
    }

    public TernaryOperatorBuilder addRightOperands(List<Component> operands) {
        Objects.requireNonNull(operands, "operands must not be null");
        rightBuffer.addAll(operands);
        return this;
    }

    public TernaryOperatorBuilder addRightOperands(Component... components) {
        Objects.requireNonNull(components, "components must not be null");
        rightBuffer.addAll(Arrays.asList(components));
        return this;
    }

    public ContractBuilder<TernaryOperatorBuilder> contract() {
        return new ContractBuilder<>(new WeakReference<>(this), this::setContract);
    }

    public TernaryOperator build() {
        Objects.requireNonNull(name, "name must be set before build");
        if (contract == null) {
            contract = new OperatorContract(
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false
            );
        }
        return new TernaryOperator("F" + idProvider.getNextId(), name, List.copyOf(leftBuffer), List.copyOf(middleBuffer), List.copyOf(rightBuffer), contract);
    }

    void setContract(OperatorContract contract) {
        this.contract = contract;
    }
}
