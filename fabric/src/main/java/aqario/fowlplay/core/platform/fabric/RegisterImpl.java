package aqario.fowlplay.core.platform.fabric;

import aqario.fowlplay.core.FowlPlay;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class RegisterImpl {
    @SuppressWarnings("unchecked")
    public static <T> void variant(String id, ResourceKey<T> key, Supplier<T> variant) {
        Registry.register((Registry<T>) BuiltInRegistries.REGISTRY.get(key.registry()), key, variant.get());
    }

    public static Supplier<Block> block(String id, Supplier<Block> block) {
        Block registry = Registry.register(BuiltInRegistries.BLOCK, FowlPlay.id(id), block.get());
        return () -> registry;
    }

    @SafeVarargs
    public static Supplier<Item> item(String id, Supplier<Item> item, ResourceKey<CreativeModeTab>... groups) {
        Item registry = Registry.register(BuiltInRegistries.ITEM, FowlPlay.id(id), item.get());
        for(ResourceKey<CreativeModeTab> group : groups) {
            addItemToItemGroup(() -> registry, group);
        }
        return () -> registry;
    }

    @SafeVarargs
    public static Supplier<Item> blockItem(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... groups) {
        return item(id, () -> new BlockItem(block.get(), new Item.Properties()), groups);
    }

    public static <T extends Mob> Supplier<Item> spawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return item(id, () -> new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    }

    public static <T> Registry<T> registry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        FabricRegistryBuilder<T, MappedRegistry<T>> builder = FabricRegistryBuilder.createSimple(registryKey);
        if(sync) {
            builder.attribute(RegistryAttribute.SYNCED);
        }
        return builder.buildAndRegister();
    }

    public static void addItemToItemGroup(Supplier<Item> item, ResourceKey<CreativeModeTab> itemGroup) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries ->
            entries.accept(item.get())
        );
    }

    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        EntityRendererRegistry.register(type.get(), provider);
    }

    public static void modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        EntityModelLayerRegistry.registerModelLayer(location, definition::get);
    }

    public static <T extends ParticleOptions> void particleFactory(Supplier<ParticleType<T>> supplier, ParticleProvider<T> provider) {
        ParticleFactoryRegistry.getInstance().register(supplier.get(), provider);
    }
}
