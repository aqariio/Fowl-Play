package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FPItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class FPRecipeGen extends FabricRecipeProvider {
    public FPRecipeGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FPItems.SCARECROW.get(), 1)
            .define('#', Items.HAY_BLOCK)
            .define('/', Items.STICK)
            .pattern(" # ")
            .pattern("/#/")
            .pattern(" / ")
            .unlockedBy(getHasName(Items.HAY_BLOCK), has(Items.HAY_BLOCK))
            .save(exporter);
    }
}
