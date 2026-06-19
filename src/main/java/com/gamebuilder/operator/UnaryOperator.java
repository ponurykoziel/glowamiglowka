package com.gamebuilder.operator;

import com.gamebuilder.domain.Component;

import java.util.List;
import java.util.Objects;

public final class UnaryOperator implements Operator {
    private final String id;
    private final String name;
    private final List<Component> operand;
    private final OperatorContract contract;

    public UnaryOperator(String id, String name, List<Component> operand, OperatorContract contract) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.operand = List.copyOf(Objects.requireNonNull(operand, "operand must not be null"));
        this.contract = Objects.requireNonNull(contract, "contract must not be null");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOperandCount() {
        return 1;
    }

    @Override
    public List<List<Component>> getOperands() {
        return List.of(operand);
    }

    public List<Component> getOperand() {
        return operand;
    }

    @Override
    public OperatorContract getContract() {
        return contract;
    }

    public UnaryOperator withId(String id) {
        return new UnaryOperator(id, this.name, this.operand, this.contract);
    }

    public UnaryOperator withName(String name) {
        return new UnaryOperator(this.id, name, this.operand, this.contract);
    }

    public UnaryOperator withOperand(List<Component> operand) {
        return new UnaryOperator(this.id, this.name, operand, this.contract);
    }

    public UnaryOperator withContract(OperatorContract contract) {
        return new UnaryOperator(this.id, this.name, this.operand, contract);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnaryOperator)) return false;
        UnaryOperator that = (UnaryOperator) o;
        return id.equals(that.id) && name.equals(that.name) && operand.equals(that.operand) && contract.equals(that.contract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, operand, contract);
    }

    @Override
    public String toString() {
        return "UnaryOperator{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", operand=" + operand + ", contract=" + contract + '}';
    }
}
