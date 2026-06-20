package com.gamebuilder.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ArtifactDto {
    private final String entityId;
    private final String description;

    @JsonCreator
    public ArtifactDto(
            @JsonProperty("entityId") String entityId,
            @JsonProperty("description") String description) {
        this.entityId = entityId;
        this.description = description;
    }

    public String getEntityId() { return entityId; }
    public String getDescription() { return description; }
}
