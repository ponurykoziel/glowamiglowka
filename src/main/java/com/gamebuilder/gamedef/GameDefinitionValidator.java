package com.gamebuilder.gamedef;

import com.gamebuilder.utils.BijectMap;
import com.gamebuilder.domain.Artifact;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.Operator;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class GameDefinitionValidator {
    private final GameDefinition gameDefinition;

    public GameDefinitionValidator(GameDefinition gameDefinition) {
        this.gameDefinition = Objects.requireNonNull(gameDefinition, "gameDefinition must not be null");
    }

    public boolean validateCoherence() {
        BijectMap<String, Component> components = gameDefinition.getComponents();
        BijectMap<String, Realm> realms = gameDefinition.getRealms();
        BijectMap<String, Operator> operators = gameDefinition.getOperators();
        BijectMap<String, Artifact> artifacts = gameDefinition.getArtifacts();

        Set<String> allEntityIds = new HashSet<>();
        for (String id : components.keySet()) {
            if (!allEntityIds.add(id)) return false;
        }
        for (String id : realms.keySet()) {
            if (!allEntityIds.add(id)) return false;
        }
        for (String id : operators.keySet()) {
            if (!allEntityIds.add(id)) return false;
        }

        for (Component component : components.values()) {
            Realm realm = component.getRealm();
            if (realm == null || !realms.containsKey(realm.getId())) {
                return false;
            }
        }

        for (Operator operator : operators.values()) {
            for (List<Component> overload : operator.getOperands()) {
                for (Component operand : overload) {
                    if (!components.containsKey(operand.getId())) {
                        return false;
                    }
                }
            }
        }

        for (String entityId : artifacts.keySet()) {
            if (gameDefinition.findEntity(entityId) == null) {
                return false;
            }
        }

        for (String entityId : components.keySet()) {
            if (!artifacts.containsKey(entityId)) {
                return false;
            }
        }
        for (String entityId : realms.keySet()) {
            if (!artifacts.containsKey(entityId)) {
                return false;
            }
        }
        for (String entityId : operators.keySet()) {
            if (!artifacts.containsKey(entityId)) {
                return false;
            }
        }

        return true;
    }
}
