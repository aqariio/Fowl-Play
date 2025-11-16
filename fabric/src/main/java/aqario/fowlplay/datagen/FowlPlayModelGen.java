package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.world.item.Item;

public class FowlPlayModelGen extends FabricModelProvider {
    public FowlPlayModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        this.registerSpawnEgg(generator, FowlPlayItems.BLUE_JAY_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.CARDINAL_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.CHICKADEE_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.CROW_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.DUCK_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.GOOSE_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.GULL_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.HAWK_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.PENGUIN_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.PIGEON_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.RAVEN_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.ROBIN_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FowlPlayItems.SPARROW_SPAWN_EGG.get());
        this.registerItem(generator, FowlPlayItems.SCARECROW.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
    }

    protected void registerSpawnEgg(BlockModelGenerators generator, Item item) {
        generator.delegateItemModel(item, ModelLocationUtils.decorateItemModelLocation("template_spawn_egg"));
    }

    protected void registerItem(BlockModelGenerators generator, Item item) {
        generator.createSimpleFlatItemModel(item);
    }
}
