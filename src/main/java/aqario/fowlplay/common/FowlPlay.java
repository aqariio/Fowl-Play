package aqario.fowlplay.common;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayActivities;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlaySensorType;
import aqario.fowlplay.common.entity.data.FowlPlayTrackedDataHandlerRegistry;
import aqario.fowlplay.common.item.FowlPlayItems;
import aqario.fowlplay.common.registry.FowlPlayRegistries;
import aqario.fowlplay.common.registry.FowlPlayRegistryKeys;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.world.gen.FowlPlayWorldGen;
import aqario.fowlplay.common.world.gen.HawkSpawner;
import aqario.fowlplay.common.world.gen.PigeonSpawner;
import aqario.fowlplay.common.world.gen.SparrowSpawner;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

    public static boolean isYACLLoaded() {
        return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Fowl Play");
        if (isYACLLoaded()) {
            FowlPlayConfig.load();
        }

        FowlPlayActivities.init();
        FowlPlayEntityType.init();
        ChickenVariant.init();
        DuckVariant.init();
        GullVariant.init();
        PigeonVariant.init();
        SparrowVariant.init();
        FowlPlayItems.init();
        FowlPlayMemoryModuleType.init();
        FowlPlayRegistries.init();
        FowlPlayRegistryKeys.init();
        FowlPlaySensorType.init();
        FowlPlaySoundEvents.init();
        FowlPlayTrackedDataHandlerRegistry.init();
        FowlPlayWorldGen.init();

        HawkSpawner hawkSpawner = new HawkSpawner();
        PigeonSpawner pigeonSpawner = new PigeonSpawner();
        SparrowSpawner sparrowSpawner = new SparrowSpawner();

        ServerTickEvents.END_WORLD_TICK.register(world -> {
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