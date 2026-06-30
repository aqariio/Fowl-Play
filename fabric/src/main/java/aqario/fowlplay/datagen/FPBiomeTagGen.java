package aqario.fowlplay.datagen;

import aqario.fowlplay.core.tags.FPBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class FPBiomeTagGen extends FabricTagProvider<Biome> {
    private static final ResourceLocation WHITE_CLIFFS = ResourceLocation.tryBuild("natures_spirit", "white_cliffs");
    private static final ResourceLocation IS_ALPINE = ResourceLocation.tryBuild("natures_spirit", "is_alpine");
    private static final ResourceLocation IS_AUTUMN = ResourceLocation.tryBuild("natures_spirit", "is_autumn");
    private static final ResourceLocation IS_COAST = ResourceLocation.tryBuild("natures_spirit", "is_coast");
    private static final ResourceLocation IS_COLD = ResourceLocation.tryBuild("natures_spirit", "is_cold");
    private static final ResourceLocation IS_CYPRESS = ResourceLocation.tryBuild("natures_spirit", "is_cypress");
    private static final ResourceLocation IS_FIELD = ResourceLocation.tryBuild("natures_spirit", "is_field");
    private static final ResourceLocation IS_FRONTIER = ResourceLocation.tryBuild("natures_spirit", "is_frontier");
    private static final ResourceLocation IS_FREEZING = ResourceLocation.tryBuild("natures_spirit", "is_freezing");
    private static final ResourceLocation IS_WETLAND = ResourceLocation.tryBuild("natures_spirit", "is_wetland");
    private static final ResourceLocation IS_BEACH = ResourceLocation.tryBuild("c", "is_beach");
    private static final ResourceLocation IS_FOREST = ResourceLocation.tryBuild("c", "is_forest");
    private static final ResourceLocation IS_RIVER = ResourceLocation.tryBuild("c", "is_river");
    private static final ResourceLocation IS_STONY_SHORES = ResourceLocation.tryBuild("c", "is_stony_shores");
    private static final ResourceLocation IS_SWAMP = ResourceLocation.tryBuild("c", "is_swamp");
    private static final ResourceLocation IS_CONIFEROUS_TREE = ResourceLocation.tryBuild("c", "is_tree/coniferous");
    private static final ResourceLocation IS_DECIDUOUS_TREE = ResourceLocation.tryBuild("c", "is_tree/deciduous");
    private static final ResourceLocation IS_VEGETATION_SPARSE = ResourceLocation.tryBuild("c", "is_vegetation_sparse");
    private static final ResourceLocation IS_VEGETATION_SPARSE_OVERWORLD = ResourceLocation.tryBuild("c", "is_vegetation_sparse/overworld");
    private static final ResourceLocation IS_TREE_CONIFEROUS = ResourceLocation.tryBuild("c", "is_tree_coniferous");
    private static final ResourceLocation IS_TREE_DECIDUOUS = ResourceLocation.tryBuild("c", "is_tree_deciduous");
    private static final ResourceLocation IS_COLD_OVERWORLD = ResourceLocation.tryBuild("forge", "is_cold/overworld");
    private static final ResourceLocation IS_SPARSE_OVERWORLD = ResourceLocation.tryBuild("forge", "is_sparse/overworld");
    private static final ResourceLocation IS_DENSE_OVERWORLD = ResourceLocation.tryBuild("forge", "is_dense/overworld");
    private static final ResourceLocation IS_CONIFEROUS = ResourceLocation.tryBuild("forge", "is_coniferous");

    public FPBiomeTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_BLUE_JAYS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_CONIFEROUS)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_CARDINALS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_CONIFEROUS)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_CHICKADEES)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_CROWS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_CONIFEROUS)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_DUCKS)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(IS_RIVER)
            .addOptionalTag(IS_SWAMP)
            .addOptionalTag(ConventionalBiomeTags.RIVER)
            .addOptionalTag(ConventionalBiomeTags.SWAMP)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_CONIFEROUS)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_GEESE)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(IS_RIVER)
            .addOptionalTag(IS_SWAMP)
            .addOptionalTag(ConventionalBiomeTags.RIVER)
            .addOptionalTag(ConventionalBiomeTags.SWAMP)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_CONIFEROUS)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_GULLS)
            .addOptionalTag(IS_COAST)
            .addOptionalTag(IS_BEACH)
            .addOptionalTag(IS_STONY_SHORES)
            .addOptionalTag(ConventionalBiomeTags.BEACH)
            .addOptionalTag(ConventionalBiomeTags.STONY_SHORES);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_HAWKS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FIELD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(IS_VEGETATION_SPARSE)
            .addOptionalTag(IS_VEGETATION_SPARSE_OVERWORLD)
            .addOptionalTag(ConventionalBiomeTags.VEGETATION_SPARSE)
            .addOptionalTag(IS_SPARSE_OVERWORLD)
            .add(Biomes.PLAINS)
            .add(Biomes.SAVANNA)
            .add(Biomes.SAVANNA_PLATEAU)
            .add(Biomes.SPARSE_JUNGLE)
            .add(Biomes.SUNFLOWER_PLAINS)
            .add(Biomes.WINDSWEPT_FOREST)
            .add(Biomes.WINDSWEPT_HILLS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_PENGUINS)
            .add(Biomes.SNOWY_PLAINS)
            .add(Biomes.SNOWY_BEACH);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_PIGEONS)
            .addOptionalTag(ConventionalBiomeTags.STONY_SHORES)
            .addOptional(WHITE_CLIFFS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_RAVENS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_CONIFEROUS)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_ROBINS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_CONIFEROUS)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_SPARROWS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_COLD_OVERWORLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FIELD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_CONIFEROUS)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
    }
}