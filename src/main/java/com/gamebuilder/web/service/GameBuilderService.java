package com.gamebuilder.web.service;

import com.gamebuilder.domain.Artifact;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.gamedef.GameDefinition;
import com.gamebuilder.gamedef.GameDefinitionValidator;
import com.gamebuilder.gamedef.ValidationResult;
import com.gamebuilder.operator.*;
import com.gamebuilder.palette.Palette;
import com.gamebuilder.utils.BijectMap;
import com.gamebuilder.web.model.*;
import com.gamebuilder.web.state.GameBuilderState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GameBuilderService {

    @Inject
    GameBuilderState state;

    // ── Palette ──────────────────────────────────────────────────────

    public PaletteDto getPalette() {
        return toDto(state.getPalette());
    }

    public PaletteDto clearPalette() {
        state.clearPalette();
        return toDto(state.getPalette());
    }

    public PaletteDto resetPalette() {
        state.resetPalette();
        return toDto(state.getPalette());
    }

    public PaletteDto setPaletteId(String id) {
        Palette p = state.getPalette();
        Palette updated = p.withId(id);
        state.setPalette(updated);
        return toDto(updated);
    }

    // ── Realms ───────────────────────────────────────────────────────

    public RealmDto addRealm(String name) {
        Realm realm = state.getPainter().realm(name);
        state.getPalette().add(realm);
        return toDto(realm);
    }

    public PaletteDto removeRealm(String id) {
        state.getPalette().remove(id);
        return toDto(state.getPalette());
    }

    // ── Components ───────────────────────────────────────────────────

    public ComponentDto addComponent(String name, String realmId) {
        Realm realm = state.getPalette().getRealms().getByKey(realmId);
        if (realm == null) {
            throw new IllegalArgumentException("Realm not found: " + realmId);
        }
        Component component = state.getPainter().component(name, realm);
        state.getPalette().add(component);
        return toDto(component);
    }

    public PaletteDto removeComponent(String id) {
        state.getPalette().remove(id);
        return toDto(state.getPalette());
    }

    // ── Operators ────────────────────────────────────────────────────

    public OperatorDto addOperator(String name, int operandCount,
                                   List<List<String>> operandIds,
                                   OperatorContractDto contractDto) {
        Palette palette = state.getPalette();

        List<List<Component>> resolvedOperands = new ArrayList<>();
        for (List<String> slot : operandIds) {
            List<Component> slotComponents = new ArrayList<>();
            for (String compId : slot) {
                Component c = palette.getComponents().getByKey(compId);
                if (c == null) {
                    throw new IllegalArgumentException("Component not found: " + compId);
                }
                slotComponents.add(c);
            }
            resolvedOperands.add(slotComponents);
        }

        OperatorContract contract = toDomain(contractDto);
        Operator operator;

        switch (operandCount) {
            case 1 -> {
                if (resolvedOperands.size() != 1) {
                    throw new IllegalArgumentException("Unary operator requires exactly 1 operand slot");
                }
                operator = state.getPainter().operator()
                        .unary()
                        .name(name)
                        .addOperands(resolvedOperands.get(0))
                        .contract()
                            .withReflexive(contract.isReflexive())
                            .withIrreflexive(contract.isIrreflexive())
                            .withAntisymmetric(contract.isAntisymmetric())
                            .withAsymmetric(contract.isAsymmetric())
                            .withIdempotent(contract.isIdempotent())
                            .withInvolutive(contract.isInvolutive())
                            .withMonotonic(contract.isMonotonic())
                            .withAssociative(contract.isAssociative())
                            .withCancellative(contract.isCancellative())
                            .withDistributive(contract.isDistributive())
                            .withTransitive(contract.isTransitive())
                            .withIdentityElement(contract.isIdentityElement())
                            .withInverseElement(contract.isInverseElement())
                            .withAbsorbingElement(contract.isAbsorbingElement())
                            .buildContract()
                        .build();
            }
            case 2 -> {
                if (resolvedOperands.size() != 2) {
                    throw new IllegalArgumentException("Binary operator requires exactly 2 operand slots");
                }
                operator = state.getPainter().operator()
                        .binary()
                        .name(name)
                        .addLhsOperands(resolvedOperands.get(0))
                        .addRhsOperands(resolvedOperands.get(1))
                        .contract()
                            .withReflexive(contract.isReflexive())
                            .withIrreflexive(contract.isIrreflexive())
                            .withAntisymmetric(contract.isAntisymmetric())
                            .withAsymmetric(contract.isAsymmetric())
                            .withIdempotent(contract.isIdempotent())
                            .withInvolutive(contract.isInvolutive())
                            .withMonotonic(contract.isMonotonic())
                            .withAssociative(contract.isAssociative())
                            .withCancellative(contract.isCancellative())
                            .withDistributive(contract.isDistributive())
                            .withTransitive(contract.isTransitive())
                            .withIdentityElement(contract.isIdentityElement())
                            .withInverseElement(contract.isInverseElement())
                            .withAbsorbingElement(contract.isAbsorbingElement())
                            .buildContract()
                        .build();
            }
            case 3 -> {
                if (resolvedOperands.size() != 3) {
                    throw new IllegalArgumentException("Ternary operator requires exactly 3 operand slots");
                }
                operator = state.getPainter().operator()
                        .ternary()
                        .name(name)
                        .addLeftOperands(resolvedOperands.get(0))
                        .addMiddleOperands(resolvedOperands.get(1))
                        .addRightOperands(resolvedOperands.get(2))
                        .contract()
                            .withReflexive(contract.isReflexive())
                            .withIrreflexive(contract.isIrreflexive())
                            .withAntisymmetric(contract.isAntisymmetric())
                            .withAsymmetric(contract.isAsymmetric())
                            .withIdempotent(contract.isIdempotent())
                            .withInvolutive(contract.isInvolutive())
                            .withMonotonic(contract.isMonotonic())
                            .withAssociative(contract.isAssociative())
                            .withCancellative(contract.isCancellative())
                            .withDistributive(contract.isDistributive())
                            .withTransitive(contract.isTransitive())
                            .withIdentityElement(contract.isIdentityElement())
                            .withInverseElement(contract.isInverseElement())
                            .withAbsorbingElement(contract.isAbsorbingElement())
                            .buildContract()
                        .build();
            }
            default -> throw new IllegalArgumentException("Operand count must be 1, 2, or 3");
        }

        state.getPalette().add(operator);
        return toDto(operator);
    }

    public PaletteDto removeOperator(String id) {
        state.getPalette().remove(id);
        return toDto(state.getPalette());
    }

    // ── Artifacts ────────────────────────────────────────────────────

    public ArtifactDto addArtifact(String entityId, String description) {
        Palette palette = state.getPalette();
        if (palette.findEntity(entityId) == null) {
            throw new IllegalArgumentException("Entity not found: " + entityId);
        }
        Artifact artifact = new Artifact(entityId, description);
        state.getArtifactBuffer().put(entityId, artifact);
        return toDto(artifact);
    }

    public List<ArtifactDto> getArtifacts() {
        List<ArtifactDto> result = new ArrayList<>();
        for (Artifact a : state.getArtifactBuffer().values()) {
            result.add(toDto(a));
        }
        return result;
    }

    public void removeArtifact(String entityId) {
        state.getArtifactBuffer().removeByKey(entityId);
    }

    // ── GameDefinition ───────────────────────────────────────────────

    public GameDefinitionDto buildGameDefinition(String id) {
        Palette palette = state.getPalette();
        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        artifacts.putAll(state.getArtifactBuffer());

        GameDefinition gd = new GameDefinition(
                id,
                palette.getComponents(),
                palette.getRealms(),
                palette.getOperators(),
                artifacts
        );

        state.setGameDefinition(gd);
        return toDto(gd);
    }

    public GameDefinitionDto getGameDefinition() {
        GameDefinition gd = state.getGameDefinition();
        if (gd == null) {
            return null;
        }
        return toDto(gd);
    }

    public void clearGameDefinition() {
        state.clearGameDefinition();
    }

    // ── Compose (ephemeral) ──────────────────────────────────────────

    public ComposeResultDto compose(String id, List<String> realmIds,
                                    List<String> componentIds, List<String> operatorIds) {
        Palette palette = state.getPalette();

        BijectMap<String, Realm> selectedRealms = new BijectMap<>();
        for (String rid : realmIds) {
            Realm r = palette.getRealms().getByKey(rid);
            if (r == null) {
                throw new IllegalArgumentException("Realm not found: " + rid);
            }
            selectedRealms.put(rid, r);
        }

        BijectMap<String, Component> selectedComponents = new BijectMap<>();
        for (String cid : componentIds) {
            Component c = palette.getComponents().getByKey(cid);
            if (c == null) {
                throw new IllegalArgumentException("Component not found: " + cid);
            }
            selectedComponents.put(cid, c);
        }

        BijectMap<String, Operator> selectedOperators = new BijectMap<>();
        for (String oid : operatorIds) {
            Operator o = palette.getOperators().getByKey(oid);
            if (o == null) {
                throw new IllegalArgumentException("Operator not found: " + oid);
            }
            selectedOperators.put(oid, o);
        }

        // Auto-match artifacts from buffer
        BijectMap<String, Artifact> artifacts = new BijectMap<>();
        for (String rid : realmIds) {
            Artifact a = state.getArtifactBuffer().getByKey(rid);
            if (a != null) artifacts.put(rid, a);
        }
        for (String cid : componentIds) {
            Artifact a = state.getArtifactBuffer().getByKey(cid);
            if (a != null) artifacts.put(cid, a);
        }
        for (String oid : operatorIds) {
            Artifact a = state.getArtifactBuffer().getByKey(oid);
            if (a != null) artifacts.put(oid, a);
        }

        GameDefinition gd = new GameDefinition(id, selectedComponents, selectedRealms, selectedOperators, artifacts);
        GameDefinitionDto gdDto = toDto(gd);
        ValidationResultDto validation = validateDefinition(gd);

        return new ComposeResultDto(gdDto, validation);
    }

    // ── Validation ───────────────────────────────────────────────────

    public ValidationResultDto validate() {
        GameDefinition gd = state.getGameDefinition();
        if (gd == null) {
            return ValidationResultDto.invalid(List.of("No GameDefinition has been built yet"));
        }
        return validateDefinition(gd);
    }

    private ValidationResultDto validateDefinition(GameDefinition gd) {
        ValidationResult result = new GameDefinitionValidator(gd).validate();
        if (result.isValid()) {
            return ValidationResultDto.valid();
        }
        return ValidationResultDto.invalid(result.getErrors());
    }

    // ── DTO conversion helpers ───────────────────────────────────────

    private PaletteDto toDto(Palette palette) {
        List<RealmDto> realmDtos = new ArrayList<>();
        for (Realm r : palette.getRealms().values()) {
            realmDtos.add(toDto(r));
        }
        List<ComponentDto> componentDtos = new ArrayList<>();
        for (Component c : palette.getComponents().values()) {
            componentDtos.add(toDto(c));
        }
        List<OperatorDto> operatorDtos = new ArrayList<>();
        for (Operator o : palette.getOperators().values()) {
            operatorDtos.add(toDto(o));
        }
        return new PaletteDto(palette.getId(), realmDtos, componentDtos, operatorDtos);
    }

    private GameDefinitionDto toDto(GameDefinition gd) {
        List<RealmDto> realmDtos = new ArrayList<>();
        for (Realm r : gd.getRealms().values()) {
            realmDtos.add(toDto(r));
        }
        List<ComponentDto> componentDtos = new ArrayList<>();
        for (Component c : gd.getComponents().values()) {
            componentDtos.add(toDto(c));
        }
        List<OperatorDto> operatorDtos = new ArrayList<>();
        for (Operator o : gd.getOperators().values()) {
            operatorDtos.add(toDto(o));
        }
        List<ArtifactDto> artifactDtos = new ArrayList<>();
        for (Artifact a : gd.getArtifacts().values()) {
            artifactDtos.add(toDto(a));
        }
        return new GameDefinitionDto(gd.getId(), realmDtos, componentDtos, operatorDtos, artifactDtos);
    }

    private RealmDto toDto(Realm r) {
        return new RealmDto(r.getId(), r.getName());
    }

    private ComponentDto toDto(Component c) {
        return new ComponentDto(c.getId(), c.getName(), c.getRealm().getId());
    }

    private OperatorDto toDto(Operator o) {
        List<List<String>> operandIds = new ArrayList<>();
        for (List<Component> slot : o.getOperands()) {
            List<String> ids = new ArrayList<>();
            for (Component c : slot) {
                ids.add(c.getId());
            }
            operandIds.add(ids);
        }
        return new OperatorDto(o.getId(), o.getName(), o.getOperandCount(), operandIds, toDto(o.getContract()));
    }

    private OperatorContractDto toDto(OperatorContract contract) {
        return new OperatorContractDto(
                contract.isReflexive(),
                contract.isIrreflexive(),
                contract.isAntisymmetric(),
                contract.isAsymmetric(),
                contract.isIdempotent(),
                contract.isInvolutive(),
                contract.isMonotonic(),
                contract.isAssociative(),
                contract.isCancellative(),
                contract.isDistributive(),
                contract.isTransitive(),
                contract.isIdentityElement(),
                contract.isInverseElement(),
                contract.isAbsorbingElement()
        );
    }

    private ArtifactDto toDto(Artifact a) {
        return new ArtifactDto(a.getEntityId(), a.getDescription());
    }

    private OperatorContract toDomain(OperatorContractDto dto) {
        return new OperatorContract(
                dto.isReflexive(),
                dto.isIrreflexive(),
                dto.isAntisymmetric(),
                dto.isAsymmetric(),
                dto.isIdempotent(),
                dto.isInvolutive(),
                dto.isMonotonic(),
                dto.isAssociative(),
                dto.isCancellative(),
                dto.isDistributive(),
                dto.isTransitive(),
                dto.isIdentityElement(),
                dto.isInverseElement(),
                dto.isAbsorbingElement()
        );
    }
}
