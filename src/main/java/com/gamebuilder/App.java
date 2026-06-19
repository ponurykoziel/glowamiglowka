package com.gamebuilder;

import com.gamebuilder.domain.AppConfig;
import com.gamebuilder.domain.Artifact;
import com.gamebuilder.domain.Memory;
import com.gamebuilder.gamedef.GameDefinition;
import com.gamebuilder.gamedef.GameDefinitionValidator;
import com.gamebuilder.palette.Palette;
import com.gamebuilder.utils.BijectMap;

public class App {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig(4);

        Palette palette = new Palette("bomberman-palette");
        BombermanInitializer initializer = new BombermanInitializer(palette, appConfig);
        initializer.populate();

        BijectMap<String, Artifact> artifacts = initializer.populateArtifacts();

        GameDefinition gameDefinition = new GameDefinition(
                "bomberman-def",
                palette.getComponents(),
                palette.getRealms(),
                palette.getOperators(),
                artifacts
        );

        Memory memory = Memory.getInstance();
        memory.putPalette(palette);
        memory.putGameDefinition(gameDefinition);

        GameDefinitionValidator validator = new GameDefinitionValidator(gameDefinition);
        boolean isValid = validator.validateCoherence();

        System.out.println("GameDefinition valid: " + isValid);
    }
}
