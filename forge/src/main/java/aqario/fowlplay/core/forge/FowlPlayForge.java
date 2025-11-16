package aqario.fowlplay.core.forge;

import aqario.fowlplay.client.forge.FowlPlayForgeClient;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import aqario.fowlplay.core.platform.CommonRegistry;
import aqario.fowlplay.core.platform.forge.PlatformHelperImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod(FowlPlay.ID)
public final class FowlPlayForge {
    @SuppressWarnings("removal")
    public FowlPlayForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            FowlPlayForgeClient.init(modBus);
        }

        modBus.addListener(FowlPlayForge::onNewRegistry);
        modBus.addListener(FowlPlayForge::onRegister);
        modBus.addListener(FowlPlayForge::onAddItemGroupEntries);

        PlatformHelperImpl.CHICKEN_VARIANTS.register(modBus);
        PlatformHelperImpl.DUCK_VARIANTS.register(modBus);
        PlatformHelperImpl.GOOSE_VARIANTS.register(modBus);
        PlatformHelperImpl.GULL_VARIANTS.register(modBus);
        PlatformHelperImpl.PIGEON_VARIANTS.register(modBus);
        PlatformHelperImpl.SPARROW_VARIANTS.register(modBus);
        PlatformHelperImpl.ACTIVITIES.register(modBus);
        PlatformHelperImpl.ENTITY_TYPES.register(modBus);
        PlatformHelperImpl.ITEMS.register(modBus);
        PlatformHelperImpl.MEMORY_MODULE_TYPES.register(modBus);
        PlatformHelperImpl.PARTICLE_TYPES.register(modBus);
        PlatformHelperImpl.SCHEDULES.register(modBus);
        PlatformHelperImpl.SENSOR_TYPES.register(modBus);
        PlatformHelperImpl.SOUND_EVENTS.register(modBus);
        PlatformHelperImpl.TRACKED_DATA_HANDLERS.register(modBus);
        FowlPlayBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modBus);
    }

    @SuppressWarnings("unchecked")
    private static void onNewRegistry(NewRegistryEvent event) {
        FowlPlay.earlyInit();
        FowlPlayRegistries.CHICKEN_VARIANT = (Supplier<CommonRegistry<ChickenVariant>>) (Supplier<?>) event.create(RegistryBuilder.of(FowlPlayRegistryKeys.CHICKEN_VARIANT.location()));
        FowlPlayRegistries.DUCK_VARIANT = (Supplier<CommonRegistry<DuckVariant>>) (Supplier<?>) event.create(RegistryBuilder.of(FowlPlayRegistryKeys.DUCK_VARIANT.location()));
        FowlPlayRegistries.GULL_VARIANT = (Supplier<CommonRegistry<GullVariant>>) (Supplier<?>) event.create(RegistryBuilder.of(FowlPlayRegistryKeys.GULL_VARIANT.location()));
        FowlPlayRegistries.GOOSE_VARIANT = (Supplier<CommonRegistry<GooseVariant>>) (Supplier<?>) event.create(RegistryBuilder.of(FowlPlayRegistryKeys.GOOSE_VARIANT.location()));
        FowlPlayRegistries.PIGEON_VARIANT = (Supplier<CommonRegistry<PigeonVariant>>) (Supplier<?>) event.create(RegistryBuilder.of(FowlPlayRegistryKeys.PIGEON_VARIANT.location()));
        FowlPlayRegistries.SPARROW_VARIANT = (Supplier<CommonRegistry<SparrowVariant>>) (Supplier<?>) event.create(RegistryBuilder.of(FowlPlayRegistryKeys.SPARROW_VARIANT.location()));
    }

    private static void onRegister(RegisterEvent event) {
        FowlPlay.init();
    }

    private static void onAddItemGroupEntries(BuildCreativeModeTabContentsEvent event) {
        PlatformHelperImpl.ITEM_TO_GROUPS.forEach(((item, group) -> {
            if(event.getTabKey() == group) {
                event.accept(item.get());
            }
        }));
    }
}
