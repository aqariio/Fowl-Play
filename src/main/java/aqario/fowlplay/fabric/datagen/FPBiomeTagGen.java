//? if fabric {
package aqario.fowlplay.fabric.datagen;

import aqario.fowlplay.core.tags.FPBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class FPBiomeTagGen extends FabricTagProvider<Biome> {
    private static final Identifier WHITE_CLIFFS = Identifier.fromNamespaceAndPath("natures_spirit", "white_cliffs");
    private static final Identifier IS_ALPINE = Identifier.fromNamespaceAndPath("natures_spirit", "is_alpine");
    private static final Identifier IS_AUTUMN = Identifier.fromNamespaceAndPath("natures_spirit", "is_autumn");
    private static final Identifier IS_COAST = Identifier.fromNamespaceAndPath("natures_spirit", "is_coast");
    private static final Identifier IS_COLD = Identifier.fromNamespaceAndPath("natures_spirit", "is_cold");
    private static final Identifier IS_CYPRESS = Identifier.fromNamespaceAndPath("natures_spirit", "is_cypress");
    private static final Identifier IS_FIELD = Identifier.fromNamespaceAndPath("natures_spirit", "is_field");
    private static final Identifier IS_FRONTIER = Identifier.fromNamespaceAndPath("natures_spirit", "is_frontier");
    private static final Identifier IS_FREEZING = Identifier.fromNamespaceAndPath("natures_spirit", "is_freezing");
    private static final Identifier IS_WETLAND = Identifier.fromNamespaceAndPath("natures_spirit", "is_wetland");
    private static final Identifier BEACH = Identifier.fromNamespaceAndPath("c", "beach");
    private static final Identifier FOREST = Identifier.fromNamespaceAndPath("c", "forest");
    private static final Identifier RIVER = Identifier.fromNamespaceAndPath("c", "river");
    private static final Identifier SWAMP = Identifier.fromNamespaceAndPath("c", "swamp");
    private static final Identifier TREE_CONIFEROUS = Identifier.fromNamespaceAndPath("c", "tree_coniferous");
    private static final Identifier TREE_DECIDUOUS = Identifier.fromNamespaceAndPath("c", "tree_deciduous");
    private static final Identifier VEGETATION_SPARSE = Identifier.fromNamespaceAndPath("c", "vegetation_sparse");
    private static final Identifier IS_TREE_CONIFEROUS = Identifier.fromNamespaceAndPath("c", "is_tree_coniferous");
    private static final Identifier IS_TREE_DECIDUOUS = Identifier.fromNamespaceAndPath("c", "is_tree_deciduous");

    public FPBiomeTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_BLUE_JAYS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_CARDINALS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_CHICKADEES)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_CROWS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_DUCKS)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(RIVER)
            .addOptionalTag(SWAMP)
            .addOptionalTag(ConventionalBiomeTags.IS_RIVER)
            .addOptionalTag(ConventionalBiomeTags.IS_SWAMP)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_GEESE)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(RIVER)
            .addOptionalTag(SWAMP)
            .addOptionalTag(ConventionalBiomeTags.IS_RIVER)
            .addOptionalTag(ConventionalBiomeTags.IS_SWAMP)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_GULLS)
            .addOptionalTag(IS_COAST)
            .addOptionalTag(BEACH)
            .addOptionalTag(ConventionalBiomeTags.IS_BEACH)
            .addOptionalTag(ConventionalBiomeTags.IS_STONY_SHORES);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_HAWKS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FIELD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(VEGETATION_SPARSE)
            .addOptionalTag(ConventionalBiomeTags.IS_VEGETATION_SPARSE)
            .addOptionalTag(ConventionalBiomeTags.IS_VEGETATION_SPARSE_OVERWORLD)
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
            .addOptionalTag(ConventionalBiomeTags.IS_STONY_SHORES)
            .addOptional(WHITE_CLIFFS);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_RAVENS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_ROBINS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FPBiomeTags.SPAWNS_SPARROWS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FIELD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
    }
}
//?}