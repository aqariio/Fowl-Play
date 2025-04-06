package aqario.fowlplay.datagen;

import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class FowlPlayBlockTagGen extends FabricTagProvider.BlockTagProvider {
    private static final Identifier STONES = Identifier.of("c", "stones");

    public FowlPlayBlockTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
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
            .addOptionalTag(STONES)
            .add(Blocks.GRAVEL);
        this.getOrCreateTagBuilder(FowlPlayBlockTags.WATERFOWL_SPAWNABLE_ON)
            .addOptionalTag(BlockTags.DIRT)
            .addOptionalTag(BlockTags.SAND)
            .addOptionalTag(STONES)
            .add(Blocks.GRAVEL);
    }
}
