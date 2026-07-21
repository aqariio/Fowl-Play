//? if fabric {
package aqario.fowlplay.fabric.datagen;

import aqario.fowlplay.core.FPItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.world.item.Item;

public class FPModelGen extends FabricModelProvider {
    public FPModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        this.registerSpawnEgg(generator, FPItems.BLUE_JAY_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.CARDINAL_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.CHICKADEE_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.CROW_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.DUCK_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.GOOSE_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.GULL_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.HAWK_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.PENGUIN_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.PIGEON_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.RAVEN_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.ROBIN_SPAWN_EGG.get());
        this.registerSpawnEgg(generator, FPItems.SPARROW_SPAWN_EGG.get());
        this.registerItem(generator, FPItems.SCARECROW.get());
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
//?}