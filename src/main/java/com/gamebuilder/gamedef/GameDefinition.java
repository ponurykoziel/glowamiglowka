package com.gamebuilder.gamedef;

import com.gamebuilder.utils.BijectMap;
import com.gamebuilder.domain.Artifact;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Entity;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.Operator;

import java.util.Objects;

public final class GameDefinition {
    private final String id;
    private final BijectMap<String, Component> components;
    private final BijectMap<String, Realm> realms;
    private final BijectMap<String, Operator> operators;
    private final BijectMap<String, Artifact> artifacts;

    public GameDefinition(
            String id,
            BijectMap<String, Component> components,
            BijectMap<String, Realm> realms,
            BijectMap<String, Operator> operators,
            BijectMap<String, Artifact> artifacts) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.components = new BijectMap<>(Objects.requireNonNull(components, "components must not be null"));
        this.realms = new BijectMap<>(Objects.requireNonNull(realms, "realms must not be null"));
        this.operators = new BijectMap<>(Objects.requireNonNull(operators, "operators must not be null"));
        this.artifacts = new BijectMap<>(Objects.requireNonNull(artifacts, "artifacts must not be null"));
    }

    public String getId() {
        return id;
    }

    public BijectMap<String, Component> getComponents() {
        return new BijectMap<>(components);
    }

    public BijectMap<String, Realm> getRealms() {
        return new BijectMap<>(realms);
    }

    public BijectMap<String, Operator> getOperators() {
        return new BijectMap<>(operators);
    }

    public BijectMap<String, Artifact> getArtifacts() {
        return new BijectMap<>(artifacts);
    }

    public Entity findEntity(String id) {
        Entity entity = components.getByKey(id);
        if (entity != null) return entity;
        entity = realms.getByKey(id);
        if (entity != null) return entity;
        return operators.getByKey(id);
    }

    public GameDefinition withId(String id) {
        return new GameDefinition(id, this.components, this.realms, this.operators, this.artifacts);
    }

    public GameDefinition withComponents(BijectMap<String, Component> components) {
        return new GameDefinition(this.id, components, this.realms, this.operators, this.artifacts);
    }

    public GameDefinition withRealms(BijectMap<String, Realm> realms) {
        return new GameDefinition(this.id, this.components, realms, this.operators, this.artifacts);
    }

    public GameDefinition withOperators(BijectMap<String, Operator> operators) {
        return new GameDefinition(this.id, this.components, this.realms, operators, this.artifacts);
    }

    public GameDefinition withArtifacts(BijectMap<String, Artifact> artifacts) {
        return new GameDefinition(this.id, this.components, this.realms, this.operators, artifacts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameDefinition)) return false;
        GameDefinition that = (GameDefinition) o;
        return id.equals(that.id) && components.equals(that.components) && realms.equals(that.realms) && operators.equals(that.operators) && artifacts.equals(that.artifacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, components, realms, operators, artifacts);
    }

    @Override
    public String toString() {
        return "GameDefinition{" + "id='" + id + '\'' + ", components=" + components + ", realms=" + realms + ", operators=" + operators + ", artifacts=" + artifacts + '}';
    }
}
