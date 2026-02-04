package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FowlPlayBlocks {
//    public static final Supplier<Block> BIRD_FEEDER = register("bird_feeder",
//        () -> new FeederBlock(BlockBehaviour.Properties.of()),
//        CreativeModeTabs.FUNCTIONAL_BLOCKS
//    );

    @SafeVarargs
    private static Supplier<Block> register(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... groups) {
        Supplier<Block> registry = PlatformHelper.registerBlock(id, block);
        registerBlockItem(id, registry, groups);
        return registry;
    }

    @SafeVarargs
    private static void registerBlockItem(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... groups) {
        PlatformHelper.registerBlockItem(id, block, groups);
    }

    public static void init() {
    }
}
