package com.gamebuilder.palette;

import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Entity;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.BinaryOperator;
import com.gamebuilder.operator.Operator;
import com.gamebuilder.operator.OperatorContract;
import com.gamebuilder.operator.UnaryOperator;
import com.gamebuilder.utils.BijectMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaletteTest {

    private Palette palette;
    private Realm realm;
    private Component component;
    private Operator operator;

    @BeforeEach
    void setUp() {
        palette = new Palette("test-palette");
        realm = new Realm("R0001", "test-realm");
        component = new Component("C0001", "test-component", realm);
        operator = new UnaryOperator("F0001", "test-op", List.of(component), new OperatorContract(
                false, false, false, false, false,
                false, false, false, false, false,
                false, false, false));
    }

    @Test
    void hasId() {
        assertEquals("test-palette", palette.getId());
    }

    @Test
    void addComponent() {
        palette.add(component);
        assertEquals(component, palette.findEntity("C0001"));
    }

    @Test
    void addRealm() {
        palette.add(realm);
        assertEquals(realm, palette.findEntity("R0001"));
    }

    @Test
    void addOperator() {
        palette.add(operator);
        assertEquals(operator, palette.findEntity("F0001"));
    }

    @Test
    void addUnsupportedTypeThrows() {
        class FakeEntity implements Entity {
            @Override public String getId() { return "X1"; }
            @Override public String getName() { return "fake"; }
        }
        assertThrows(IllegalArgumentException.class, () -> palette.add(new FakeEntity()));
    }

    @Test
    void findEntityReturnsNullForMissing() {
        assertNull(palette.findEntity("MISSING"));
    }

    @Test
    void findEntitySearchesAllMaps() {
        palette.add(realm);
        palette.add(component);
        palette.add(operator);
        assertEquals(realm, palette.findEntity("R0001"));
        assertEquals(component, palette.findEntity("C0001"));
        assertEquals(operator, palette.findEntity("F0001"));
    }

    @Test
    void removeById() {
        palette.add(component);
        palette.remove("C0001");
        assertNull(palette.findEntity("C0001"));
    }

    @Test
    void removeByEntity() {
        palette.add(component);
        palette.remove(component);
        assertNull(palette.findEntity("C0001"));
    }

    @Test
    void clearEmptiesAll() {
        palette.add(realm);
        palette.add(component);
        palette.add(operator);
        palette.clear();
        assertNull(palette.findEntity("R0001"));
        assertNull(palette.findEntity("C0001"));
        assertNull(palette.findEntity("F0001"));
    }

    @Test
    void addAll() {
        palette.addAll(List.of(realm, component, operator));
        assertEquals(realm, palette.findEntity("R0001"));
        assertEquals(component, palette.findEntity("C0001"));
        assertEquals(operator, palette.findEntity("F0001"));
    }

    @Test
    void gettersReturnDefensiveCopies() {
        palette.add(component);
        BijectMap<String, Component> copy = palette.getComponents();
        copy.put("C0002", new Component("C0002", "injected", realm));
        assertNull(palette.findEntity("C0002"));
    }

    @Test
    void removeByIdRemovesFromAllMaps() {
        palette.add(component);
        palette.add(realm);
        palette.add(operator);
        palette.remove("C0001");
        assertNull(palette.findEntity("C0001"));
        assertEquals(realm, palette.findEntity("R0001"));
        assertEquals(operator, palette.findEntity("F0001"));
    }

    @Test
    void withIdCreatesCopy() {
        palette.add(component);
        Palette copy = palette.withId("other-id");
        assertEquals("other-id", copy.getId());
        assertEquals(component, copy.findEntity("C0001"));
        assertEquals("test-palette", palette.getId());
    }
}
