package aqario.fowlplay.core.tags;

import aqario.fowlplay.core.FowlPlay;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class FowlPlayBlockTags {
    public static final TagKey<Block> PENGUINS_SLIDE_ON = create("penguins_slide_on");
    public static final TagKey<Block> PENGUINS_SPAWNABLE_ON = create("penguins_spawnable_on");
    public static final TagKey<Block> PERCHES = create("perches");
    public static final TagKey<Block> SHOREBIRDS_SPAWNABLE_ON = create("shorebirds_spawnable_on");
    public static final TagKey<Block> WATERFOWL_SPAWNABLE_ON = create("waterfowl_spawnable_on");

    private static TagKey<Block> create(String id) {
        return TagKey.of(RegistryKeys.BLOCK, FowlPlay.id(id));
    }
}
