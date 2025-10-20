package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class FowlPlayRecipeGen extends FabricRecipeProvider {
    public FowlPlayRecipeGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, FowlPlayItems.SCARECROW.get(), 1)
            .input('#', Items.HAY_BLOCK)
            .input('/', Items.STICK)
            .pattern(" # ")
            .pattern("/#/")
            .pattern(" / ")
            .criterion(hasItem(Items.HAY_BLOCK), conditionsFromItem(Items.HAY_BLOCK))
            .offerTo(exporter);
    }
}
