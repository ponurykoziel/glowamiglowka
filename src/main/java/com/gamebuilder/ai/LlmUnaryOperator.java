package com.gamebuilder.ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class LlmUnaryOperator {
    private final String name;
    private final List<String> operands;
    private final List<String> contract;
    private final String description;

    @JsonCreator
    public LlmUnaryOperator(
            @JsonProperty("name") String name,
            @JsonProperty("operands") List<String> operands,
            @JsonProperty("contract") List<String> contract,
            @JsonProperty("description") String description) {
        this.name = name;
        this.operands = operands;
        this.contract = contract;
        this.description = description;
    }

    public String getName() { return name; }
    public List<String> getOperands() { return operands; }
    public List<String> getContract() { return contract; }
    public String getDescription() { return description; }
}
