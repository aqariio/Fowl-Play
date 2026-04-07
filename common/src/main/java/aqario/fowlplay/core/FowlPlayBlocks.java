package aqario.fowlplay.core;

import aqario.fowlplay.common.block.FeederBlock;
import aqario.fowlplay.common.registry.BlockRegister;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public final class FowlPlayBlocks {
    public static final BlockRegister REGISTRAR = BlockRegister.create(FowlPlay.ID);
    public static final Supplier<Block> BIRD_FEEDER = register("bird_feeder",
        () -> new FeederBlock(BlockBehaviour.Properties.of()),
        CreativeModeTabs.FUNCTIONAL_BLOCKS
    );

    @SafeVarargs
    private static Supplier<Block> register(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... tabs) {
        return REGISTRAR.register(id, block, tabs);
    }
}
