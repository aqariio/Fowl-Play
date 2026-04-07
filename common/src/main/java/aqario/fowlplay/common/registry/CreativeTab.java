package aqario.fowlplay.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CreativeTab {
    @SafeVarargs
    @ExpectPlatform
    public static <T extends Item> void add(Supplier<T> item, ResourceKey<CreativeModeTab>... tab) {
        throw new AssertionError();
    }
}
