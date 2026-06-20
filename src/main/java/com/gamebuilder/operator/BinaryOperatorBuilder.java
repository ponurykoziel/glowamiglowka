package com.gamebuilder.operator;

import com.gamebuilder.domain.Component;
import com.gamebuilder.utils.IdProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BinaryOperatorBuilder {
    private final IdProvider idProvider;
    private String name;
    private final List<Component> lhsBuffer = new ArrayList<>();
    private final List<Component> rhsBuffer = new ArrayList<>();
    private OperatorContract contract;

    public BinaryOperatorBuilder(IdProvider idProvider) {
        this.idProvider = Objects.requireNonNull(idProvider, "idProvider must not be null");
    }

    public BinaryOperatorBuilder name(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        return this;
    }

    public BinaryOperatorBuilder addLhsOperands(List<Component> operands) {
        Objects.requireNonNull(operands, "operands must not be null");
        lhsBuffer.addAll(operands);
        return this;
    }

    public BinaryOperatorBuilder addLhsOperands(Component... components) {
        Objects.requireNonNull(components, "components must not be null");
        lhsBuffer.addAll(Arrays.asList(components));
        return this;
    }

    public BinaryOperatorBuilder addRhsOperands(List<Component> operands) {
        Objects.requireNonNull(operands, "operands must not be null");
        rhsBuffer.addAll(operands);
        return this;
    }

    public BinaryOperatorBuilder addRhsOperands(Component... components) {
        Objects.requireNonNull(components, "components must not be null");
        rhsBuffer.addAll(Arrays.asList(components));
        return this;
    }

    public ContractBuilder<BinaryOperatorBuilder> contract() {
        return new ContractBuilder<>(this, this::setContract);
    }

    public BinaryOperator build() {
        Objects.requireNonNull(name, "name must be set before build");
        if (contract == null) {
            contract = new OperatorContract(
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false, false
            );
        }
        return new BinaryOperator("F" + idProvider.getNextId(), name, List.copyOf(lhsBuffer), List.copyOf(rhsBuffer), contract);
    }

    void setContract(OperatorContract contract) {
        this.contract = contract;
    }
}
