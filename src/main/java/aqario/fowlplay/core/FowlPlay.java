package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.world.gen.GullSpawner;
import aqario.fowlplay.common.world.gen.HawkSpawner;
import aqario.fowlplay.common.world.gen.PigeonSpawner;
import aqario.fowlplay.common.world.gen.SparrowSpawner;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
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
        ModContainer mod = FabricLoader.getInstance().getModContainer(ID).orElseThrow(() -> new IllegalStateException("Fowl Play mod container not found??"));
        LOGGER.info("Loading {} {}", mod.getMetadata().getName(), mod.getMetadata().getVersion());
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

        GullSpawner gullSpawner = new GullSpawner();
        HawkSpawner hawkSpawner = new HawkSpawner();
        PigeonSpawner pigeonSpawner = new PigeonSpawner();
        SparrowSpawner sparrowSpawner = new SparrowSpawner();

        ServerTickEvents.END_WORLD_TICK.register(world -> {
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