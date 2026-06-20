package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.common.util.ResourcePathBuilder;
import aqario.fowlplay.common.worldgen.BiomeModifier;
import aqario.fowlplay.common.worldgen.PigeonSpawner;
import aqario.fowlplay.common.worldgen.SparrowSpawner;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

    public static ResourceLocation id(ResourcePathBuilder path) {
        return new ResourceLocation(ID, path.build());
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(ID, id);
    }

    public static boolean isDebugUtilsLoaded() {
        return Platform.isModLoaded("debugutils");
    }

    public static void init() {
        Mod mod = Platform.getMod(ID);
        LOGGER.info("Loading {} {}", mod.getName(), mod.getVersion());
        FowlPlayConfig.load();

        FPRegistries.init();
        FPBuiltInRegistries.init();

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

        TickEvent.SERVER_LEVEL_POST.register(world -> {
            pigeonSpawner.tick(
                world,
                world.getServer().isSpawningMonsters(),
                world.getServer().isSpawningAnimals()
            );
            sparrowSpawner.tick(
                world,
                world.getServer().isSpawningMonsters(),
                world.getServer().isSpawningAnimals()
            );
        });
    }
}