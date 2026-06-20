package com.gamebuilder.gamedef;

import com.gamebuilder.utils.BijectMap;
import com.gamebuilder.domain.Artifact;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.Operator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class GameDefinitionValidator {
    private final GameDefinition gameDefinition;

    public GameDefinitionValidator(GameDefinition gameDefinition) {
        this.gameDefinition = Objects.requireNonNull(gameDefinition, "gameDefinition must not be null");
    }

    public ValidationResult validate() {
        BijectMap<String, Component> components = gameDefinition.getComponents();
        BijectMap<String, Realm> realms = gameDefinition.getRealms();
        BijectMap<String, Operator> operators = gameDefinition.getOperators();
        BijectMap<String, Artifact> artifacts = gameDefinition.getArtifacts();

        List<String> errors = new ArrayList<>();

        Set<String> allEntityIds = new HashSet<>();
        for (String id : components.keySet()) {
            if (!allEntityIds.add(id)) {
                errors.add("Duplicate entity ID across maps: " + id);
            }
        }
        for (String id : realms.keySet()) {
            if (!allEntityIds.add(id)) {
                errors.add("Duplicate entity ID across maps: " + id);
            }
        }
        for (String id : operators.keySet()) {
            if (!allEntityIds.add(id)) {
                errors.add("Duplicate entity ID across maps: " + id);
            }
        }

        for (Component c : components.values()) {
            Realm realm = c.getRealm();
            if (realm == null || !realms.containsKey(realm.getId())) {
                errors.add("Component '" + c.getName() + "' (" + c.getId() + ") references missing realm: " + (realm != null ? realm.getId() : "null"));
            }
        }

        for (Operator op : operators.values()) {
            for (int slotIdx = 0; slotIdx < op.getOperands().size(); slotIdx++) {
                List<Component> slot = op.getOperands().get(slotIdx);
                for (Component operand : slot) {
                    if (!components.containsKey(operand.getId())) {
                        errors.add("Operator '" + op.getName() + "' (" + op.getId() + ") slot " + slotIdx + " references missing component: " + operand.getId());
                    }
                }
            }
        }

        for (String entityId : artifacts.keySet()) {
            if (gameDefinition.findEntity(entityId) == null) {
                errors.add("Artifact references missing entity: " + entityId);
            }
        }

        for (String id : components.keySet()) {
            if (!artifacts.containsKey(id)) {
                errors.add("Component '" + components.getByKey(id).getName() + "' (" + id + ") has no artifact");
            }
        }
        for (String id : realms.keySet()) {
            if (!artifacts.containsKey(id)) {
                errors.add("Realm '" + realms.getByKey(id).getName() + "' (" + id + ") has no artifact");
            }
        }
        for (String id : operators.keySet()) {
            if (!artifacts.containsKey(id)) {
                errors.add("Operator '" + operators.getByKey(id).getName() + "' (" + id + ") has no artifact");
            }
        }

        if (errors.isEmpty()) {
            return ValidationResult.ok();
        }
        return ValidationResult.fail(errors);
    }

    public boolean validateCoherence() {
        return validate().isValid();
    }
}