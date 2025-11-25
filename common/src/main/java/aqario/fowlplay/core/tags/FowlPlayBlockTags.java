package aqario.fowlplay.core.tags;

import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class FowlPlayBlockTags {
    public static final TagKey<Block> PENGUINS_SLIDE_ON = create("penguins_slide_on");
    public static final TagKey<Block> PENGUINS_SPAWNABLE_ON = create("penguins_spawnable_on");
    public static final TagKey<Block> PERCHES = create("perches");
    public static final TagKey<Block> SHOREBIRDS_SPAWNABLE_ON = create("shorebirds_spawnable_on");
    public static final TagKey<Block> WATERFOWL_SPAWNABLE_ON = create("waterfowl_spawnable_on");

    private static TagKey<Block> create(String id) {
        return TagKey.create(Registries.BLOCK, FowlPlay.id(id));
    }
}
