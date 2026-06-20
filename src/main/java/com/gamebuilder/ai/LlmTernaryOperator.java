package com.gamebuilder.ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class LlmTernaryOperator {
    private final String name;
    private final List<String> leftOperands;
    private final List<String> middleOperands;
    private final List<String> rightOperands;
    private final List<String> contract;
    private final String description;

    @JsonCreator
    public LlmTernaryOperator(
            @JsonProperty("name") String name,
            @JsonProperty("leftOperands") List<String> leftOperands,
            @JsonProperty("middleOperands") List<String> middleOperands,
            @JsonProperty("rightOperands") List<String> rightOperands,
            @JsonProperty("contract") List<String> contract,
            @JsonProperty("description") String description) {
        this.name = name;
        this.leftOperands = leftOperands;
        this.middleOperands = middleOperands;
        this.rightOperands = rightOperands;
        this.contract = contract;
        this.description = description;
    }

    public String getName() { return name; }
    public List<String> getLeftOperands() { return leftOperands; }
    public List<String> getMiddleOperands() { return middleOperands; }
    public List<String> getRightOperands() { return rightOperands; }
    public List<String> getContract() { return contract; }
    public String getDescription() { return description; }
}
