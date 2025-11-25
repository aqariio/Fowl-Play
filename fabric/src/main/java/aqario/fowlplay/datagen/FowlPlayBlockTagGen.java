package aqario.fowlplay.datagen;

import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class FowlPlayBlockTagGen extends FabricTagProvider.BlockTagProvider {
    public FowlPlayBlockTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.getOrCreateTagBuilder(FowlPlayBlockTags.PENGUINS_SLIDE_ON)
            .addOptionalTag(BlockTags.ICE)
            .addOptionalTag(BlockTags.SNOW);
        this.getOrCreateTagBuilder(FowlPlayBlockTags.PENGUINS_SPAWNABLE_ON)
            .addOptionalTag(BlockTags.DIRT)
            .addOptionalTag(BlockTags.ICE)
            .addOptionalTag(BlockTags.SAND)
            .add(Blocks.POWDER_SNOW)
            .add(Blocks.SNOW_BLOCK);
        this.getOrCreateTagBuilder(FowlPlayBlockTags.PERCHES)
            .addOptionalTag(BlockTags.LEAVES)
            .addOptionalTag(BlockTags.LOGS)
            .addOptionalTag(BlockTags.WALLS)
            .addOptionalTag(BlockTags.FENCES)
            .addOptionalTag(BlockTags.FENCE_GATES);
        this.getOrCreateTagBuilder(FowlPlayBlockTags.SHOREBIRDS_SPAWNABLE_ON)
            .addOptionalTag(BlockTags.DIRT)
            .addOptionalTag(BlockTags.SAND)
            .addOptionalTag(ConventionalBlockTags.STONES)
            .add(Blocks.GRAVEL);
        this.getOrCreateTagBuilder(FowlPlayBlockTags.WATERFOWL_SPAWNABLE_ON)
            .addOptionalTag(BlockTags.DIRT)
            .addOptionalTag(BlockTags.SAND)
            .addOptionalTag(ConventionalBlockTags.STONES)
            .add(Blocks.GRAVEL);
    }
}
