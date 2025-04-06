package aqario.fowlplay.datagen;

import aqario.fowlplay.core.tags.FowlPlayItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class FowlPlayItemTagGen extends FabricTagProvider.ItemTagProvider {
    private static final Identifier WORM = Identifier.of("angling", "worm");
    private static final Identifier RAW_MEAT_FOODS = Identifier.of("c", "foods/raw_meat");
    private static final Identifier RAW_MEATS_FOODS = Identifier.of("c", "foods/raw_meats");

    public FowlPlayItemTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        this.getOrCreateTagBuilder(FowlPlayItemTags.BLUE_JAY_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.GLOW_BERRIES)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SWEET_BERRIES)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FowlPlayItemTags.CARDINAL_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.GLOW_BERRIES)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SWEET_BERRIES)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FowlPlayItemTags.CHICKADEE_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.GLOW_BERRIES)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SWEET_BERRIES)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FowlPlayItemTags.CROW_FOOD)
            .addOptionalTag(ConventionalItemTags.FOODS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.EGG)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.TURTLE_EGG)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FowlPlayItemTags.DUCK_FOOD)
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
        this.getOrCreateTagBuilder(FowlPlayItemTags.GULL_FOOD)
            .addOptionalTag(ConventionalItemTags.FOODS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.EGG)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.TURTLE_EGG)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FowlPlayItemTags.HAWK_FOOD)
            .add(Items.EGG)
            .add(Items.TURTLE_EGG)
            .addOptionalTag(RAW_MEAT_FOODS)
            .addOptionalTag(RAW_MEATS_FOODS);
        this.getOrCreateTagBuilder(FowlPlayItemTags.PENGUIN_FOOD)
            .add(Items.COD)
            .add(Items.SALMON)
            .add(Items.TROPICAL_FISH);
        this.getOrCreateTagBuilder(FowlPlayItemTags.PIGEON_FOOD)
            .addOptionalTag(ConventionalItemTags.FOODS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FowlPlayItemTags.RAVEN_FOOD)
            .addOptionalTag(ConventionalItemTags.FOODS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.EGG)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.TURTLE_EGG)
            .add(Items.WHEAT_SEEDS);
        this.getOrCreateTagBuilder(FowlPlayItemTags.ROBIN_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.GLOW_BERRIES)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.SWEET_BERRIES)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS)
            .addOptional(WORM);
        this.getOrCreateTagBuilder(FowlPlayItemTags.SPARROW_FOOD)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS)
            .add(Items.WHEAT_SEEDS);
    }
}
