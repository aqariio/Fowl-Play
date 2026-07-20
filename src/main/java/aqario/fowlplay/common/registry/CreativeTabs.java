//~ expect_platform

package aqario.fowlplay.common.registry;

import aqario.fowlplay.fabric.common.registry.CreativeTabsImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CreativeTabs {
    @SafeVarargs
    public static <T extends Item> void addToEnd(Supplier<T> item, ResourceKey<CreativeModeTab>... tab) {
        CreativeTabsImpl.addToEnd(item, tab);
    }
}
