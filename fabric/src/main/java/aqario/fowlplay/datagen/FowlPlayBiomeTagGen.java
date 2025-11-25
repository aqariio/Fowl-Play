package aqario.fowlplay.datagen;

import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class FowlPlayBiomeTagGen extends FabricTagProvider<Biome> {
    private static final ResourceLocation WHITE_CLIFFS = ResourceLocation.fromNamespaceAndPath("natures_spirit", "white_cliffs");
    private static final ResourceLocation IS_ALPINE = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_alpine");
    private static final ResourceLocation IS_AUTUMN = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_autumn");
    private static final ResourceLocation IS_COAST = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_coast");
    private static final ResourceLocation IS_COLD = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_cold");
    private static final ResourceLocation IS_CYPRESS = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_cypress");
    private static final ResourceLocation IS_FIELD = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_field");
    private static final ResourceLocation IS_FRONTIER = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_frontier");
    private static final ResourceLocation IS_FREEZING = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_freezing");
    private static final ResourceLocation IS_WETLAND = ResourceLocation.fromNamespaceAndPath("natures_spirit", "is_wetland");
    private static final ResourceLocation BEACH = ResourceLocation.fromNamespaceAndPath("c", "beach");
    private static final ResourceLocation FOREST = ResourceLocation.fromNamespaceAndPath("c", "forest");
    private static final ResourceLocation RIVER = ResourceLocation.fromNamespaceAndPath("c", "river");
    private static final ResourceLocation SWAMP = ResourceLocation.fromNamespaceAndPath("c", "swamp");
    private static final ResourceLocation TREE_CONIFEROUS = ResourceLocation.fromNamespaceAndPath("c", "tree_coniferous");
    private static final ResourceLocation TREE_DECIDUOUS = ResourceLocation.fromNamespaceAndPath("c", "tree_deciduous");
    private static final ResourceLocation VEGETATION_SPARSE = ResourceLocation.fromNamespaceAndPath("c", "vegetation_sparse");
    private static final ResourceLocation IS_TREE_CONIFEROUS = ResourceLocation.fromNamespaceAndPath("c", "is_tree_coniferous");
    private static final ResourceLocation IS_TREE_DECIDUOUS = ResourceLocation.fromNamespaceAndPath("c", "is_tree_deciduous");

    public FowlPlayBiomeTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS)
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
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CARDINALS)
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
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CHICKADEES)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CROWS)
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
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_DUCKS)
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
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_GEESE)
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
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_GULLS)
            .addOptionalTag(IS_COAST)
            .addOptionalTag(BEACH)
            .addOptionalTag(ConventionalBiomeTags.IS_BEACH)
            .addOptionalTag(ConventionalBiomeTags.IS_STONY_SHORES);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_HAWKS)
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
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_PENGUINS)
            .add(Biomes.SNOWY_PLAINS)
            .add(Biomes.SNOWY_BEACH);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_PIGEONS)
            .addOptionalTag(ConventionalBiomeTags.IS_STONY_SHORES)
            .addOptional(WHITE_CLIFFS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_RAVENS)
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
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_ROBINS)
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
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_SPARROWS)
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
