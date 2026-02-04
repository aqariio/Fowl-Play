package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.common.util.PathBuilder;
import aqario.fowlplay.common.world.gen.PigeonSpawner;
import aqario.fowlplay.common.world.gen.SparrowSpawner;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

    public static ResourceLocation id(PathBuilder path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path.build());
    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(ID, id);
    }

    public static boolean isDebugUtilsLoaded() {
        return Platform.isModLoaded("debugutils");
    }

    public static void earlyInit() {
        Mod mod = Platform.getMod(ID);
        LOGGER.info("Loading {} {}", mod.getName(), mod.getVersion());
        FowlPlayConfig.load();

        FowlPlayRegistries.init();
        FowlPlayBuiltInRegistries.init();
    }

    public static void init() {
        ChickenVariant.init();
        DuckVariant.init();
        GooseVariant.init();
        GullVariant.init();
        PigeonVariant.init();
        SparrowVariant.init();

        FowlPlayActivities.init();
        FowlPlayBlocks.init();
        FowlPlayEntityTypes.init();
        FowlPlayItems.init();
        FowlPlayMemoryTypes.init();
        FowlPlayParticleTypes.init();
        FowlPlaySchedules.init();
        FowlPlaySensorTypes.init();
        FowlPlaySoundEvents.init();
        FowlPlayEntityDataSerializers.init();

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