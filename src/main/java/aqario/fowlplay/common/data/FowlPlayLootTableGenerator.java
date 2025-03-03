//package aqario.fowlplay.common.data;
//
//import aqario.fowlplay.common.FowlPlay;
//import aqario.fowlplay.common.entity.FowlPlayEntityType;
//import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
//import net.minecraft.entity.EntityType;
//import net.minecraft.item.Items;
//import net.minecraft.loot.LootPool;
//import net.minecraft.loot.LootTable;
//import net.minecraft.loot.context.LootContextTypes;
//import net.minecraft.loot.entry.ItemEntry;
//import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
//import net.minecraft.loot.function.SetCountLootFunction;
//import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
//import net.minecraft.loot.provider.number.UniformLootNumberProvider;
//import net.minecraft.registry.Registries;
//import net.minecraft.registry.RegistryKey;
//import net.minecraft.registry.RegistryWrapper;
//import net.minecraft.util.Identifier;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.function.BiConsumer;
//
//public class FowlPlayLootTableGenerator extends SimpleFabricLootTableProvider {
//    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture;
//
//    public FowlPlayLootTableGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
//        super(output, registryLookup, LootContextTypes.ENTITY);
//        this.registryLookupFuture = registryLookup;
//    }
//
//    @Override
//    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> biConsumer) {
//        this.registerBird(biConsumer, FowlPlayEntityType.BLUE_JAY);
//        this.registerBird(biConsumer, FowlPlayEntityType.CARDINAL);
//        this.registerBird(biConsumer, FowlPlayEntityType.CHICKADEE);
//        this.registerBird(biConsumer, FowlPlayEntityType.DUCK);
//        this.registerBird(biConsumer, FowlPlayEntityType.GULL);
//        this.registerBird(biConsumer, FowlPlayEntityType.HAWK);
//        this.registerBird(biConsumer, FowlPlayEntityType.PENGUIN);
//        this.registerBird(biConsumer, FowlPlayEntityType.PIGEON);
//        this.registerBird(biConsumer, FowlPlayEntityType.RAVEN);
//        this.registerBird(biConsumer, FowlPlayEntityType.ROBIN);
//        this.registerBird(biConsumer, FowlPlayEntityType.SPARROW);
//    }
//
//    private void registerBird(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> biConsumer, EntityType<?> type) {
//        this.register(
//            biConsumer,
//            type,
//            LootTable.builder()
//                .pool(
//                    LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(1.0F))
//                        .with(
//                            ItemEntry.builder(Items.FEATHER)
//                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
//                                .apply(EnchantedCountIncreaseLootFunction.builder(this.registryLookupFuture.join(), UniformLootNumberProvider.create(0.0F, 1.0F)))
//                        )
//                )
//                .randomSequenceId(Identifier.of(FowlPlay.ID, "entities/" + Registries.ENTITY_TYPE.getId(type).getPath()))
//        );
//    }
//
//    private void register(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> biConsumer, EntityType<?> type, LootTable.Builder builder) {
//        biConsumer.accept(type.getLootTableId(), builder);
//    }
//}
