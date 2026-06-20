package com.gamebuilder.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class OperatorDto {
    private final String id;
    private final String name;
    private final int operandCount;
    private final List<List<String>> operands; // component IDs
    private final OperatorContractDto contract;

    @JsonCreator
    public OperatorDto(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("operandCount") int operandCount,
            @JsonProperty("operands") List<List<String>> operands,
            @JsonProperty("contract") OperatorContractDto contract) {
        this.id = id;
        this.name = name;
        this.operandCount = operandCount;
        this.operands = operands;
        this.contract = contract;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getOperandCount() { return operandCount; }
    public List<List<String>> getOperands() { return operands; }
    public OperatorContractDto getContract() { return contract; }
}
