package com.gamebuilder.operator;

import com.gamebuilder.domain.Component;

import java.util.List;
import java.util.Objects;

public final class TernaryOperator implements Operator {
    private final String id;
    private final String name;
    private final List<Component> left;
    private final List<Component> middle;
    private final List<Component> right;
    private final OperatorContract contract;

    public TernaryOperator(String id, String name, List<Component> left, List<Component> middle, List<Component> right, OperatorContract contract) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.left = List.copyOf(Objects.requireNonNull(left, "left must not be null"));
        this.middle = List.copyOf(Objects.requireNonNull(middle, "middle must not be null"));
        this.right = List.copyOf(Objects.requireNonNull(right, "right must not be null"));
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
        return 3;
    }

    @Override
    public List<List<Component>> getOperands() {
        return List.of(left, middle, right);
    }

    public List<Component> getLeft() {
        return left;
    }

    public List<Component> getMiddle() {
        return middle;
    }

    public List<Component> getRight() {
        return right;
    }

    @Override
    public OperatorContract getContract() {
        return contract;
    }

    public TernaryOperator withId(String id) {
        return new TernaryOperator(id, this.name, this.left, this.middle, this.right, this.contract);
    }

    public TernaryOperator withName(String name) {
        return new TernaryOperator(this.id, name, this.left, this.middle, this.right, this.contract);
    }

    public TernaryOperator withLeft(List<Component> left) {
        return new TernaryOperator(this.id, this.name, left, this.middle, this.right, this.contract);
    }

    public TernaryOperator withMiddle(List<Component> middle) {
        return new TernaryOperator(this.id, this.name, this.left, middle, this.right, this.contract);
    }

    public TernaryOperator withRight(List<Component> right) {
        return new TernaryOperator(this.id, this.name, this.left, this.middle, right, this.contract);
    }

    public TernaryOperator withContract(OperatorContract contract) {
        return new TernaryOperator(this.id, this.name, this.left, this.middle, this.right, contract);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TernaryOperator)) return false;
        TernaryOperator that = (TernaryOperator) o;
        return id.equals(that.id) && name.equals(that.name) && left.equals(that.left) && middle.equals(that.middle) && right.equals(that.right) && contract.equals(that.contract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, left, middle, right, contract);
    }

    @Override
    public String toString() {
        return "TernaryOperator{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", left=" + left + ", middle=" + middle + ", right=" + right + ", contract=" + contract + '}';
    }
}
