package com.gamebuilder.web.state;

import com.gamebuilder.BombermanInitializer;
import com.gamebuilder.domain.AppConfig;
import com.gamebuilder.domain.Artifact;
import com.gamebuilder.gamedef.GameDefinition;
import com.gamebuilder.palette.Palette;
import com.gamebuilder.palette.Painter;
import com.gamebuilder.utils.BijectMap;
import com.gamebuilder.utils.IdProvider;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class GameBuilderState {

    private final AppConfig appConfig;
    private final IdProvider idProvider;
    private final Painter painter;

    private volatile Palette palette;
    private volatile GameDefinition gameDefinition;
    private final BijectMap<String, Artifact> artifactBuffer = new BijectMap<>();

    public GameBuilderState() {
        this.appConfig = new AppConfig(4);
        this.idProvider = appConfig.getIdProvider();
        this.painter = new Painter(idProvider);
        this.palette = newPalette();
        populateBomberman();
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public IdProvider getIdProvider() {
        return idProvider;
    }

    public Painter getPainter() {
        return painter;
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public GameDefinition getGameDefinition() {
        return gameDefinition;
    }

    public void setGameDefinition(GameDefinition gameDefinition) {
        this.gameDefinition = gameDefinition;
    }

    public BijectMap<String, Artifact> getArtifactBuffer() {
        return artifactBuffer;
    }

    public void clearPalette() {
        this.palette = newPalette();
        this.artifactBuffer.clear();
        this.gameDefinition = null;
    }

    public void resetPalette() {
        this.palette = newPalette();
        this.artifactBuffer.clear();
        this.gameDefinition = null;
        populateBomberman();
    }

    public void clearGameDefinition() {
        this.gameDefinition = null;
    }

    private Palette newPalette() {
        return new Palette("palette-" + UUID.randomUUID().toString().substring(0, 8));
    }

    private void populateBomberman() {
        BombermanInitializer initializer = new BombermanInitializer(palette, appConfig);
        initializer.populate();
        BijectMap<String, Artifact> artifacts = initializer.populateArtifacts();
        for (var entry : artifacts.keySet()) {
            artifactBuffer.put(entry, artifacts.getByKey(entry));
        }
    }
}
