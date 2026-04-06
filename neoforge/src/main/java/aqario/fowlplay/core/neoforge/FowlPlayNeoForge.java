package aqario.fowlplay.core.neoforge;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.neoforge.RegisterImpl;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import java.util.Comparator;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(IEventBus modBus) {
        FowlPlay.init();

        modBus.addListener(FowlPlayNeoForge::onNewRegistry);
        modBus.addListener(FowlPlayNeoForge::onSetup);
        modBus.addListener(FowlPlayNeoForge::onAddItemGroupEntries);

        RegisterImpl.CHICKEN_VARIANTS.register(modBus);
        RegisterImpl.DUCK_VARIANTS.register(modBus);
        RegisterImpl.GOOSE_VARIANTS.register(modBus);
        RegisterImpl.GULL_VARIANTS.register(modBus);
        RegisterImpl.PIGEON_VARIANTS.register(modBus);
        RegisterImpl.SPARROW_VARIANTS.register(modBus);
        RegisterImpl.ACTIVITIES.register(modBus);
        RegisterImpl.ENTITY_TYPES.register(modBus);
        RegisterImpl.ITEMS.register(modBus);
        RegisterImpl.MEMORY_MODULE_TYPES.register(modBus);
        RegisterImpl.PARTICLE_TYPES.register(modBus);
        RegisterImpl.SCHEDULES.register(modBus);
        RegisterImpl.SENSOR_TYPES.register(modBus);
        RegisterImpl.SOUND_EVENTS.register(modBus);
        RegisterImpl.TRACKED_DATA_HANDLERS.register(modBus);
        FowlPlayBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modBus);
        FowlPlayDataAttachments.ATTACHMENT_TYPES.register(modBus);
    }

    private static void onNewRegistry(NewRegistryEvent event) {
        FowlPlay.earlyInit();
        RegisterImpl.REGISTRIES.forEach(event::register);
    }

    private static void onSetup(FMLCommonSetupEvent event) {
    }

    private static void onAddItemGroupEntries(BuildCreativeModeTabContentsEvent event) {
        RegisterImpl.ITEM_TO_GROUPS.entrySet().stream()
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
