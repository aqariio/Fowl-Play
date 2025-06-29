package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayEntityType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class FowlPlayEntityLootTableGen extends SimpleFabricLootTableProvider {
    public FowlPlayEntityLootTableGen(FabricDataOutput output) {
        super(output, LootContextTypes.ENTITY);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
        this.registerBird(exporter, FowlPlayEntityType.BLUE_JAY.get());
        this.registerBird(exporter, FowlPlayEntityType.CARDINAL.get());
        this.registerBird(exporter, FowlPlayEntityType.CHICKADEE.get());
        this.registerBird(exporter, FowlPlayEntityType.CROW.get());
        this.registerBird(exporter, FowlPlayEntityType.DUCK.get());
        this.registerBird(exporter, FowlPlayEntityType.GULL.get());
        this.registerBird(exporter, FowlPlayEntityType.HAWK.get());
        this.registerBird(exporter, FowlPlayEntityType.PENGUIN.get());
        this.registerBird(exporter, FowlPlayEntityType.PIGEON.get());
        this.registerBird(exporter, FowlPlayEntityType.RAVEN.get());
        this.registerBird(exporter, FowlPlayEntityType.ROBIN.get());
        this.registerBird(exporter, FowlPlayEntityType.SPARROW.get());
    }

    private void registerBird(BiConsumer<Identifier, LootTable.Builder> exporter, EntityType<?> type) {
        this.register(
            exporter,
            type,
            LootTable.builder()
                .pool(
                    LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(
                            ItemEntry.builder(Items.FEATHER)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                                .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0F, 1.0F)))
                        )
                )
                .randomSequenceId(type.getLootTableId())
        );
    }

    private void register(BiConsumer<Identifier, LootTable.Builder> exporter, EntityType<?> type, LootTable.Builder builder) {
        exporter.accept(type.getLootTableId(), builder);
    }
}