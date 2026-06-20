package com.gamebuilder.operator;

public record OperatorContract(
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
        boolean transitive,
        boolean identityElement,
        boolean inverseElement,
        boolean absorbingElement
) {
    public boolean isReflexive() { return reflexive; }
    public boolean isIrreflexive() { return irreflexive; }
    public boolean isAntisymmetric() { return antisymmetric; }
    public boolean isAsymmetric() { return asymmetric; }
    public boolean isIdempotent() { return idempotent; }
    public boolean isInvolutive() { return involutive; }
    public boolean isMonotonic() { return monotonic; }
    public boolean isAssociative() { return associative; }
    public boolean isCancellative() { return cancellative; }
    public boolean isDistributive() { return distributive; }
    public boolean isTransitive() { return transitive; }
    public boolean isIdentityElement() { return identityElement; }
    public boolean isInverseElement() { return inverseElement; }
    public boolean isAbsorbingElement() { return absorbingElement; }
}