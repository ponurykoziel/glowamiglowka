package com.gamebuilder.operator;

import com.gamebuilder.domain.Component;
import com.gamebuilder.utils.IdProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UnaryOperatorBuilder {
    private final IdProvider idProvider;
    private String name;
    private final List<Component> operandBuffer = new ArrayList<>();
    private OperatorContract contract;

    public UnaryOperatorBuilder(IdProvider idProvider) {
        this.idProvider = Objects.requireNonNull(idProvider, "idProvider must not be null");
    }

    public UnaryOperatorBuilder name(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        return this;
    }

    public UnaryOperatorBuilder addOperands(List<Component> operands) {
        Objects.requireNonNull(operands, "operands must not be null");
        operandBuffer.addAll(operands);
        return this;
    }

    public UnaryOperatorBuilder addOperands(Component... components) {
        Objects.requireNonNull(components, "components must not be null");
        operandBuffer.addAll(Arrays.asList(components));
        return this;
    }

    public ContractBuilder<UnaryOperatorBuilder> contract() {
        return new ContractBuilder<>(this, this::setContract);
    }

    public UnaryOperator build() {
        Objects.requireNonNull(name, "name must be set before build");
        if (contract == null) {
            contract = new OperatorContract(
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false, false
            );
        }
        return new UnaryOperator("F" + idProvider.getNextId(), name, List.copyOf(operandBuffer), contract);
    }

    void setContract(OperatorContract contract) {
        this.contract = contract;
    }
}
