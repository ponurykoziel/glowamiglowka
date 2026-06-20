package com.gamebuilder.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class OperatorContractDto {
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
    private final boolean transitive;
    private final boolean identityElement;
    private final boolean inverseElement;
    private final boolean absorbingElement;

    @JsonCreator
    public OperatorContractDto(
            @JsonProperty("reflexive") boolean reflexive,
            @JsonProperty("irreflexive") boolean irreflexive,
            @JsonProperty("antisymmetric") boolean antisymmetric,
            @JsonProperty("asymmetric") boolean asymmetric,
            @JsonProperty("idempotent") boolean idempotent,
            @JsonProperty("involutive") boolean involutive,
            @JsonProperty("monotonic") boolean monotonic,
            @JsonProperty("associative") boolean associative,
            @JsonProperty("cancellative") boolean cancellative,
            @JsonProperty("distributive") boolean distributive,
            @JsonProperty("transitive") boolean transitive,
            @JsonProperty("identityElement") boolean identityElement,
            @JsonProperty("inverseElement") boolean inverseElement,
            @JsonProperty("absorbingElement") boolean absorbingElement) {
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
        this.transitive = transitive;
        this.identityElement = identityElement;
        this.inverseElement = inverseElement;
        this.absorbingElement = absorbingElement;
    }

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
