package aqario.fowlplay.common.config;

import aqario.fowlplay.common.FowlPlay;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class FowlPlayConfig {
    public static boolean isYACLLoaded() {
        return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
    }

    private static ConfigClassHandler<FowlPlayConfig> getConfig() {
        return ConfigClassHandler.createBuilder(FowlPlayConfig.class)
            .id(Identifier.of(FowlPlay.ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                .setPath(FabricLoader.getInstance().getConfigDir().resolve(FowlPlay.ID + ".json5"))
                .setJson5(true)
                .build())
            .build();
    }

    public static FowlPlayConfig getInstance() {
        if (isYACLLoaded()) {
            return getConfig().instance();
        }
        return new FowlPlayConfig();
    }

    public static FowlPlayConfig getDefaults() {
        return getConfig().defaults();
    }

    public static void load() {
        getConfig().load();
    }

    public static void save() {
        getConfig().save();
    }

    // Visual

    @SerialEntry
    public boolean customChickenModel = true;

    // Audio

    // Blue Jay

    @SerialEntry
    public int blueJayCallVolume = 9;

    // Cardinal

    @SerialEntry
    public int cardinalCallVolume = 2;
    @SerialEntry
    public int cardinalSongVolume = 6;

    // Chickadee

    @SerialEntry
    public int chickadeeCallVolume = 6;
    @SerialEntry
    public int chickadeeSongVolume = 6;

    // Duck

    @SerialEntry
    public int duckCallVolume = 2;

    // Gull

    @SerialEntry
    public int gullCallVolume = 6;
    @SerialEntry
    public int gullSongVolume = 7;

    // Hawk

    @SerialEntry
    public int hawkCallVolume = 12;

    // Penguin

    @SerialEntry
    public int penguinCallVolume = 4;

    // Pigeon

    @SerialEntry
    public int pigeonCallVolume = 1;
    @SerialEntry
    public int pigeonSongVolume = 6;

    // Raven

    @SerialEntry
    public int ravenCallVolume = 10;

    // Robin

    @SerialEntry
    public int robinCallVolume = 2;
    @SerialEntry
    public int robinSongVolume = 6;

    // Sparrow

    @SerialEntry
    public int sparrowCallVolume = 2;
    @SerialEntry
    public int sparrowSongVolume = 6;

    // Spawning

    // Blue Jay

    @SerialEntry
    public int blueJaySpawnWeight = 25;
    @SerialEntry
    public int blueJayMinGroupSize = 1;
    @SerialEntry
    public int blueJayMaxGroupSize = 2;

    // Cardinal

    @SerialEntry
    public int cardinalSpawnWeight = 35;
    @SerialEntry
    public int cardinalMinGroupSize = 1;
    @SerialEntry
    public int cardinalMaxGroupSize = 2;

    // Chickadee

    @SerialEntry
    public int chickadeeSpawnWeight = 50;
    @SerialEntry
    public int chickadeeMinGroupSize = 3;
    @SerialEntry
    public int chickadeeMaxGroupSize = 5;

    // Duck

    @SerialEntry
    public int duckSpawnWeight = 8;
    @SerialEntry
    public int duckMinGroupSize = 6;
    @SerialEntry
    public int duckMaxGroupSize = 10;

    // Gull

    @SerialEntry
    public int gullSpawnWeight = 30;
    @SerialEntry
    public int gullMinGroupSize = 8;
    @SerialEntry
    public int gullMaxGroupSize = 12;

    // Hawk

    @SerialEntry
    public int hawkSpawnWeight = 15;
    @SerialEntry
    public int hawkMinGroupSize = 1;
    @SerialEntry
    public int hawkMaxGroupSize = 2;

    // Penguin

    @SerialEntry
    public int penguinSpawnWeight = 1;
    @SerialEntry
    public int penguinMinGroupSize = 16;
    @SerialEntry
    public int penguinMaxGroupSize = 24;

    // Pigeon

    @SerialEntry
    public int pigeonSpawnWeight = 20;
    @SerialEntry
    public int pigeonMinGroupSize = 4;
    @SerialEntry
    public int pigeonMaxGroupSize = 8;

    // Raven

    @SerialEntry
    public int ravenSpawnWeight = 20;
    @SerialEntry
    public int ravenMinGroupSize = 1;
    @SerialEntry
    public int ravenMaxGroupSize = 3;

    // Robin

    @SerialEntry
    public int robinSpawnWeight = 50;
    @SerialEntry
    public int robinMinGroupSize = 3;
    @SerialEntry
    public int robinMaxGroupSize = 5;

    // Sparrow

    @SerialEntry
    public int sparrowSpawnWeight = 75;
    @SerialEntry
    public int sparrowMinGroupSize = 6;
    @SerialEntry
    public int sparrowMaxGroupSize = 10;
}
