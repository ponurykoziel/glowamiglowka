package com.gamebuilder.operator;

import java.util.Objects;

public final class OperatorContract {
    private final boolean reflexive;
    private final boolean irreflexive;
    private final boolean antisymmetric;
    private final boolean asymmetric;
    private final boolean idempotent;
    private final boolean involutive;
    private final boolean monotonic;
    private final boolean associative;
    private final boolean cancellative;
    private final boolean distributive;
    private final boolean identityElement;
    private final boolean inverseElement;
    private final boolean absorbingElement;

    public OperatorContract(
            boolean reflexive,
            boolean irreflexive,
            boolean antisymmetric,
            boolean asymmetric,
            boolean idempotent,
            boolean involutive,
            boolean monotonic,
            boolean associative,
            boolean cancellative,
            boolean distributive,
            boolean identityElement,
            boolean inverseElement,
            boolean absorbingElement) {
        this.reflexive = reflexive;
        this.irreflexive = irreflexive;
        this.antisymmetric = antisymmetric;
        this.asymmetric = asymmetric;
        this.idempotent = idempotent;
        this.involutive = involutive;
        this.monotonic = monotonic;
        this.associative = associative;
        this.cancellative = cancellative;
        this.distributive = distributive;
        this.identityElement = identityElement;
        this.inverseElement = inverseElement;
        this.absorbingElement = absorbingElement;
    }

    public boolean isReflexive() {
        return reflexive;
    }

    public boolean isIrreflexive() {
        return irreflexive;
    }

    public boolean isAntisymmetric() {
        return antisymmetric;
    }

    public boolean isAsymmetric() {
        return asymmetric;
    }

    public boolean isIdempotent() {
        return idempotent;
    }

    public boolean isInvolutive() {
        return involutive;
    }

    public boolean isMonotonic() {
        return monotonic;
    }

    public boolean isAssociative() {
        return associative;
    }

    public boolean isCancellative() {
        return cancellative;
    }

    public boolean isDistributive() {
        return distributive;
    }

    public boolean isIdentityElement() {
        return identityElement;
    }

    public boolean isInverseElement() {
        return inverseElement;
    }

    public boolean isAbsorbingElement() {
        return absorbingElement;
    }

    public OperatorContract withReflexive(boolean reflexive) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withIrreflexive(boolean irreflexive) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withAntisymmetric(boolean antisymmetric) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withAsymmetric(boolean asymmetric) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withIdempotent(boolean idempotent) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withInvolutive(boolean involutive) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withMonotonic(boolean monotonic) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withAssociative(boolean associative) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withCancellative(boolean cancellative) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withDistributive(boolean distributive) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withIdentityElement(boolean identityElement) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withInverseElement(boolean inverseElement) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    public OperatorContract withAbsorbingElement(boolean absorbingElement) {
        return new OperatorContract(
                reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperatorContract)) return false;
        OperatorContract that = (OperatorContract) o;
        return reflexive == that.reflexive &&
                irreflexive == that.irreflexive &&
                antisymmetric == that.antisymmetric &&
                asymmetric == that.asymmetric &&
                idempotent == that.idempotent &&
                involutive == that.involutive &&
                monotonic == that.monotonic &&
                associative == that.associative &&
                cancellative == that.cancellative &&
                distributive == that.distributive &&
                identityElement == that.identityElement &&
                inverseElement == that.inverseElement &&
                absorbingElement == that.absorbingElement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reflexive, irreflexive, antisymmetric, asymmetric, idempotent,
                involutive, monotonic, associative, cancellative, distributive,
                identityElement, inverseElement, absorbingElement);
    }

    @Override
    public String toString() {
        return "OperatorContract{" +
                "reflexive=" + reflexive +
                ", irreflexive=" + irreflexive +
                ", antisymmetric=" + antisymmetric +
                ", asymmetric=" + asymmetric +
                ", idempotent=" + idempotent +
                ", involutive=" + involutive +
                ", monotonic=" + monotonic +
                ", associative=" + associative +
                ", cancellative=" + cancellative +
                ", distributive=" + distributive +
                ", identityElement=" + identityElement +
                ", inverseElement=" + inverseElement +
                ", absorbingElement=" + absorbingElement +
                '}';
    }
}
