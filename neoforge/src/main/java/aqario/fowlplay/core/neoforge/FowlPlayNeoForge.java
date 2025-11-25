package aqario.fowlplay.core.neoforge;

import aqario.fowlplay.client.neoforge.FowlPlayNeoForgeClient;
import aqario.fowlplay.common.network.neoforge.ChickenVariantPayload;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.neoforge.PlatformHelperImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import java.util.Comparator;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(ModContainer mod, IEventBus modBus) {
        FowlPlay.init();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            FowlPlayNeoForgeClient.init(modBus);
        }

        modBus.addListener(FowlPlayNeoForge::onNewRegistry);
        modBus.addListener(FowlPlayNeoForge::onSetup);
        modBus.addListener(FowlPlayNeoForge::onAddItemGroupEntries);
        modBus.addListener(FowlPlayNeoForge::onRegisterPayloadHandlers);

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
        FowlPlayDataAttachments.ATTACHMENT_TYPES.register(modBus);
    }

    private static void onRegisterPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
            ChickenVariantPayload.ID,
            ChickenVariantPayload.CODEC,
            (payload, context) -> {
                ClientLevel world = Minecraft.getInstance().level;
                if(world == null) {
                    return;
                }
                Entity entity = world.getEntity(payload.entityId());
                if(entity instanceof Chicken) {
                    entity.setData(FowlPlayDataAttachments.CHICKEN_VARIANT, payload.variant());
                }
            }
        );
    }

    private static void onNewRegistry(NewRegistryEvent event) {
        FowlPlay.earlyInit();
        PlatformHelperImpl.REGISTRIES.forEach(event::register);
    }

    private static void onSetup(FMLCommonSetupEvent event) {
    }

    private static void onAddItemGroupEntries(BuildCreativeModeTabContentsEvent event) {
        PlatformHelperImpl.ITEM_TO_GROUPS.entrySet().stream()
            .sorted(Comparator.comparing(entry ->
                BuiltInRegistries.ITEM.getKey(entry.getKey().get()))
            )
            .forEach(entry -> {
                Item item = entry.getKey().get();
                ResourceKey<CreativeModeTab> group = entry.getValue();
                if(event.getTabKey() == group) {
                    event.accept(item);
                }
            });
    }
}
