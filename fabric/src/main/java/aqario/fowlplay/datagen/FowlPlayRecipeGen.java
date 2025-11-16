package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class FowlPlayRecipeGen extends FabricRecipeProvider {
    public FowlPlayRecipeGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
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
