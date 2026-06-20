package com.gamebuilder.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ComponentDto {
    private final String id;
    private final String name;
    private final String realmId;

    @JsonCreator
    public ComponentDto(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("realmId") String realmId) {
        this.id = id;
        this.name = name;
        this.realmId = realmId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getRealmId() { return realmId; }
}
