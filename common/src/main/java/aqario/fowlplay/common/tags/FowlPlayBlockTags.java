package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class FowlPlayBlockTags {
    public static final TagKey<Block> PASSERINES_SPAWNABLE_ON = create("passerines_spawnable_on");
    public static final TagKey<Block> PENGUINS_SLIDE_ON = create("penguins_slide_on");
    public static final TagKey<Block> PENGUINS_SPAWNABLE_ON = create("penguins_spawnable_on");
    public static final TagKey<Block> SHOREBIRDS_SPAWNABLE_ON = create("shorebirds_spawnable_on");

    private static TagKey<Block> create(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(FowlPlay.ID, id));
    }
}
