//? if fabric {
package aqario.fowlplay.fabric.datagen;

import aqario.fowlplay.core.tags.FPItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class FPItemTagGen extends FabricTagProvider.ItemTagProvider {
    private static final Identifier WORM = Identifier.fromNamespaceAndPath("angling", "worm");

    public FPItemTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.getOrCreateTagBuilder(FPItemTags.BIRD_FEED)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FPItemTags.BLUE_JAY_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.GLOW_BERRIES)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SWEET_BERRIES)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FPItemTags.CARDINAL_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.GLOW_BERRIES)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SWEET_BERRIES)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FPItemTags.CHICKADEE_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.GLOW_BERRIES)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SWEET_BERRIES)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FPItemTags.CROW_FOOD)
            .addOptionalTag(ConventionalItemTags.FOODS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.EGG)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.TURTLE_EGG)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FPItemTags.DUCK_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.BREAD)
            .add(Items.COD)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SALMON)
            .add(Items.SEAGRASS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.TROPICAL_FISH)
            .add(Items.WHEAT_SEEDS)
            .addOptional(WORM);
        this.getOrCreateTagBuilder(FPItemTags.GOOSE_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.BREAD)
            .add(Items.COD)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SALMON)
            .add(Items.SEAGRASS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.TROPICAL_FISH)
            .add(Items.WHEAT_SEEDS)
            .addOptional(WORM);
        this.getOrCreateTagBuilder(FPItemTags.GULL_FOOD)
            .addOptionalTag(ConventionalItemTags.FOODS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.EGG)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.TURTLE_EGG)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FPItemTags.HAWK_FOOD)
            .add(Items.EGG)
            .add(Items.TURTLE_EGG)
            .addOptionalTag(ConventionalItemTags.RAW_MEAT_FOODS)
            .addOptionalTag(ConventionalItemTags.RAW_MEATS_FOODS);
        this.getOrCreateTagBuilder(FPItemTags.PENGUIN_FOOD)
            .add(Items.COD)
            .add(Items.SALMON)
            .add(Items.TROPICAL_FISH);
        this.getOrCreateTagBuilder(FPItemTags.PIGEON_FOOD)
            .addOptionalTag(ConventionalItemTags.FOODS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FPItemTags.RAVEN_FOOD)
            .addOptionalTag(ConventionalItemTags.FOODS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.EGG)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.TURTLE_EGG)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FPItemTags.ROBIN_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.GLOW_BERRIES)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SWEET_BERRIES)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS)
            .addOptional(WORM);
        this.getOrCreateTagBuilder(FPItemTags.SPARROW_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
    }
}
//?}