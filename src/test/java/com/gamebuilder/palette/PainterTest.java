package com.gamebuilder.palette;

import com.gamebuilder.domain.AppConfig;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.BinaryOperator;
import com.gamebuilder.operator.Operator;
import com.gamebuilder.operator.TernaryOperator;
import com.gamebuilder.operator.UnaryOperator;
import com.gamebuilder.utils.IdProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PainterTest {

    private AppConfig appConfig;
    private Painter painter;
    private Realm realm;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig(4);
        painter = new Painter(appConfig.getIdProvider());
        realm = painter.realm("test-realm");
    }

    @Test
    void realmHasCorrectPrefix() {
        assertTrue(realm.getId().startsWith("R"));
    }

    @Test
    void realmHasCorrectName() {
        assertEquals("test-realm", realm.getName());
    }

    @Test
    void componentHasCorrectPrefix() {
        Component c = painter.component("test-comp", realm);
        assertTrue(c.getId().startsWith("C"));
    }

    @Test
    void componentHasCorrectNameAndRealm() {
        Component c = painter.component("test-comp", realm);
        assertEquals("test-comp", c.getName());
        assertEquals(realm, c.getRealm());
    }

    @Test
    void componentNullNameThrows() {
        assertThrows(NullPointerException.class, () -> painter.component(null, realm));
    }

    @Test
    void componentNullRealmThrows() {
        assertThrows(NullPointerException.class, () -> painter.component("name", null));
    }

    @Test
    void realmNullNameThrows() {
        assertThrows(NullPointerException.class, () -> painter.realm(null));
    }

    @Test
    void unaryOperatorBuilds() {
        Operator op = painter.operator()
                .unary()
                .name("test-unary")
                .addOperands(painter.component("a", realm))
                .build();
        assertTrue(op instanceof UnaryOperator);
        assertEquals(1, op.getOperandCount());
        assertTrue(op.getId().startsWith("F"));
    }

    @Test
    void binaryOperatorBuilds() {
        Operator op = painter.operator()
                .binary()
                .name("test-binary")
                .addLhsOperands(painter.component("a", realm))
                .addRhsOperands(painter.component("b", realm))
                .build();
        assertTrue(op instanceof BinaryOperator);
        assertEquals(2, op.getOperandCount());
        assertTrue(op.getId().startsWith("F"));
    }

    @Test
    void ternaryOperatorBuilds() {
        Operator op = painter.operator()
                .ternary()
                .name("test-ternary")
                .addLeftOperands(painter.component("a", realm))
                .addMiddleOperands(painter.component("b", realm))
                .addRightOperands(painter.component("c", realm))
                .build();
        assertTrue(op instanceof TernaryOperator);
        assertEquals(3, op.getOperandCount());
        assertTrue(op.getId().startsWith("F"));
    }

    @Test
    void binaryOperatorVarargsAndListWork() {
        Component a = painter.component("a", realm);
        Component b = painter.component("b", realm);
        Component c = painter.component("c", realm);

        Operator op1 = painter.operator()
                .binary()
                .name("op1")
                .addLhsOperands(a, b)
                .addRhsOperands(List.of(c))
                .build();

        Operator op2 = painter.operator()
                .binary()
                .name("op2")
                .addLhsOperands(List.of(a))
                .addRhsOperands(b, c)
                .build();

        assertEquals(2, op1.getOperands().get(0).size());
        assertEquals(1, op1.getOperands().get(1).size());
        assertEquals(1, op2.getOperands().get(0).size());
        assertEquals(2, op2.getOperands().get(1).size());
    }

    @Test
    void contractBuilderChainsBack() {
        Operator op = painter.operator()
                .binary()
                .name("contracted")
                .addLhsOperands(painter.component("a", realm))
                .addRhsOperands(painter.component("b", realm))
                .contract()
                    .withAssociative(true)
                    .withIdempotent(true)
                    .buildContract()
                .build();

        assertTrue(op.getContract().isAssociative());
        assertTrue(op.getContract().isIdempotent());
    }

    @Test
    void operatorWithoutContractDefaultsToAllFalse() {
        Operator op = painter.operator()
                .unary()
                .name("default-contract")
                .addOperands(painter.component("a", realm))
                .build();

        assertFalse(op.getContract().isReflexive());
        assertFalse(op.getContract().isAssociative());
        assertFalse(op.getContract().isIdempotent());
    }

    @Test
    void builderNullChecks() {
        assertThrows(NullPointerException.class, () ->
            painter.operator().unary().name(null));
        assertThrows(NullPointerException.class, () ->
            painter.operator().binary().addLhsOperands((List<Component>) null));
        assertThrows(NullPointerException.class, () ->
            painter.operator().ternary().addLeftOperands((Component[]) null));
    }
}
