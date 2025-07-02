package aqario.fowlplay.core.forge;

import aqario.fowlplay.client.forge.FowlPlayForgeClient;
import aqario.fowlplay.common.integration.YACLIntegration;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayItems;
import aqario.fowlplay.core.platform.forge.PlatformHelperImpl;
import net.minecraft.item.ItemGroups;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(FowlPlay.ID)
public final class FowlPlayForge {
    @SuppressWarnings("removal")
    public FowlPlayForge() {
        IEventBus modBus = MinecraftForge.EVENT_BUS;

        FowlPlay.init();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            FowlPlayForgeClient.init(modBus);
        }

        modBus.addListener(FowlPlayForge::onNewRegistry);
        modBus.addListener(FowlPlayForge::onSetup);
        modBus.addListener(FowlPlayForge::onAddItemGroupEntries);

        PlatformHelperImpl.CHICKEN_VARIANTS.register(modBus);
        PlatformHelperImpl.DUCK_VARIANTS.register(modBus);
        PlatformHelperImpl.GULL_VARIANTS.register(modBus);
        PlatformHelperImpl.PIGEON_VARIANTS.register(modBus);
        PlatformHelperImpl.SPARROW_VARIANTS.register(modBus);
        PlatformHelperImpl.ACTIVITIES.register(modBus);
        PlatformHelperImpl.ENTITY_TYPES.register(modBus);
        PlatformHelperImpl.ITEMS.register(modBus);
        PlatformHelperImpl.MEMORY_MODULE_TYPES.register(modBus);
        PlatformHelperImpl.PARTICLE_TYPES.register(modBus);
        PlatformHelperImpl.SENSOR_TYPES.register(modBus);
        PlatformHelperImpl.SOUND_EVENTS.register(modBus);
        PlatformHelperImpl.TRACKED_DATA_HANDLERS.register(modBus);
        ModLoadingContext.get().getContainer().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(
                (client, screen) -> YACLIntegration.createScreen(screen)
            )
        );
    }

    private static void onNewRegistry(NewRegistryEvent event) {
        FowlPlay.earlyInit();
    }

    private static void onSetup(FMLCommonSetupEvent event) {
    }

    private static void onAddItemGroupEntries(BuildCreativeModeTabContentsEvent event) {
//        PlatformHelperImpl.ITEM_TO_GROUPS.forEach(((item, group) -> {
//            if(event.getTabKey() == group) {
//                event.add(item.get());
//            }
//        }));
        if(event.getTabKey() == ItemGroups.SPAWN_EGGS) {
            event.add(FowlPlayItems.BLUE_JAY_SPAWN_EGG.get());
            event.add(FowlPlayItems.CARDINAL_SPAWN_EGG.get());
            event.add(FowlPlayItems.CHICKADEE_SPAWN_EGG.get());
            event.add(FowlPlayItems.CROW_SPAWN_EGG.get());
            event.add(FowlPlayItems.DUCK_SPAWN_EGG.get());
            event.add(FowlPlayItems.GULL_SPAWN_EGG.get());
            event.add(FowlPlayItems.HAWK_SPAWN_EGG.get());
            event.add(FowlPlayItems.PENGUIN_SPAWN_EGG.get());
            event.add(FowlPlayItems.PIGEON_SPAWN_EGG.get());
            event.add(FowlPlayItems.RAVEN_SPAWN_EGG.get());
            event.add(FowlPlayItems.ROBIN_SPAWN_EGG.get());
            event.add(FowlPlayItems.SPARROW_SPAWN_EGG.get());
        }
    }
}
