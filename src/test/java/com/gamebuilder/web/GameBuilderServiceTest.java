package com.gamebuilder.web;

import com.gamebuilder.web.model.*;
import com.gamebuilder.web.service.GameBuilderService;
import com.gamebuilder.web.state.GameBuilderState;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class GameBuilderServiceTest {

    @Inject
    GameBuilderState state;

    @Inject
    GameBuilderService service;

    @BeforeEach
    void setUp() {
        state.clearPalette();
    }

    @Test
    void emptyPaletteHasRandomId() {
        PaletteDto p = service.getPalette();
        assertNotNull(p.getId());
        assertTrue(p.getId().startsWith("palette-"));
        assertEquals(0, p.getRealms().size());
        assertEquals(0, p.getComponents().size());
        assertEquals(0, p.getOperators().size());
    }

    @Test
    void setPaletteId() {
        PaletteDto p = service.setPaletteId("my-game");
        assertEquals("my-game", p.getId());
    }

    @Test
    void addAndRemoveRealm() {
        RealmDto r = service.addRealm("item");
        assertNotNull(r.getId());
        assertTrue(r.getId().startsWith("R"));
        assertEquals("item", r.getName());

        PaletteDto p = service.getPalette();
        assertEquals(1, p.getRealms().size());

        p = service.removeRealm(r.getId());
        assertEquals(0, p.getRealms().size());
    }

    @Test
    void addComponentRequiresExistingRealm() {
        RealmDto r = service.addRealm("area");
        ComponentDto c = service.addComponent("field", r.getId());
        assertNotNull(c.getId());
        assertTrue(c.getId().startsWith("C"));
        assertEquals("field", c.getName());
        assertEquals(r.getId(), c.getRealmId());

        PaletteDto p = service.getPalette();
        assertEquals(1, p.getComponents().size());
    }

    @Test
    void addComponentWithMissingRealmThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                service.addComponent("orphan", "R_MISSING"));
    }

    @Test
    void addUnaryOperator() {
        RealmDto r = service.addRealm("item");
        ComponentDto c = service.addComponent("player", r.getId());

        OperatorDto op = service.addOperator("spawn", 1,
                List.of(List.of(c.getId())),
                new OperatorContractDto(false, false, false, false, false,
                        false, false, false, false, false,
                        false, false, false, false));

        assertNotNull(op.getId());
        assertTrue(op.getId().startsWith("F"));
        assertEquals("spawn", op.getName());
        assertEquals(1, op.getOperandCount());
        assertEquals(1, op.getOperands().size());
        assertEquals(c.getId(), op.getOperands().get(0).get(0));
    }

    @Test
    void addBinaryOperator() {
        RealmDto r = service.addRealm("area");
        ComponentDto a = service.addComponent("field", r.getId());
        ComponentDto b = service.addComponent("explosion", r.getId());

        OperatorDto op = service.addOperator("spread", 2,
                List.of(List.of(b.getId()), List.of(a.getId())),
                new OperatorContractDto(false, false, false, false, false,
                        false, false, false, false, false,
                        false, false, false, false));

        assertEquals(2, op.getOperandCount());
        assertEquals(2, op.getOperands().size());
        assertEquals(b.getId(), op.getOperands().get(0).get(0));
        assertEquals(a.getId(), op.getOperands().get(1).get(0));
    }

    @Test
    void addTernaryOperator() {
        RealmDto item = service.addRealm("item");
        RealmDto coord = service.addRealm("coordinate");
        ComponentDto player = service.addComponent("player", item.getId());
        ComponentDto wall = service.addComponent("wall", item.getId());
        ComponentDto time = service.addComponent("granular_time", coord.getId());

        OperatorDto op = service.addOperator("presence", 3,
                List.of(List.of(player.getId(), wall.getId()), List.of(time.getId()), List.of()),
                new OperatorContractDto(false, false, false, false, false,
                        false, false, false, false, false,
                        false, false, false, false));

        assertEquals(3, op.getOperandCount());
        assertEquals(3, op.getOperands().size());
        assertEquals(2, op.getOperands().get(0).size());
        assertEquals(1, op.getOperands().get(1).size());
        assertEquals(0, op.getOperands().get(2).size());
    }

    @Test
    void addOperatorWithContract() {
        RealmDto r = service.addRealm("item");
        ComponentDto a = service.addComponent("bomb", r.getId());
        ComponentDto b = service.addComponent("field", r.getId());

        OperatorContractDto contract = new OperatorContractDto(
                false, true, false, false, false,
                false, false, false, false, false,
                false, false, false, false);

        OperatorDto op = service.addOperator("plantBomb", 2,
                List.of(List.of(a.getId()), List.of(b.getId())),
                contract);

        assertTrue(op.getContract().isIrreflexive());
        assertFalse(op.getContract().isReflexive());
    }

    @Test
    void addOperatorWithMissingComponentThrows() {
        RealmDto r = service.addRealm("item");
        service.addComponent("player", r.getId());

        assertThrows(IllegalArgumentException.class, () ->
                service.addOperator("bad", 1,
                        List.of(List.of("C_MISSING")),
                        new OperatorContractDto(false, false, false, false, false,
                                false, false, false, false, false,
                                false, false, false, false)));
    }

    @Test
    void addArtifactForExistingEntity() {
        RealmDto r = service.addRealm("item");
        ArtifactDto a = service.addArtifact(r.getId(), "Tangible game objects");

        assertEquals(r.getId(), a.getEntityId());
        assertEquals("Tangible game objects", a.getDescription());

        List<ArtifactDto> artifacts = service.getArtifacts();
        assertEquals(1, artifacts.size());
    }

    @Test
    void addArtifactForMissingEntityThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                service.addArtifact("MISSING", "desc"));
    }

    @Test
    void removeArtifact() {
        RealmDto r = service.addRealm("item");
        service.addArtifact(r.getId(), "desc");
        assertEquals(1, service.getArtifacts().size());

        service.removeArtifact(r.getId());
        assertEquals(0, service.getArtifacts().size());
    }

    @Test
    void buildAndValidateCoherentDefinition() {
        // Realms
        RealmDto item = service.addRealm("item");
        RealmDto area = service.addRealm("area");
        RealmDto coord = service.addRealm("coordinate");

        // Components
        ComponentDto player = service.addComponent("player", item.getId());
        ComponentDto wall = service.addComponent("wall", item.getId());
        ComponentDto enemy = service.addComponent("enemy", item.getId());
        ComponentDto bomb = service.addComponent("bomb", item.getId());
        ComponentDto field = service.addComponent("field", area.getId());
        ComponentDto explosion = service.addComponent("explosion", area.getId());
        ComponentDto time = service.addComponent("granular_time", coord.getId());
        ComponentDto location = service.addComponent("location", coord.getId());

        // Operators
        service.addOperator("place", 2,
                List.of(List.of(field.getId()), List.of(location.getId())),
                defaultContract());
        service.addOperator("occupy", 2,
                List.of(List.of(player.getId(), wall.getId(), enemy.getId()), List.of(field.getId())),
                defaultContract());
        service.addOperator("presence", 3,
                List.of(List.of(player.getId(), wall.getId(), enemy.getId(), bomb.getId(), field.getId(), explosion.getId()),
                        List.of(time.getId()), List.of()),
                defaultContract());
        service.addOperator("collide", 2,
                List.of(List.of(player.getId(), wall.getId(), enemy.getId(), bomb.getId()),
                        List.of(player.getId(), wall.getId(), enemy.getId(), bomb.getId())),
                defaultContract());
        service.addOperator("spawn", 1,
                List.of(List.of(player.getId(), enemy.getId())),
                defaultContract());

        // Artifacts for every entity
        for (RealmDto r : List.of(item, area, coord)) {
            service.addArtifact(r.getId(), "Realm: " + r.getName());
        }
        for (ComponentDto c : List.of(player, wall, enemy, bomb, field, explosion, time, location)) {
            service.addArtifact(c.getId(), "Component: " + c.getName());
        }
        PaletteDto palette = service.getPalette();
        for (OperatorDto o : palette.getOperators()) {
            service.addArtifact(o.getId(), "Operator: " + o.getName());
        }

        // Build
        GameDefinitionDto gd = service.buildGameDefinition("bomberman");
        assertNotNull(gd);
        assertEquals("bomberman", gd.getId());
        assertEquals(3, gd.getRealms().size());
        assertEquals(8, gd.getComponents().size());
        assertEquals(5, gd.getOperators().size());
        assertEquals(3 + 8 + 5, gd.getArtifacts().size());

        // Validate
        ValidationResultDto result = service.validate();
        assertTrue(result.isValid(), "Expected valid, got errors: " + result.getErrors());
    }

    @Test
    void validateDetectsMissingArtifact() {
        RealmDto r = service.addRealm("item");
        ComponentDto c = service.addComponent("player", r.getId());
        service.addOperator("spawn", 1,
                List.of(List.of(c.getId())), defaultContract());

        // Only add artifact for realm, not component or operator
        service.addArtifact(r.getId(), "desc");

        service.buildGameDefinition("incomplete");
        ValidationResultDto result = service.validate();

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("has no artifact")),
                "Should report missing artifacts, got: " + result.getErrors());
    }

    @Test
    void validateDetectsMissingRealm() {
        // Add a component referencing a realm that's not in the palette
        // We do this by adding a realm, adding a component, then removing the realm
        RealmDto r = service.addRealm("item");
        ComponentDto c = service.addComponent("player", r.getId());
        service.addArtifact(r.getId(), "desc");
        service.addArtifact(c.getId(), "desc");

        // Remove the realm — this leaves the component dangling
        service.removeRealm(r.getId());

        service.buildGameDefinition("broken");
        ValidationResultDto result = service.validate();

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("references missing realm")),
                "Should report missing realm, got: " + result.getErrors());
    }

    @Test
    void validateWithoutDefinitionReturnsError() {
        ValidationResultDto result = service.validate();
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertTrue(result.getErrors().get(0).contains("No GameDefinition"));
    }

    @Test
    void clearPaletteResetsEverything() {
        RealmDto r = service.addRealm("item");
        service.addArtifact(r.getId(), "desc");
        service.buildGameDefinition("test");

        service.clearPalette();

        PaletteDto p = service.getPalette();
        assertEquals(0, p.getRealms().size());
        assertEquals(0, p.getComponents().size());
        assertEquals(0, p.getOperators().size());
        assertEquals(0, service.getArtifacts().size());
        assertNull(service.getGameDefinition());
    }

    @Test
    void getGameDefinitionReturnsNullWhenNotBuilt() {
        assertNull(service.getGameDefinition());
    }

    @Test
    void clearGameDefinition() {
        RealmDto r = service.addRealm("item");
        service.addArtifact(r.getId(), "desc");
        service.buildGameDefinition("test");
        assertNotNull(service.getGameDefinition());

        service.clearGameDefinition();
        assertNull(service.getGameDefinition());
    }

    private static OperatorContractDto defaultContract() {
        return new OperatorContractDto(false, false, false, false, false,
                false, false, false, false, false,
                false, false, false, false);
    }
}
