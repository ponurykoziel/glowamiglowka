package com.gamebuilder.gamedef;

import com.gamebuilder.utils.BijectMap;
import com.gamebuilder.domain.Artifact;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Entity;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.BinaryOperator;
import com.gamebuilder.operator.Operator;
import com.gamebuilder.operator.OperatorContract;
import com.gamebuilder.operator.UnaryOperator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameDefinitionValidatorTest {

    private static Realm realm(String id) {
        return new Realm(id, "realm-" + id);
    }

    private static Component component(String id, Realm r) {
        return new Component(id, "comp-" + id, r);
    }

    private static Operator unary(String id, List<Component> ops) {
        return new UnaryOperator(id, "op-" + id, ops, allFalseContract());
    }

    private static Operator binary(String id, List<Component> lhs, List<Component> rhs) {
        return new BinaryOperator(id, "op-" + id, lhs, rhs, allFalseContract());
    }

    private static OperatorContract allFalseContract() {
        return new OperatorContract(
                false, false, false, false, false,
                false, false, false, false, false,
                false, false, false);
    }

    private static Artifact artifact(Entity entity) {
        return new Artifact(entity.getId(), "desc");
    }

    private static GameDefinition definition(
            BijectMap<String, Component> components,
            BijectMap<String, Realm> realms,
            BijectMap<String, Operator> operators,
            BijectMap<String, Artifact> artifacts) {
        return new GameDefinition("test-def", components, realms, operators, artifacts);
    }

    @Test
    void fullyValidDefinition() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        Realm r = realm("R1");
        realms.put(r.getId(), r);

        BijectMap<String, Component> components = new BijectMap<>();
        Component c = component("C1", r);
        components.put(c.getId(), c);

        BijectMap<String, Operator> operators = new BijectMap<>();
        Operator op = unary("F1", List.of(c));
        operators.put(op.getId(), op);

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put(r.getId(), artifact(r));
        artifacts.put(c.getId(), artifact(c));
        artifacts.put(op.getId(), artifact(op));

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, operators, artifacts));
        assertTrue(v.validateCoherence());
    }

    @Test
    void componentWithMissingRealm() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        BijectMap<String, Component> components = new BijectMap<>();
        Component c = new Component("C1", "orphan", realm("R_MISSING"));
        components.put(c.getId(), c);

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put(c.getId(), artifact(c));

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, new BijectMap<>(), artifacts));
        assertFalse(v.validateCoherence());
    }

    @Test
    void operatorWithMissingOperand() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        Realm r = realm("R1");
        realms.put(r.getId(), r);

        BijectMap<String, Component> components = new BijectMap<>();
        Component c = component("C1", r);
        components.put(c.getId(), c);

        BijectMap<String, Operator> operators = new BijectMap<>();
        Component missing = component("C_MISSING", r);
        Operator op = unary("F1", List.of(missing));
        operators.put(op.getId(), op);

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put(r.getId(), artifact(r));
        artifacts.put(c.getId(), artifact(c));
        artifacts.put(op.getId(), artifact(op));

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, operators, artifacts));
        assertFalse(v.validateCoherence());
    }

    @Test
    void artifactWithMissingEntity() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        BijectMap<String, Component> components = new BijectMap<>();
        BijectMap<String, Operator> operators = new BijectMap<>();

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put("MISSING", new Artifact("MISSING", "orphan artifact"));

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, operators, artifacts));
        assertFalse(v.validateCoherence());
    }

    @Test
    void entityWithoutArtifact() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        Realm r = realm("R1");
        realms.put(r.getId(), r);

        BijectMap<String, Component> components = new BijectMap<>();
        Component c = component("C1", r);
        components.put(c.getId(), c);

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put(r.getId(), artifact(r));
        // c has no artifact

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, new BijectMap<>(), artifacts));
        assertFalse(v.validateCoherence());
    }

    @Test
    void operatorWithoutArtifact() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        Realm r = realm("R1");
        realms.put(r.getId(), r);

        BijectMap<String, Component> components = new BijectMap<>();
        Component c = component("C1", r);
        components.put(c.getId(), c);

        BijectMap<String, Operator> operators = new BijectMap<>();
        Operator op = unary("F1", List.of(c));
        operators.put(op.getId(), op);

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put(r.getId(), artifact(r));
        artifacts.put(c.getId(), artifact(c));
        // op has no artifact

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, operators, artifacts));
        assertFalse(v.validateCoherence());
    }

    @Test
    void binaryOperatorWithMissingRhsOperand() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        Realm r = realm("R1");
        realms.put(r.getId(), r);

        BijectMap<String, Component> components = new BijectMap<>();
        Component c = component("C1", r);
        components.put(c.getId(), c);

        BijectMap<String, Operator> operators = new BijectMap<>();
        Component missing = component("C_MISSING", r);
        Operator op = binary("F1", List.of(c), List.of(missing));
        operators.put(op.getId(), op);

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put(r.getId(), artifact(r));
        artifacts.put(c.getId(), artifact(c));
        artifacts.put(op.getId(), artifact(op));

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, operators, artifacts));
        assertFalse(v.validateCoherence());
    }

    @Test
    void emptyDefinitionIsValid() {
        GameDefinitionValidator v = new GameDefinitionValidator(
                definition(new BijectMap<>(), new BijectMap<>(), new BijectMap<>(), new BijectMap<>()));
        assertTrue(v.validateCoherence());
    }

    @Test
    void duplicateIdAcrossMapsFails() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        Realm r = realm("DUP");
        realms.put(r.getId(), r);

        BijectMap<String, Component> components = new BijectMap<>();
        Component c = new Component("DUP", "dup-comp", r);
        components.put(c.getId(), c);

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put(r.getId(), artifact(r));
        artifacts.put(c.getId(), artifact(c));

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, new BijectMap<>(), artifacts));
        assertFalse(v.validateCoherence());
    }

    @Test
    void duplicateIdBetweenComponentAndOperatorFails() {
        BijectMap<String, Realm> realms = new BijectMap<>();
        Realm r = realm("R1");
        realms.put(r.getId(), r);

        BijectMap<String, Component> components = new BijectMap<>();
        Component c = component("DUP", r);
        components.put(c.getId(), c);

        BijectMap<String, Operator> operators = new BijectMap<>();
        Operator op = unary("DUP", List.of(c));
        operators.put(op.getId(), op);

        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.put(r.getId(), artifact(r));
        artifacts.put(c.getId(), artifact(c));
        artifacts.put(op.getId(), artifact(op));

        GameDefinitionValidator v = new GameDefinitionValidator(definition(components, realms, operators, artifacts));
        assertFalse(v.validateCoherence());
    }
}
