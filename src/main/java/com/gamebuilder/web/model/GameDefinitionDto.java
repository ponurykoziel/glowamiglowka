package com.gamebuilder.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class GameDefinitionDto {
    private final String id;
    private final List<RealmDto> realms;
    private final List<ComponentDto> components;
    private final List<OperatorDto> operators;
    private final List<ArtifactDto> artifacts;

    @JsonCreator
    public GameDefinitionDto(
            @JsonProperty("id") String id,
            @JsonProperty("realms") List<RealmDto> realms,
            @JsonProperty("components") List<ComponentDto> components,
            @JsonProperty("operators") List<OperatorDto> operators,
            @JsonProperty("artifacts") List<ArtifactDto> artifacts) {
        this.id = id;
        this.realms = realms;
        this.components = components;
        this.operators = operators;
        this.artifacts = artifacts;
    }

    public String getId() { return id; }
    public List<RealmDto> getRealms() { return realms; }
    public List<ComponentDto> getComponents() { return components; }
    public List<OperatorDto> getOperators() { return operators; }
    public List<ArtifactDto> getArtifacts() { return artifacts; }
}
