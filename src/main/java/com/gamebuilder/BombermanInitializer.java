package com.gamebuilder;

import com.gamebuilder.domain.AppConfig;
import com.gamebuilder.domain.Artifact;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.Operator;
import com.gamebuilder.palette.Palette;
import com.gamebuilder.palette.Painter;
import com.gamebuilder.utils.BijectMap;
import com.gamebuilder.utils.IdProvider;

public class BombermanInitializer {
    private final Palette palette;
    private final AppConfig appConfig;

    public BombermanInitializer(Palette palette, AppConfig appConfig) {
        this.palette = palette;
        this.appConfig = appConfig;
    }

    public void populate() {
        IdProvider idProvider = appConfig.getIdProvider();
        Painter painter = new Painter(idProvider);

        Realm item = painter.realm("item");
        Realm area = painter.realm("area");
        Realm coordinate = painter.realm("coordinate");

        palette.add(item);
        palette.add(area);
        palette.add(coordinate);

        Component player = painter.component("player", item);
        Component wall = painter.component("wall", item);
        Component enemy = painter.component("enemy", item);
        Component bomb = painter.component("bomb", item);
        Component field = painter.component("field", area);
        Component explosion = painter.component("explosion", area);
        Component granular_time = painter.component("granular_time", coordinate);
        Component location = painter.component("location", coordinate);

        palette.add(player);
        palette.add(wall);
        palette.add(enemy);
        palette.add(bomb);
        palette.add(field);
        palette.add(explosion);
        palette.add(granular_time);
        palette.add(location);

        Operator place = painter.operator()
                .binary()
                .name("place")
                .addLhsOperands(field)
                .addRhsOperands(location)
                .build();
        palette.add(place);

        Operator occupy = painter.operator()
                .binary()
                .name("occupy")
                .addLhsOperands(player, wall, enemy)
                .addRhsOperands(field)
                .build();
        palette.add(occupy);

        Operator presence = painter.operator()
                .ternary()
                .name("presence")
                .addLeftOperands(player, wall, enemy, bomb, field, explosion)
                .addMiddleOperands(granular_time)
                .build();
        palette.add(presence);

        Operator collide = painter.operator()
                .binary()
                .name("collide")
                .addLhsOperands(player, wall, enemy, bomb)
                .addRhsOperands(player, wall, enemy, bomb)
                .build();
        palette.add(collide);

        Operator adjacent = painter.operator()
                .binary()
                .name("adjacent")
                .addLhsOperands(field)
                .addRhsOperands(field)
                .build();
        palette.add(adjacent);

        Operator cornerTangent = painter.operator()
                .binary()
                .name("cornerTangent")
                .addLhsOperands(field)
                .addRhsOperands(field)
                .build();
        palette.add(cornerTangent);

        Operator moveTo = painter.operator()
                .binary()
                .name("moveTo")
                .addLhsOperands(player, wall, enemy, bomb)
                .addRhsOperands(field)
                .build();
        palette.add(moveTo);

        Operator kill = painter.operator()
                .binary()
                .name("kill")
                .addLhsOperands(explosion, enemy)
                .addRhsOperands(player, enemy)
                .build();
        palette.add(kill);

        Operator plantBomb = painter.operator()
                .binary()
                .name("plantBomb")
                .addLhsOperands(player)
                .addRhsOperands(field)
                .build();
        palette.add(plantBomb);

        Operator timerExplode = painter.operator()
                .binary()
                .name("timerExplode")
                .addLhsOperands(granular_time)
                .addRhsOperands(bomb)
                .build();
        palette.add(timerExplode);

        Operator chainExplosion = painter.operator()
                .binary()
                .name("chainExplosion")
                .addLhsOperands(explosion)
                .addRhsOperands(bomb)
                .build();
        palette.add(chainExplosion);

        Operator spread = painter.operator()
                .binary()
                .name("spread")
                .addLhsOperands(explosion)
                .addRhsOperands(field)
                .build();
        palette.add(spread);

        Operator block = painter.operator()
                .binary()
                .name("block")
                .addLhsOperands(wall)
                .addRhsOperands(player, enemy, bomb, explosion)
                .build();
        palette.add(block);

        Operator spawn = painter.operator()
                .unary()
                .name("spawn")
                .addOperands(player, enemy)
                .build();
        palette.add(spawn);

        Operator winCondition = painter.operator()
                .unary()
                .name("winCondition")
                .addOperands(granular_time)
                .build();
        palette.add(winCondition);

        Operator defeatCondition = painter.operator()
                .unary()
                .name("defeatCondition")
                .addOperands(granular_time)
                .build();
        palette.add(defeatCondition);
    }

    public BijectMap<String, Artifact> populateArtifacts() {
        BijectMap<String, Artifact> artifacts = new BijectMap<>();

        for (Realm r : palette.getRealms().values()) {
            artifacts.put(r.getId(), new Artifact(r.getId(), realmDescription(r.getName())));
        }
        for (Component c : palette.getComponents().values()) {
            artifacts.put(c.getId(), new Artifact(c.getId(), componentDescription(c.getName())));
        }
        for (Operator o : palette.getOperators().values()) {
            artifacts.put(o.getId(), new Artifact(o.getId(), operatorDescription(o.getName())));
        }

        return artifacts;
    }

    private String realmDescription(String name) {
        return switch (name) {
            case "item" -> "Tangible game objects";
            case "area" -> "Spatial grid cells";
            case "coordinate" -> "Position and time metrics";
            default -> "Realm: " + name;
        };
    }

    private String componentDescription(String name) {
        return switch (name) {
            case "player" -> "Player-controlled character";
            case "wall" -> "Solid blocking obstacle";
            case "enemy" -> "Hostile non-player character";
            case "bomb" -> "Timed explosive device";
            case "field" -> "Empty walkable tile";
            case "explosion" -> "Active blast effect";
            case "granular_time" -> "Discrete simulation tick";
            case "location" -> "XY coordinate pair";
            default -> "Component: " + name;
        };
    }

    private String operatorDescription(String name) {
        return switch (name) {
            case "place" -> "Maps area to coordinate";
            case "occupy" -> "Entity is inside field";
            case "presence" -> "Entity exists at time";
            case "collide" -> "Two entities touch";
            case "adjacent" -> "Orthogonally neighboring fields";
            case "cornerTangent" -> "Diagonally neighboring fields";
            case "moveTo" -> "Entity enters field";
            case "kill" -> "Removes living entity";
            case "plantBomb" -> "Player drops bomb";
            case "timerExplode" -> "Bomb detonates";
            case "chainExplosion" -> "Blast triggers bomb";
            case "spread" -> "Blast fills adjacent fields";
            case "block" -> "Wall stops movement/blast";
            case "spawn" -> "Creates new entity";
            case "winCondition" -> "Victory criteria met";
            case "defeatCondition" -> "Loss criteria met";
            default -> "Operator: " + name;
        };
    }
}
