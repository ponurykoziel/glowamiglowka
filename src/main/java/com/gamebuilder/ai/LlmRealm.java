package com.gamebuilder.ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public final class LlmRealm {
    private final String name;
    private final String description;

    @JsonCreator
    public LlmRealm(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
}
