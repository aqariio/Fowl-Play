package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FPEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class FowlPlayEntityLootTableGen extends SimpleFabricLootTableProvider {
    public FowlPlayEntityLootTableGen(FabricDataOutput output) {
        super(output, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> exporter) {
        this.registerBird(exporter, FPEntityTypes.BLUE_JAY.get());
        this.registerBird(exporter, FPEntityTypes.CARDINAL.get());
        this.registerBird(exporter, FPEntityTypes.CHICKADEE.get());
        this.registerBird(exporter, FPEntityTypes.CROW.get());
        this.registerBird(exporter, FPEntityTypes.DUCK.get());
        this.registerBird(exporter, FPEntityTypes.GOOSE.get());
        this.registerBird(exporter, FPEntityTypes.GULL.get());
        this.registerBird(exporter, FPEntityTypes.HAWK.get());
        this.registerBird(exporter, FPEntityTypes.PENGUIN.get());
        this.registerBird(exporter, FPEntityTypes.PIGEON.get());
        this.registerBird(exporter, FPEntityTypes.RAVEN.get());
        this.registerBird(exporter, FPEntityTypes.ROBIN.get());
        this.registerBird(exporter, FPEntityTypes.SPARROW.get());
    }

    private void registerBird(BiConsumer<ResourceLocation, LootTable.Builder> exporter, EntityType<?> type) {
        this.register(
            exporter,
            type,
            LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(
                            LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
                .setRandomSequence(type.getDefaultLootTable())
        );
    }

    private void register(BiConsumer<ResourceLocation, LootTable.Builder> exporter, EntityType<?> type, LootTable.Builder builder) {
        exporter.accept(type.getDefaultLootTable(), builder);
    }
}