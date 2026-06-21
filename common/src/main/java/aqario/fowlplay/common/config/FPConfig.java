package aqario.fowlplay.common.config;

import aqario.fowlplay.common.integration.YACLIntegration;
import dev.isxander.yacl3.config.v2.api.SerialEntry;

public class FPConfig {
    public static FPConfig getInstance() {
        return YACLIntegration.HANDLED_CONFIG.instance();
    }

    public static void load() {
        YACLIntegration.HANDLED_CONFIG.load();
    }

    public static void save() {
        YACLIntegration.HANDLED_CONFIG.save();
    }

    // Visual

    @SerialEntry
    public boolean customChickenModel = true;

    // Audio

    // Blue Jay
    public static final int blueJayCallRange = 80;

    // Cardinal
    public static final int cardinalCallRange = 16;
    public static final int cardinalSongRange = 64;

    // Chickadee
    public static final int chickadeeCallRange = 32;
    public static final int chickadeeSongRange = 32;

    // Crow
    public static final int crowCallRange = 96;

    // Duck
    public static final int duckCallRange = 16;

    // Goose
    public static final int gooseCallRange = 32;

    // Gull
    public static final int gullCallRange = 32;
    public static final int gullSongRange = 48;

    // Hawk
    public static final int hawkCallRange = 96;

    // Penguin
    public static final int penguinCallRange = 32;

    // Pigeon
    public static final int pigeonCallRange = 16;
    public static final int pigeonSongRange = 48;

    // Raven
    public static final int ravenCallRange = 96;

    // Robin
    public static final int robinCallRange = 32;
    public static final int robinSongRange = 32;

    // Sparrow
    public static final int sparrowCallRange = 32;
    public static final int sparrowSongRange = 32;

    // Vulture
    public static final int vultureCallRange = 32;

    // Spawning

    // Blue Jay

    @SerialEntry
    public int blueJaySpawnWeight = 2;
    @SerialEntry
    public int blueJayMinGroupSize = 1;
    @SerialEntry
    public int blueJayMaxGroupSize = 1;

    // Cardinal

    @SerialEntry
    public int cardinalSpawnWeight = 5;
    @SerialEntry
    public int cardinalMinGroupSize = 1;
    @SerialEntry
    public int cardinalMaxGroupSize = 1;

    // Chickadee

    @SerialEntry
    public int chickadeeSpawnWeight = 4;
    @SerialEntry
    public int chickadeeMinGroupSize = 1;
    @SerialEntry
    public int chickadeeMaxGroupSize = 1;

    // Crow

    @SerialEntry
    public int crowSpawnWeight = 3;
    @SerialEntry
    public int crowMinGroupSize = 1;
    @SerialEntry
    public int crowMaxGroupSize = 6;

    // Duck

    @SerialEntry
    public int duckSpawnWeight = 4;
    @SerialEntry
    public int duckMinGroupSize = 2;
    @SerialEntry
    public int duckMaxGroupSize = 10;

    // Goose

    @SerialEntry
    public int gooseSpawnWeight = 4;
    @SerialEntry
    public int gooseMinGroupSize = 2;
    @SerialEntry
    public int gooseMaxGroupSize = 10;

    // Gull

    @SerialEntry
    public int gullSpawnWeight = 5;
    @SerialEntry
    public int gullMinGroupSize = 3;
    @SerialEntry
    public int gullMaxGroupSize = 12;

    // Hawk

    @SerialEntry
    public int hawkSpawnWeight = 1;
    @SerialEntry
    public int hawkMinGroupSize = 1;
    @SerialEntry
    public int hawkMaxGroupSize = 1;

    // Penguin

    @SerialEntry
    public int penguinSpawnWeight = 1;
    @SerialEntry
    public int penguinMinGroupSize = 16;
    @SerialEntry
    public int penguinMaxGroupSize = 24;

    // Pigeon

    @SerialEntry
    public int pigeonSpawnWeight = 3;
    @SerialEntry
    public int pigeonMinGroupSize = 1;
    @SerialEntry
    public int pigeonMaxGroupSize = 5;

    // Raven

    @SerialEntry
    public int ravenSpawnWeight = 2;
    @SerialEntry
    public int ravenMinGroupSize = 1;
    @SerialEntry
    public int ravenMaxGroupSize = 2;

    // Robin

    @SerialEntry
    public int robinSpawnWeight = 8;
    @SerialEntry
    public int robinMinGroupSize = 1;
    @SerialEntry
    public int robinMaxGroupSize = 2;

    // Sparrow

    @SerialEntry
    public int sparrowSpawnWeight = 20;
    @SerialEntry
    public int sparrowMinGroupSize = 2;
    @SerialEntry
    public int sparrowMaxGroupSize = 6;
}
