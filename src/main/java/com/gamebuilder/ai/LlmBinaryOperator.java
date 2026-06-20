package com.gamebuilder.ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class LlmBinaryOperator {
    private final String name;
    private final List<String> lhsOperands;
    private final List<String> rhsOperands;
    private final List<String> contract;
    private final String description;

    @JsonCreator
    public LlmBinaryOperator(
            @JsonProperty("name") String name,
            @JsonProperty("lhsOperands") List<String> lhsOperands,
            @JsonProperty("rhsOperands") List<String> rhsOperands,
            @JsonProperty("contract") List<String> contract,
            @JsonProperty("description") String description) {
        this.name = name;
        this.lhsOperands = lhsOperands;
        this.rhsOperands = rhsOperands;
        this.contract = contract;
        this.description = description;
    }

    public String getName() { return name; }
    public List<String> getLhsOperands() { return lhsOperands; }
    public List<String> getRhsOperands() { return rhsOperands; }
    public List<String> getContract() { return contract; }
    public String getDescription() { return description; }
}
