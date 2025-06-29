package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.world.gen.GullSpawner;
import aqario.fowlplay.common.world.gen.HawkSpawner;
import aqario.fowlplay.common.world.gen.PigeonSpawner;
import aqario.fowlplay.common.world.gen.SparrowSpawner;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

    public static void earlyInit() {
        Mod mod = Platform.getMod(ID);
        LOGGER.info("Loading {} {}", mod.getName(), mod.getVersion());
        FowlPlayConfig.load();

        FowlPlayRegistryKeys.init();
        FowlPlayRegistries.init();
    }

    public static void init() {
        ChickenVariant.init();
        DuckVariant.init();
        GullVariant.init();
        PigeonVariant.init();
        SparrowVariant.init();

        FowlPlayActivities.init();
        FowlPlayEntityType.init();
        FowlPlayItems.init();
        FowlPlayMemoryModuleType.init();
        FowlPlayParticleTypes.init();
        FowlPlaySensorType.init();
        FowlPlaySoundEvents.init();
        FowlPlayTrackedDataHandlerRegistry.init();

        initSpawners();
    }

    private static void initSpawners() {
        GullSpawner gullSpawner = new GullSpawner();
        HawkSpawner hawkSpawner = new HawkSpawner();
        PigeonSpawner pigeonSpawner = new PigeonSpawner();
        SparrowSpawner sparrowSpawner = new SparrowSpawner();

        TickEvent.SERVER_LEVEL_POST.register(world -> {
            gullSpawner.spawn(
                world,
                world.getServer().isMonsterSpawningEnabled(),
                world.getServer().shouldSpawnAnimals()
            );
            hawkSpawner.spawn(
                world,
                world.getServer().isMonsterSpawningEnabled(),
                world.getServer().shouldSpawnAnimals()
            );
            pigeonSpawner.spawn(
                world,
                world.getServer().isMonsterSpawningEnabled(),
                world.getServer().shouldSpawnAnimals()
            );
            sparrowSpawner.spawn(
                world,
                world.getServer().isMonsterSpawningEnabled(),
                world.getServer().shouldSpawnAnimals()
            );
        });
    }
}