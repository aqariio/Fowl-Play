package aqario.fowlplay.common.registry.forge;

import aqario.fowlplay.core.forge.FowlPlayForge;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class CreativeTabsImpl {
    @SafeVarargs
    public static <T extends Item> void addToEnd(Supplier<T> item, ResourceKey<CreativeModeTab>... tabs) {
        for(ResourceKey<CreativeModeTab> tab : tabs) {
            FowlPlayForge.eventBus().<BuildCreativeModeTabContentsEvent>addListener(event -> {
                if(tab == event.getTabKey()) {
                    event.accept(item.get());
                }
            });
        }
    }
}
