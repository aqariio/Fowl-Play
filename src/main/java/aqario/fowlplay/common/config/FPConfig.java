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

    // Common

    @SerialEntry
    public boolean customChickenModel = true;
    @SerialEntry
    public boolean customChickenBehavior = true;

    @SerialEntry
    public boolean customParrotModel = true;
    @SerialEntry
    public boolean customParrotBehavior = true;

    // Spawning

    // Spawn Caps

    @SerialEntry
    public int ambientBirdsSpawnCap = 15;
    @SerialEntry
    public int birdsSpawnCap = 20;

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
    public int duckMaxGroupSize = 6;

    // Goose

    @SerialEntry
    public int gooseSpawnWeight = 4;
    @SerialEntry
    public int gooseMinGroupSize = 1;
    @SerialEntry
    public int gooseMaxGroupSize = 4;

    // Gull

    @SerialEntry
    public int gullSpawnWeight = 5;
    @SerialEntry
    public int gullMinGroupSize = 3;
    @SerialEntry
    public int gullMaxGroupSize = 8;

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
