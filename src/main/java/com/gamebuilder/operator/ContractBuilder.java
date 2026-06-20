package com.gamebuilder.operator;

import java.util.Objects;
import java.util.function.Consumer;

public class ContractBuilder<T> {
    private final T owner;
    private final Consumer<OperatorContract> contractSetter;

    private boolean reflexive;
    private boolean irreflexive;
    private boolean antisymmetric;
    private boolean asymmetric;
    private boolean idempotent;
    private boolean involutive;
    private boolean monotonic;
    private boolean associative;
    private boolean cancellative;
    private boolean distributive;
    private boolean transitive;
    private boolean identityElement;
    private boolean inverseElement;
    private boolean absorbingElement;

    public ContractBuilder(T owner, Consumer<OperatorContract> contractSetter) {
        this.owner = Objects.requireNonNull(owner, "owner must not be null");
        this.contractSetter = Objects.requireNonNull(contractSetter, "contractSetter must not be null");
    }

    public ContractBuilder<T> withReflexive(boolean reflexive) {
        this.reflexive = reflexive;
        return this;
    }

    public ContractBuilder<T> withIrreflexive(boolean irreflexive) {
        this.irreflexive = irreflexive;
        return this;
    }

    public ContractBuilder<T> withAntisymmetric(boolean antisymmetric) {
        this.antisymmetric = antisymmetric;
        return this;
    }

    public ContractBuilder<T> withAsymmetric(boolean asymmetric) {
        this.asymmetric = asymmetric;
        return this;
    }

    public ContractBuilder<T> withIdempotent(boolean idempotent) {
        this.idempotent = idempotent;
        return this;
    }

    public ContractBuilder<T> withInvolutive(boolean involutive) {
        this.involutive = involutive;
        return this;
    }

    public ContractBuilder<T> withMonotonic(boolean monotonic) {
        this.monotonic = monotonic;
        return this;
    }

    public ContractBuilder<T> withAssociative(boolean associative) {
        this.associative = associative;
        return this;
    }

    public ContractBuilder<T> withCancellative(boolean cancellative) {
        this.cancellative = cancellative;
        return this;
    }

    public ContractBuilder<T> withDistributive(boolean distributive) {
        this.distributive = distributive;
        return this;
    }

    public ContractBuilder<T> withTransitive(boolean transitive) {
        this.transitive = transitive;
        return this;
    }

    public ContractBuilder<T> withIdentityElement(boolean identityElement) {
        this.identityElement = identityElement;
        return this;
    }

    public ContractBuilder<T> withInverseElement(boolean inverseElement) {
        this.inverseElement = inverseElement;
        return this;
    }

    public ContractBuilder<T> withAbsorbingElement(boolean absorbingElement) {
        this.absorbingElement = absorbingElement;
        return this;
    }

    public T buildContract() {
        contractSetter.accept(new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                transitive, identityElement, inverseElement, absorbingElement
        ));
        return owner;
    }
}