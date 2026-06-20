package com.gamebuilder.web.resource;

import com.gamebuilder.ai.*;
import com.gamebuilder.web.model.*;
import com.gamebuilder.web.service.GameBuilderService;
import com.gamebuilder.web.InputSanitizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/gamebuilder")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameBuilderController {

    private static final Logger LOG = Logger.getLogger(GameBuilderController.class);

    @Inject
    GameBuilderService service;

    @Inject
    OllamaClient ollamaClient;

    @Inject
    @ConfigProperty(name = "ollama.max.user.prompt.length", defaultValue = "1000")
    int maxUserPromptLength;

    // ── Palette ──────────────────────────────────────────────────────

    @GET
    @Path("/palette")
    public PaletteDto getPalette() {
        return service.getPalette();
    }

    @DELETE
    @Path("/palette")
    public PaletteDto clearPalette() {
        LOG.info("Clearing palette");
        return service.clearPalette();
    }

    @POST
    @Path("/palette/reset")
    public PaletteDto resetPalette() {
        LOG.info("Resetting palette to Bomberman defaults");
        return service.resetPalette();
    }

    @PUT
    @Path("/palette/id")
    public PaletteDto setPaletteId(Map<String, String> body) {
        String id = InputSanitizer.sanitize(body.get("id"));
        if (id == null || id.isBlank()) {
            throw new BadRequestException("id is required");
        }
        LOG.infof("Setting palette id to: %s", id);
        return service.setPaletteId(id);
    }

    // ── Realms ───────────────────────────────────────────────────────

    @POST
    @Path("/realms")
    public Response addRealm(Map<String, String> body) {
        String name = InputSanitizer.sanitize(body.get("name"));
        if (name == null || name.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "name is required"))
                    .build();
        }
        RealmDto realm = service.addRealm(name);
        LOG.infof("Added realm: %s (%s)", realm.getName(), realm.getId());
        return Response.status(Response.Status.CREATED).entity(realm).build();
    }

    @DELETE
    @Path("/realms/{id}")
    public PaletteDto removeRealm(@PathParam("id") String id) {
        id = InputSanitizer.sanitize(id);
        LOG.infof("Removing realm: %s", id);
        return service.removeRealm(id);
    }

    // ── Components ───────────────────────────────────────────────────

    @POST
    @Path("/components")
    public Response addComponent(Map<String, String> body) {
        String name = InputSanitizer.sanitize(body.get("name"));
        String realmId = InputSanitizer.sanitize(body.get("realmId"));
        if (name == null || name.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "name is required"))
                    .build();
        }
        if (realmId == null || realmId.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "realmId is required"))
                    .build();
        }
        try {
            ComponentDto component = service.addComponent(name, realmId);
            LOG.infof("Added component: %s (%s) in realm %s", component.getName(), component.getId(), realmId);
            return Response.status(Response.Status.CREATED).entity(component).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/components/{id}")
    public PaletteDto removeComponent(@PathParam("id") String id) {
        id = InputSanitizer.sanitize(id);
        LOG.infof("Removing component: %s", id);
        return service.removeComponent(id);
    }

    // ── Operators ────────────────────────────────────────────────────

    @POST
    @Path("/operators")
    @SuppressWarnings("unchecked")
    public Response addOperator(Map<String, Object> body) {
        String name = InputSanitizer.sanitize((String) body.get("name"));
        if (name == null || name.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "name is required"))
                    .build();
        }

        int operandCount;
        if (body.containsKey("operandCount")) {
            operandCount = ((Number) body.get("operandCount")).intValue();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "operandCount is required (1, 2, or 3)"))
                    .build();
        }

        List<List<String>> operands;
        if (body.containsKey("operands") && body.get("operands") instanceof List) {
            operands = (List<List<String>>) body.get("operands");
            // sanitize all operand IDs
            for (List<String> slot : operands) {
                for (int i = 0; i < slot.size(); i++) {
                    slot.set(i, InputSanitizer.sanitize(slot.get(i)));
                }
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "operands is required (list of lists of component IDs)"))
                    .build();
        }

        OperatorContractDto contract = new OperatorContractDto(
                false, false, false, false, false,
                false, false, false, false, false,
                false, false, false, false
        );
        if (body.containsKey("contract") && body.get("contract") instanceof Map) {
            Map<String, Object> c = (Map<String, Object>) body.get("contract");
            contract = new OperatorContractDto(
                    bool(c, "reflexive"), bool(c, "irreflexive"),
                    bool(c, "antisymmetric"), bool(c, "asymmetric"),
                    bool(c, "idempotent"), bool(c, "involutive"),
                    bool(c, "monotonic"), bool(c, "associative"),
                    bool(c, "cancellative"), bool(c, "distributive"),
                    bool(c, "transitive"),
                    bool(c, "identityElement"), bool(c, "inverseElement"),
                    bool(c, "absorbingElement")
            );
        }

        try {
            OperatorDto operator = service.addOperator(name, operandCount, operands, contract);
            LOG.infof("Added operator: %s (%s) arity=%d", operator.getName(), operator.getId(), operandCount);
            return Response.status(Response.Status.CREATED).entity(operator).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/operators/{id}")
    public PaletteDto removeOperator(@PathParam("id") String id) {
        id = InputSanitizer.sanitize(id);
        LOG.infof("Removing operator: %s", id);
        return service.removeOperator(id);
    }

    // ── Artifacts ────────────────────────────────────────────────────

    @POST
    @Path("/artifacts")
    public Response addArtifact(Map<String, String> body) {
        String entityId = InputSanitizer.sanitize(body.get("entityId"));
        String description = InputSanitizer.sanitize(body.get("description"));
        if (entityId == null || entityId.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "entityId is required"))
                    .build();
        }
        if (description == null || description.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "description is required"))
                    .build();
        }
        try {
            ArtifactDto artifact = service.addArtifact(entityId, description);
            LOG.infof("Added artifact for entity: %s", entityId);
            return Response.status(Response.Status.CREATED).entity(artifact).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/artifacts")
    public List<ArtifactDto> getArtifacts() {
        return service.getArtifacts();
    }

    @DELETE
    @Path("/artifacts/{entityId}")
    public Response removeArtifact(@PathParam("entityId") String entityId) {
        entityId = InputSanitizer.sanitize(entityId);
        service.removeArtifact(entityId);
        LOG.infof("Removed artifact for entity: %s", entityId);
        return Response.noContent().build();
    }

    // ── GameDefinition ───────────────────────────────────────────────

    @POST
    @Path("/gamedef")
    public Response buildGameDefinition(Map<String, String> body) {
        String id = InputSanitizer.sanitize(body.get("id"));
        if (id == null || id.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "id is required"))
                    .build();
        }
        GameDefinitionDto gd = service.buildGameDefinition(id);
        LOG.infof("Built GameDefinition: %s", id);
        return Response.status(Response.Status.CREATED).entity(gd).build();
    }

    @GET
    @Path("/gamedef")
    public Response getGameDefinition() {
        GameDefinitionDto gd = service.getGameDefinition();
        if (gd == null) {
            return Response.noContent().build();
        }
        return Response.ok(gd).build();
    }

    @DELETE
    @Path("/gamedef")
    public Response clearGameDefinition() {
        service.clearGameDefinition();
        LOG.info("Cleared GameDefinition");
        return Response.noContent().build();
    }

    // ── Compose (ephemeral) ──────────────────────────────────────────

    @POST
    @Path("/compose")
    @SuppressWarnings("unchecked")
    public Response compose(Map<String, Object> body) {
        String id = InputSanitizer.sanitize((String) body.get("id"));
        if (id == null || id.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "id is required"))
                    .build();
        }

        List<String> realmIds = body.containsKey("realmIds") && body.get("realmIds") instanceof List
                ? sanitizeStringList((List<String>) body.get("realmIds")) : List.of();
        List<String> componentIds = body.containsKey("componentIds") && body.get("componentIds") instanceof List
                ? sanitizeStringList((List<String>) body.get("componentIds")) : List.of();
        List<String> operatorIds = body.containsKey("operatorIds") && body.get("operatorIds") instanceof List
                ? sanitizeStringList((List<String>) body.get("operatorIds")) : List.of();

        try {
            ComposeResultDto result = service.compose(id, realmIds, componentIds, operatorIds);
            LOG.infof("Composed GameDefinition: %s (valid=%s)", id, result.getValidation().isValid());
            return Response.ok(result).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    // ── Validation ───────────────────────────────────────────────────

    @GET
    @Path("/validate")
    public ValidationResultDto validate() {
        ValidationResultDto result = service.validate();
        LOG.infof("Validation result: %s", result.isValid() ? "valid" : "invalid (" + result.getErrors().size() + " errors)");
        return result;
    }

    // ── Ask AI ────────────────────────────────────────────────────────

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @POST
    @Path("/ask/ai")
    public Response askAi(Map<String, String> body) {
        String prompt = InputSanitizer.sanitize(body.get("prompt"));
        if (prompt == null || prompt.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "prompt is required"))
                    .build();
        }
        if (prompt.length() > maxUserPromptLength) {
            prompt = prompt.substring(0, maxUserPromptLength);
        }
        LOG.infof("Ask AI prompt: %s", prompt);

        PaletteDto palette = service.getPalette();
        List<ArtifactDto> artifacts = service.getArtifacts();
        Map<String, String> artifactMap = new HashMap<>();
        for (var a : artifacts) {
            artifactMap.put(a.getEntityId(), a.getDescription());
        }

        String fullPrompt;
        try {
            fullPrompt = """
                    Answer this prompt:
                    ```
                    %s
                    ```
                    using the context provided below:

                    Game builder definitions.
                    Realms are categories of domain entities, helping organise entities.
                    Components are domain entities of the game. They cover all objects in the system.
                    Operators are defining the game mechanics, rules and interactions.

                    realms:
                    ```
                    %s
                    ```
                    components:
                    ```
                    %s
                    ```
                    operators:
                    ```
                    %s
                    ```
                    """.formatted(prompt,
                    MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(buildLlmRealms(palette, artifactMap)),
                    MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(buildLlmComponents(palette, artifactMap)),
                    MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(buildLlmOperators(palette, artifactMap)));
        } catch (Exception e) {
            LOG.error("Failed to serialize AI context", e);
            return Response.serverError().entity(Map.of("error", "Failed to build AI context")).build();
        }

        String reply = ollamaClient.chat(fullPrompt);
        return Response.ok(Map.of("message", reply)).build();
    }

    private List<LlmRealm> buildLlmRealms(PaletteDto palette, Map<String, String> artifactMap) {
        var list = new ArrayList<LlmRealm>();
        for (var r : palette.getRealms()) {
            String desc = artifactMap.getOrDefault(r.getId(), "");
            list.add(new LlmRealm(r.getName(), desc));
        }
        return list;
    }

    private List<LlmComponent> buildLlmComponents(PaletteDto palette, Map<String, String> artifactMap) {
        var list = new ArrayList<LlmComponent>();
        for (var c : palette.getComponents()) {
            String desc = artifactMap.getOrDefault(c.getId(), "");
            // resolve realm name
            String realmName = c.getRealmId();
            for (var r : palette.getRealms()) {
                if (r.getId().equals(c.getRealmId())) {
                    realmName = r.getName();
                    break;
                }
            }
            list.add(new LlmComponent(c.getName(), realmName, desc));
        }
        return list;
    }

    private List<Object> buildLlmOperators(PaletteDto palette, Map<String, String> artifactMap) {
        var list = new ArrayList<Object>();
        for (var o : palette.getOperators()) {
            var contractProps = new ArrayList<String>();
            var c = o.getContract();
            if (c.isReflexive()) contractProps.add("reflexive");
            if (c.isIrreflexive()) contractProps.add("irreflexive");
            if (c.isAntisymmetric()) contractProps.add("antisymmetric");
            if (c.isAsymmetric()) contractProps.add("asymmetric");
            if (c.isIdempotent()) contractProps.add("idempotent");
            if (c.isInvolutive()) contractProps.add("involutive");
            if (c.isMonotonic()) contractProps.add("monotonic");
            if (c.isAssociative()) contractProps.add("associative");
            if (c.isCancellative()) contractProps.add("cancellative");
            if (c.isDistributive()) contractProps.add("distributive");
            if (c.isTransitive()) contractProps.add("transitive");
            if (c.isIdentityElement()) contractProps.add("identityElement");
            if (c.isInverseElement()) contractProps.add("inverseElement");
            if (c.isAbsorbingElement()) contractProps.add("absorbingElement");

            String desc = artifactMap.getOrDefault(o.getId(), "");

            // resolve operand component IDs to names
            var operands = o.getOperands();
            switch (o.getOperandCount()) {
                case 1 -> {
                    var opNames = resolveComponentNames(operands.get(0), palette);
                    list.add(new LlmUnaryOperator(o.getName(), opNames, contractProps, desc));
                }
                case 2 -> {
                    var lhsNames = resolveComponentNames(operands.get(0), palette);
                    var rhsNames = resolveComponentNames(operands.get(1), palette);
                    list.add(new LlmBinaryOperator(o.getName(), lhsNames, rhsNames, contractProps, desc));
                }
                case 3 -> {
                    var leftNames = resolveComponentNames(operands.get(0), palette);
                    var middleNames = resolveComponentNames(operands.get(1), palette);
                    var rightNames = resolveComponentNames(operands.get(2), palette);
                    list.add(new LlmTernaryOperator(o.getName(), leftNames, middleNames, rightNames, contractProps, desc));
                }
            }
        }
        return list;
    }

    private List<String> resolveComponentNames(List<String> componentIds, PaletteDto palette) {
        var names = new ArrayList<String>();
        for (var id : componentIds) {
            String name = id;
            for (var c : palette.getComponents()) {
                if (c.getId().equals(id)) {
                    name = c.getName();
                    break;
                }
            }
            names.add(name);
        }
        return names;
    }

    // ── Helper ───────────────────────────────────────────────────────

    private static boolean bool(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Boolean b) return b;
        return false;
    }

    private static List<String> sanitizeStringList(List<String> list) {
        List<String> result = new ArrayList<>(list.size());
        for (String s : list) {
            result.add(InputSanitizer.sanitize(s));
        }
        return result;
    }
}
