package com.gamebuilder.ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class LlmComponent {
    private final String name;
    private final String realm;
    private final String description;

    @JsonCreator
    public LlmComponent(
            @JsonProperty("name") String name,
            @JsonProperty("realm") String realm,
            @JsonProperty("description") String description) {
        this.name = name;
        this.realm = realm;
        this.description = description;
    }

    public String getName() { return name; }
    public String getRealm() { return realm; }
    public String getDescription() { return description; }
}
