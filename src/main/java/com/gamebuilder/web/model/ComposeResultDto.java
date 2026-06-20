package com.gamebuilder.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ComposeResultDto {
    private final GameDefinitionDto gameDefinition;
    private final ValidationResultDto validation;

    @JsonCreator
    public ComposeResultDto(
            @JsonProperty("gameDefinition") GameDefinitionDto gameDefinition,
            @JsonProperty("validation") ValidationResultDto validation) {
        this.gameDefinition = gameDefinition;
        this.validation = validation;
    }

    public GameDefinitionDto getGameDefinition() { return gameDefinition; }
    public ValidationResultDto getValidation() { return validation; }
}
