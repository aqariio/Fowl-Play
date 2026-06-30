package aqario.fowlplay.datagen;

import aqario.fowlplay.core.tags.FPBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class FPBlockTagGen extends FabricTagProvider.BlockTagProvider {
    private static final ResourceLocation STONES = ResourceLocation.tryBuild("c", "stones");

    public FPBlockTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.getOrCreateTagBuilder(FPBlockTags.PENGUINS_SLIDE_ON)
            .addOptionalTag(BlockTags.ICE)
            .addOptionalTag(BlockTags.SNOW);
        this.getOrCreateTagBuilder(FPBlockTags.PENGUINS_SPAWNABLE_ON)
            .addOptionalTag(BlockTags.DIRT)
            .addOptionalTag(BlockTags.ICE)
            .addOptionalTag(BlockTags.SAND)
            .add(Blocks.POWDER_SNOW)
            .add(Blocks.SNOW_BLOCK);
        this.getOrCreateTagBuilder(FPBlockTags.PERCHES)
            .addOptionalTag(BlockTags.LEAVES)
            .addOptionalTag(BlockTags.LOGS)
            .addOptionalTag(BlockTags.WALLS)
            .addOptionalTag(BlockTags.FENCES)
            .addOptionalTag(BlockTags.FENCE_GATES);
        this.getOrCreateTagBuilder(FPBlockTags.SHOREBIRDS_SPAWNABLE_ON)
            .addOptionalTag(BlockTags.DIRT)
            .addOptionalTag(BlockTags.SAND)
            .addOptionalTag(STONES)
            .add(Blocks.GRAVEL);
        this.getOrCreateTagBuilder(FPBlockTags.WATERFOWL_SPAWNABLE_ON)
            .addOptionalTag(BlockTags.DIRT)
            .addOptionalTag(BlockTags.SAND)
            .addOptionalTag(STONES)
            .add(Blocks.GRAVEL);
    }
}