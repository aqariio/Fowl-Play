package aqario.fowlplay.common;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayActivities;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlaySensorType;
import aqario.fowlplay.common.entity.data.FowlPlayTrackedDataHandlerRegistry;
import aqario.fowlplay.common.item.FowlPlayItems;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.world.gen.FowlPlayWorldGen;
import aqario.fowlplay.common.world.gen.PigeonSpawner;
import aqario.fowlplay.common.world.gen.SparrowSpawner;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Fowl Play");
        FowlPlayActivities.init();
        FowlPlayEntityType.init();
        FowlPlayItems.init();
        FowlPlayMemoryModuleType.init();
        FowlPlaySensorType.init();
        FowlPlaySoundEvents.init();
        FowlPlayTrackedDataHandlerRegistry.init();
        FowlPlayWorldGen.init();

        PigeonSpawner pigeonSpawner = new PigeonSpawner();
        SparrowSpawner sparrowSpawner = new SparrowSpawner();
        ServerTickEvents.END_WORLD_TICK.register(world -> {
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