package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class FowlPlayRecipeGen extends FabricRecipeProvider {
    public FowlPlayRecipeGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FowlPlayItems.SCARECROW.get(), 1)
            .define('#', Items.HAY_BLOCK)
            .define('/', Items.STICK)
            .pattern(" # ")
            .pattern("/#/")
            .pattern(" / ")
            .unlockedBy(getHasName(Items.HAY_BLOCK), has(Items.HAY_BLOCK))
            .save(exporter);
    }
}
