package aqario.fowlplay.datagen;

import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class FowlPlayBiomeTagGen extends FabricTagProvider<Biome> {
    private static final Identifier WHITE_CLIFFS = Identifier.of("natures_spirit", "white_cliffs");
    private static final Identifier IS_ALPINE = Identifier.of("natures_spirit", "is_alpine");
    private static final Identifier IS_AUTUMN = Identifier.of("natures_spirit", "is_autumn");
    private static final Identifier IS_COAST = Identifier.of("natures_spirit", "is_coast");
    private static final Identifier IS_COLD = Identifier.of("natures_spirit", "is_cold");
    private static final Identifier IS_CYPRESS = Identifier.of("natures_spirit", "is_cypress");
    private static final Identifier IS_FIELD = Identifier.of("natures_spirit", "is_field");
    private static final Identifier IS_FRONTIER = Identifier.of("natures_spirit", "is_frontier");
    private static final Identifier IS_FREEZING = Identifier.of("natures_spirit", "is_freezing");
    private static final Identifier IS_WETLAND = Identifier.of("natures_spirit", "is_wetland");
    private static final Identifier IS_BEACH = Identifier.of("c", "is_beach");
    private static final Identifier IS_FOREST = Identifier.of("c", "is_forest");
    private static final Identifier IS_RIVER = Identifier.of("c", "is_river");
    private static final Identifier IS_STONY_SHORES = Identifier.of("c", "is_stony_shores");
    private static final Identifier IS_SWAMP = Identifier.of("c", "is_swamp");
    private static final Identifier IS_CONIFEROUS_TREE = Identifier.of("c", "is_tree/coniferous");
    private static final Identifier IS_DECIDUOUS_TREE = Identifier.of("c", "is_tree/deciduous");
    private static final Identifier IS_VEGETATION_SPARSE = Identifier.of("c", "is_vegetation_sparse");
    private static final Identifier IS_VEGETATION_SPARSE_OVERWORLD = Identifier.of("c", "is_vegetation_sparse/overworld");
    private static final Identifier IS_TREE_CONIFEROUS = Identifier.of("c", "is_tree_coniferous");
    private static final Identifier IS_TREE_DECIDUOUS = Identifier.of("c", "is_tree_deciduous");

    public FowlPlayBiomeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CARDINALS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CHICKADEES)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CROWS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_DUCKS)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(IS_RIVER)
            .addOptionalTag(IS_SWAMP)
            .addOptionalTag(ConventionalBiomeTags.RIVER)
            .addOptionalTag(ConventionalBiomeTags.SWAMP)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_GULLS)
            .addOptionalTag(IS_COAST)
            .addOptionalTag(IS_BEACH)
            .addOptionalTag(IS_STONY_SHORES)
            .addOptionalTag(ConventionalBiomeTags.BEACH)
            .addOptionalTag(ConventionalBiomeTags.STONY_SHORES);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_HAWKS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FIELD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(IS_VEGETATION_SPARSE)
            .addOptionalTag(IS_VEGETATION_SPARSE_OVERWORLD)
            .addOptionalTag(ConventionalBiomeTags.VEGETATION_SPARSE)
            .add(BiomeKeys.PLAINS)
            .add(BiomeKeys.SAVANNA)
            .add(BiomeKeys.SAVANNA_PLATEAU)
            .add(BiomeKeys.SPARSE_JUNGLE)
            .add(BiomeKeys.SUNFLOWER_PLAINS)
            .add(BiomeKeys.WINDSWEPT_FOREST)
            .add(BiomeKeys.WINDSWEPT_HILLS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_PENGUINS)
            .add(BiomeKeys.SNOWY_PLAINS)
            .add(BiomeKeys.SNOWY_BEACH);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_PIGEONS)
            .addOptionalTag(ConventionalBiomeTags.STONY_SHORES)
            .addOptional(WHITE_CLIFFS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_RAVENS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_ROBINS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_SPARROWS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FIELD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(IS_FOREST)
            .addOptionalTag(IS_CONIFEROUS_TREE)
            .addOptionalTag(IS_DECIDUOUS_TREE)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS)
            .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS);
    }
}
