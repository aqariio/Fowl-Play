package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FPConfig;
import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.common.util.ResourcePathBuilder;
import aqario.fowlplay.common.worldgen.BiomeModifier;
import aqario.fowlplay.common.worldgen.PigeonSpawner;
import aqario.fowlplay.common.worldgen.SparrowSpawner;
import aqario.fowlplay.core.platform.Events;
import aqario.fowlplay.core.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

    public static ResourceLocation id(ResourcePathBuilder path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path.build());
    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(ID, id);
    }

    public static boolean isDebugUtilsLoaded() {
        return Platform.isModLoaded("debugutils");
    }

    public static void earlyInit() {
        LOGGER.info("Loading {} {}", Platform.getName(), Platform.getVersion());
        FPConfig.load();

        FPRegistries.init();
        FPBuiltInRegistries.init();
    }

    public static void init() {
        ChickenVariant.REGISTRAR.register();
        DuckVariant.REGISTRAR.register();
        GooseVariant.REGISTRAR.register();
        GullVariant.REGISTRAR.register();
        PigeonVariant.REGISTRAR.register();
        SparrowVariant.REGISTRAR.register();

        FPActivities.REGISTRAR.register();
        FPBlocks.REGISTRAR.register();
        FPEntityTypes.REGISTRAR.register();
        FPItems.REGISTRAR.register();
        FPMemoryTypes.REGISTRAR.register();
        FPParticleTypes.REGISTRAR.register();
        FPSchedules.REGISTRAR.register();
        FPSensorTypes.REGISTRAR.register();
        FPSoundEvents.REGISTRAR.register();
        FPEntityDataSerializers.REGISTRAR.register();

        BiomeModifier.register();
        initSpawners();
    }

    private static void initSpawners() {
        PigeonSpawner pigeonSpawner = new PigeonSpawner();
        SparrowSpawner sparrowSpawner = new SparrowSpawner();

        Events.serverLevelTickPost(level -> {
            pigeonSpawner.tick(
                level,
                level.getServer().isSpawningMonsters(),
                level.getServer().isSpawningAnimals()
            );
            sparrowSpawner.tick(
                level,
                level.getServer().isSpawningMonsters(),
                level.getServer().isSpawningAnimals()
            );
        });
    }
}