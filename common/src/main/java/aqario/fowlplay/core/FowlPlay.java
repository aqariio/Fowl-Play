package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.common.util.PathBuilder;
import aqario.fowlplay.common.world.gen.PigeonSpawner;
import aqario.fowlplay.common.world.gen.SparrowSpawner;
import dev.architectury.event.events.common.TickEvent;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.blay09.mods.balm.world.entity.BalmEntityTypeRegistrar;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";
    public static BalmRegistrars REGISTRARS;
    public static BalmEntityTypeRegistrar ENTITY_REGISTRAR;

    public static ResourceLocation id(PathBuilder path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path.build());
    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(ID, id);
    }

    public static boolean isDebugUtilsLoaded() {
        return Balm.isModLoaded("debugutils");
    }

    public static void init(BalmRegistrars registrars) {
        LOGGER.info("Loading {}", Balm.getModName(ID));
        REGISTRARS = registrars;
        registrars.entityTypes(registrar -> ENTITY_REGISTRAR = registrar);

        FowlPlayConfig.load();

        FowlPlayRegistries.init();
        FowlPlayBuiltInRegistries.init();

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
        registrars.registrar(Registries.MEMORY_MODULE_TYPE, FowlPlayMemoryTypes::init);
        registrars.particleTypes(FowlPlayParticleTypes::init);
        FowlPlaySchedules.init();
        FowlPlaySensorTypes.init();
        FowlPlaySoundEvents.init();
        FowlPlayEntityDataSerializers.init();

        FowlPlaySpawnPlacements.init();

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