package com.gamebuilder.domain;

import java.util.Objects;

public final class Artifact {
    private final String entityId;
    private final String description;

    public Artifact(String entityId, String description) {
        this.entityId = Objects.requireNonNull(entityId, "entityId must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
    }

    public String getEntityId() {
        return entityId;
    }

    public String getDescription() {
        return description;
    }

    public Artifact withEntityId(String entityId) {
        return new Artifact(entityId, this.description);
    }

    public Artifact withDescription(String description) {
        return new Artifact(this.entityId, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artifact)) return false;
        Artifact artifact = (Artifact) o;
        return entityId.equals(artifact.entityId) && description.equals(artifact.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, description);
    }

    @Override
    public String toString() {
        return "Artifact{" + "entityId='" + entityId + '\'' + ", description='" + description + '\'' + '}';
    }
}
