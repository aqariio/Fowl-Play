package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.Item;

public class FowlPlayModelGen extends FabricModelProvider {
    public FowlPlayModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        this.registerSpawnEgg(generator, FowlPlayItems.BLUE_JAY_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.CARDINAL_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.CHICKADEE_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.CROW_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.DUCK_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.GULL_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.HAWK_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.PENGUIN_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.PIGEON_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.RAVEN_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.ROBIN_SPAWN_EGG);
        this.registerSpawnEgg(generator, FowlPlayItems.SPARROW_SPAWN_EGG);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
    }

    protected void registerSpawnEgg(BlockStateModelGenerator generator, Item item) {
        generator.registerParentedItemModel(item, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
    }
}
