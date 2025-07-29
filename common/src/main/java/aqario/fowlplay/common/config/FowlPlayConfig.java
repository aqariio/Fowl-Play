package aqario.fowlplay.common.config;

import aqario.fowlplay.common.integration.YACLIntegration;
import dev.isxander.yacl3.config.v2.api.SerialEntry;

public class FowlPlayConfig {
    public static FowlPlayConfig getInstance() {
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

    @SerialEntry
    public int blueJayCallVolume = 5;

    // Cardinal

    @SerialEntry
    public int cardinalCallVolume = 1;
    @SerialEntry
    public int cardinalSongVolume = 4;

    // Chickadee

    @SerialEntry
    public int chickadeeCallVolume = 2;
    @SerialEntry
    public int chickadeeSongVolume = 2;

    // Crow

    @SerialEntry
    public int crowCallVolume = 6;

    // Duck

    @SerialEntry
    public int duckCallVolume = 1;

    // Gull

    @SerialEntry
    public int gullCallVolume = 2;
    @SerialEntry
    public int gullSongVolume = 3;

    // Hawk

    @SerialEntry
    public int hawkCallVolume = 6;

    // Penguin

    @SerialEntry
    public int penguinCallVolume = 2;

    // Pigeon

    @SerialEntry
    public int pigeonCallVolume = 1;
    @SerialEntry
    public int pigeonSongVolume = 3;

    // Raven

    @SerialEntry
    public int ravenCallVolume = 6;

    // Robin

    @SerialEntry
    public int robinCallVolume = 2;
    @SerialEntry
    public int robinSongVolume = 2;

    // Sparrow

    @SerialEntry
    public int sparrowCallVolume = 2;
    @SerialEntry
    public int sparrowSongVolume = 2;

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
    public int crowSpawnWeight = 5;
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
