package aqario.fowlplay.common.registry;

import aqario.fowlplay.core.platform.Register;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlockRegister {
    private final CommonRegister<Block> blocks;
    private final ItemRegister items;

    private BlockRegister(String namespace) {
        this.blocks = CommonRegister.create(BuiltInRegistries.BLOCK, namespace);
        this.items = ItemRegister.create(namespace);
    }

    public static BlockRegister create(String namespace) {
        return new BlockRegister(namespace);
    }

    @SafeVarargs
    public final Supplier<Block> register(
        String id,
        Supplier<Block> block,
        ResourceKey<CreativeModeTab>... tabs
    ) {
        Supplier<Block> entry = this.blocks.register(id, block);
        Supplier<Item> item = this.items.register(id, () -> new BlockItem(entry.get(), new Item.Properties()));
        Register.addToCreativeTab(item, tabs);
        return entry;
    }

    public Supplier<Block> register(
        String id,
        Supplier<Block> block
    ) {
        Supplier<Block> entry = this.blocks.register(id, block);
        this.items.register(id, () -> new BlockItem(entry.get(), new Item.Properties()));
        return entry;
    }

    public void register() {
        this.blocks.register();
        this.items.register();
    }
}
