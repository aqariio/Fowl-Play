package aqario.fowlplay.common.config;

import aqario.fowlplay.core.FowlPlay;
import dev.isxander.yacl3.config.v2.api.SerialEntry;

public class FowlPlayConfig {
    public static FowlPlayConfig getInstance() {
        if (FowlPlay.isYACLLoaded()) {
            return YACLIntegration.HANDLED_CONFIG.instance();
        }
        return new FowlPlayConfig();
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
    public int blueJayCallVolume = 7;

    // Cardinal

    @SerialEntry
    public int cardinalCallVolume = 4;
    @SerialEntry
    public int cardinalSongVolume = 6;

    // Chickadee

    @SerialEntry
    public int chickadeeCallVolume = 5;
    @SerialEntry
    public int chickadeeSongVolume = 5;

    // Crow

    @SerialEntry
    public int crowCallVolume = 7;

    // Duck

    @SerialEntry
    public int duckCallVolume = 3;

    // Gull

    @SerialEntry
    public int gullCallVolume = 5;
    @SerialEntry
    public int gullSongVolume = 6;

    // Hawk

    @SerialEntry
    public int hawkCallVolume = 8;

    // Penguin

    @SerialEntry
    public int penguinCallVolume = 4;

    // Pigeon

    @SerialEntry
    public int pigeonCallVolume = 1;
    @SerialEntry
    public int pigeonSongVolume = 5;

    // Raven

    @SerialEntry
    public int ravenCallVolume = 7;

    // Robin

    @SerialEntry
    public int robinCallVolume = 3;
    @SerialEntry
    public int robinSongVolume = 5;

    // Sparrow

    @SerialEntry
    public int sparrowCallVolume = 3;
    @SerialEntry
    public int sparrowSongVolume = 4;

    // Spawning

    // Blue Jay

    @SerialEntry
    public int blueJaySpawnWeight = 2;
    @SerialEntry
    public int blueJayMinGroupSize = 1;
    @SerialEntry
    public int blueJayMaxGroupSize = 2;

    // Cardinal

    @SerialEntry
    public int cardinalSpawnWeight = 4;
    @SerialEntry
    public int cardinalMinGroupSize = 1;
    @SerialEntry
    public int cardinalMaxGroupSize = 2;

    // Chickadee

    @SerialEntry
    public int chickadeeSpawnWeight = 5;
    @SerialEntry
    public int chickadeeMinGroupSize = 1;
    @SerialEntry
    public int chickadeeMaxGroupSize = 3;

    // Crow

    @SerialEntry
    public int crowSpawnWeight = 5;
    @SerialEntry
    public int crowMinGroupSize = 3;
    @SerialEntry
    public int crowMaxGroupSize = 6;

    // Duck

    @SerialEntry
    public int duckSpawnWeight = 4;
    @SerialEntry
    public int duckMinGroupSize = 10;
    @SerialEntry
    public int duckMaxGroupSize = 15;

    // Gull

    @SerialEntry
    public int gullSpawnWeight = 5;
    @SerialEntry
    public int gullMinGroupSize = 5;
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
    public int pigeonMinGroupSize = 2;
    @SerialEntry
    public int pigeonMaxGroupSize = 5;

    // Raven

    @SerialEntry
    public int ravenSpawnWeight = 1;
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
    public int robinMaxGroupSize = 3;

    // Sparrow

    @SerialEntry
    public int sparrowSpawnWeight = 20;
    @SerialEntry
    public int sparrowMinGroupSize = 3;
    @SerialEntry
    public int sparrowMaxGroupSize = 6;
}
