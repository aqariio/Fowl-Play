package aqario.fowlplay.common.registry.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class CreativeTabImpl {
    @SafeVarargs
    public static <T extends Item> void add(Supplier<T> item, ResourceKey<CreativeModeTab>... tabs) {
        T entry = item.get();
        for(ResourceKey<CreativeModeTab> tab : tabs) {
            ItemGroupEvents.modifyEntriesEvent(tab).register(entries ->
                entries.accept(entry)
            );
        }
    }
}
