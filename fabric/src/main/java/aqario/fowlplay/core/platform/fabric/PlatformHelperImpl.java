package aqario.fowlplay.core.platform.fabric;

import aqario.fowlplay.common.entity.ai.brain.ExtendedSchedule;
import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayBuiltInRegistries;
import aqario.fowlplay.core.FowlPlayRegistries;
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
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PlatformHelperImpl {
    @SuppressWarnings("unchecked")
    public static <T> void registerVariant(String id, ResourceKey<T> key, Supplier<T> variant) {
        if(key.isFor(FowlPlayRegistries.CHICKEN_VARIANT)) {
            Registry.register(FowlPlayBuiltInRegistries.CHICKEN_VARIANT, (ResourceKey<ChickenVariant>) key, (ChickenVariant) variant.get());
        }
        else if(key.isFor(FowlPlayRegistries.DUCK_VARIANT)) {
            Registry.register(FowlPlayBuiltInRegistries.DUCK_VARIANT, (ResourceKey<DuckVariant>) key, (DuckVariant) variant.get());
        }
        else if(key.isFor(FowlPlayRegistries.GOOSE_VARIANT)) {
            Registry.register(FowlPlayBuiltInRegistries.GOOSE_VARIANT, (ResourceKey<GooseVariant>) key, (GooseVariant) variant.get());
        }
        else if(key.isFor(FowlPlayRegistries.GULL_VARIANT)) {
            Registry.register(FowlPlayBuiltInRegistries.GULL_VARIANT, (ResourceKey<GullVariant>) key, (GullVariant) variant.get());
        }
        else if(key.isFor(FowlPlayRegistries.PIGEON_VARIANT)) {
            Registry.register(FowlPlayBuiltInRegistries.PIGEON_VARIANT, (ResourceKey<PigeonVariant>) key, (PigeonVariant) variant.get());
        }
        else if(key.isFor(FowlPlayRegistries.SPARROW_VARIANT)) {
            Registry.register(FowlPlayBuiltInRegistries.SPARROW_VARIANT, (ResourceKey<SparrowVariant>) key, (SparrowVariant) variant.get());
        }
    }

    public static Supplier<Activity> registerActivity(String id, Supplier<Activity> activity) {
        Activity registry = Registry.register(BuiltInRegistries.ACTIVITY, FowlPlay.id(id), activity.get());
        return () -> registry;
    }

    public static Supplier<Block> registerBlock(String id, Supplier<Block> block) {
        Block registry = Registry.register(BuiltInRegistries.BLOCK, FowlPlay.id(id), block.get());
        return () -> registry;
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType) {
        EntityType<T> registry = Registry.register(BuiltInRegistries.ENTITY_TYPE, FowlPlay.id(id), entityType.get());
        return () -> registry;
    }

    @SafeVarargs
    public static Supplier<Item> registerItem(String id, Supplier<Item> item, ResourceKey<CreativeModeTab>... groups) {
        Item registry = Registry.register(BuiltInRegistries.ITEM, FowlPlay.id(id), item.get());
        for(ResourceKey<CreativeModeTab> group : groups) {
            addItemToItemGroup(() -> registry, group);
        }
        return () -> registry;
    }

    @SafeVarargs
    public static Supplier<Item> registerBlockItem(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... groups) {
        return registerItem(id, () -> new BlockItem(block.get(), new Item.Properties()), groups);
    }

    public static <T extends Mob> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(id, () -> new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    }

    public static <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String id, Supplier<MemoryModuleType<T>> memoryModuleType) {
        MemoryModuleType<T> registry = Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, FowlPlay.id(id), memoryModuleType.get());
        return () -> registry;
    }

    public static Supplier<SimpleParticleType> registerParticleType(String id, Supplier<SimpleParticleType> particleType) {
        SimpleParticleType registry = Registry.register(BuiltInRegistries.PARTICLE_TYPE, FowlPlay.id(id), particleType.get());
        return () -> registry;
    }

    public static Supplier<ExtendedSchedule> registerSchedule(String id, Supplier<ExtendedSchedule> schedule) {
        ExtendedSchedule registry = Registry.register(BuiltInRegistries.SCHEDULE, FowlPlay.id(id), schedule.get());
        return () -> registry;
    }

    public static <T extends Sensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<SensorType<T>> sensorType) {
        SensorType<T> registry = Registry.register(BuiltInRegistries.SENSOR_TYPE, FowlPlay.id(id), sensorType.get());
        return () -> registry;
    }

    public static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> soundEvent) {
        SoundEvent registry = Registry.register(BuiltInRegistries.SOUND_EVENT, FowlPlay.id(id), soundEvent.get());
        return () -> registry;
    }

    public static <T> Registry<T> registerRegistry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        FabricRegistryBuilder<T, MappedRegistry<T>> builder = FabricRegistryBuilder.createSimple(registryKey);
        if(sync) {
            builder.attribute(RegistryAttribute.SYNCED);
        }
        return builder.buildAndRegister();
    }

    public static <T> void registerTrackedDataHandler(String id, EntityDataSerializer<T> handler) {
        EntityDataSerializers.registerSerializer(handler);
    }

    public static void addItemToItemGroup(Supplier<Item> item, ResourceKey<CreativeModeTab> itemGroup) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries ->
            entries.accept(item.get())
        );
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        EntityRendererRegistry.register(type.get(), provider);
    }

    public static void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        EntityModelLayerRegistry.registerModelLayer(location, definition::get);
    }

    public static <T extends ParticleOptions> void registerParticleFactory(Supplier<ParticleType<T>> supplier, ParticleProvider<T> provider) {
        ParticleFactoryRegistry.getInstance().register(supplier.get(), provider);
    }
}
