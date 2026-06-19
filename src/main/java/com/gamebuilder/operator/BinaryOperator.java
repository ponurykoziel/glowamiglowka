package com.gamebuilder.operator;

import com.gamebuilder.domain.Component;

import java.util.List;
import java.util.Objects;

public final class BinaryOperator implements Operator {
    private final String id;
    private final String name;
    private final List<Component> lhs;
    private final List<Component> rhs;
    private final OperatorContract contract;

    public BinaryOperator(String id, String name, List<Component> lhs, List<Component> rhs, OperatorContract contract) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.lhs = List.copyOf(Objects.requireNonNull(lhs, "lhs must not be null"));
        this.rhs = List.copyOf(Objects.requireNonNull(rhs, "rhs must not be null"));
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
        return 2;
    }

    @Override
    public List<List<Component>> getOperands() {
        return List.of(lhs, rhs);
    }

    public List<Component> getLhs() {
        return lhs;
    }

    public List<Component> getRhs() {
        return rhs;
    }

    @Override
    public OperatorContract getContract() {
        return contract;
    }

    public BinaryOperator withId(String id) {
        return new BinaryOperator(id, this.name, this.lhs, this.rhs, this.contract);
    }

    public BinaryOperator withName(String name) {
        return new BinaryOperator(this.id, name, this.lhs, this.rhs, this.contract);
    }

    public BinaryOperator withLhs(List<Component> lhs) {
        return new BinaryOperator(this.id, this.name, lhs, this.rhs, this.contract);
    }

    public BinaryOperator withRhs(List<Component> rhs) {
        return new BinaryOperator(this.id, this.name, this.lhs, rhs, this.contract);
    }

    public BinaryOperator withContract(OperatorContract contract) {
        return new BinaryOperator(this.id, this.name, this.lhs, this.rhs, contract);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryOperator)) return false;
        BinaryOperator that = (BinaryOperator) o;
        return id.equals(that.id) && name.equals(that.name) && lhs.equals(that.lhs) && rhs.equals(that.rhs) && contract.equals(that.contract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lhs, rhs, contract);
    }

    @Override
    public String toString() {
        return "BinaryOperator{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", lhs=" + lhs + ", rhs=" + rhs + ", contract=" + contract + '}';
    }
}
