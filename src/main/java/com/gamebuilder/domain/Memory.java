package com.gamebuilder.domain;

import com.gamebuilder.gamedef.GameDefinition;
import com.gamebuilder.palette.Palette;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class Memory {
    private static final Memory INSTANCE = new Memory();

    private final ConcurrentHashMap<String, Palette> palettes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, GameDefinition> gameDefinitions = new ConcurrentHashMap<>();

    private Memory() {
    }

    public static Memory getInstance() {
        return INSTANCE;
    }

    // --- Palettes ---

    public Palette putPalette(Palette palette) {
        Objects.requireNonNull(palette, "palette must not be null");
        return palettes.put(palette.getId(), palette);
    }

    public Palette getPalette(String id) {
        Objects.requireNonNull(id, "id must not be null");
        return palettes.get(id);
    }

    public Palette removePalette(String id) {
        Objects.requireNonNull(id, "id must not be null");
        return palettes.remove(id);
    }

    public Set<String> listPaletteIds() {
        return Set.copyOf(palettes.keySet());
    }

    // --- GameDefinitions ---

    public GameDefinition putGameDefinition(GameDefinition gameDefinition) {
        Objects.requireNonNull(gameDefinition, "gameDefinition must not be null");
        return gameDefinitions.put(gameDefinition.getId(), gameDefinition);
    }

    public GameDefinition getGameDefinition(String id) {
        Objects.requireNonNull(id, "id must not be null");
        return gameDefinitions.get(id);
    }

    public GameDefinition removeGameDefinition(String id) {
        Objects.requireNonNull(id, "id must not be null");
        return gameDefinitions.remove(id);
    }

    public Set<String> listGameDefinitionIds() {
        return Set.copyOf(gameDefinitions.keySet());
    }

    // --- Bulk ---

    public void clear() {
        palettes.clear();
        gameDefinitions.clear();
    }
}
