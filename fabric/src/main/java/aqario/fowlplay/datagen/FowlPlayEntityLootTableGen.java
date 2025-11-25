package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FowlPlayEntityLootTableGen extends SimpleFabricLootTableProvider {
    private final CompletableFuture<HolderLookup.Provider> registryLookupFuture;

    public FowlPlayEntityLootTableGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.ENTITY);
        this.registryLookupFuture = registryLookup;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> exporter) {
        this.registerBird(exporter, FowlPlayEntityTypes.BLUE_JAY.get());
        this.registerBird(exporter, FowlPlayEntityTypes.CARDINAL.get());
        this.registerBird(exporter, FowlPlayEntityTypes.CHICKADEE.get());
        this.registerBird(exporter, FowlPlayEntityTypes.CROW.get());
        this.registerBird(exporter, FowlPlayEntityTypes.DUCK.get());
        this.registerBird(exporter, FowlPlayEntityTypes.GOOSE.get());
        this.registerBird(exporter, FowlPlayEntityTypes.GULL.get());
        this.registerBird(exporter, FowlPlayEntityTypes.HAWK.get());
        this.registerBird(exporter, FowlPlayEntityTypes.PENGUIN.get());
        this.registerBird(exporter, FowlPlayEntityTypes.PIGEON.get());
        this.registerBird(exporter, FowlPlayEntityTypes.RAVEN.get());
        this.registerBird(exporter, FowlPlayEntityTypes.ROBIN.get());
        this.registerBird(exporter, FowlPlayEntityTypes.SPARROW.get());
    }

    private void registerBird(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> exporter, EntityType<?> type) {
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
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registryLookupFuture.join(), UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
                .setRandomSequence(type.getDefaultLootTable().location())
        );
    }

    private void register(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> exporter, EntityType<?> type, LootTable.Builder builder) {
        exporter.accept(type.getDefaultLootTable(), builder);
    }
}
