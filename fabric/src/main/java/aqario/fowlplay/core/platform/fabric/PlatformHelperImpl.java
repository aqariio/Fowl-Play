package aqario.fowlplay.core.platform.fabric;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.CommonRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PlatformHelperImpl {
    @SuppressWarnings("unchecked")
    public static <T> Supplier<T> registerVariant(String id, Supplier<T> variant) {
        if(variant.get() instanceof ChickenVariant v) {
            T registry = (T) Registry.register((Registry<ChickenVariant>) FowlPlayRegistries.CHICKEN_VARIANT.get(), Identifier.of(FowlPlay.ID, id), v);
            return () -> registry;
        }
        else if(variant.get() instanceof DuckVariant v) {
            T registry = (T) Registry.register((Registry<DuckVariant>) FowlPlayRegistries.DUCK_VARIANT.get(), Identifier.of(FowlPlay.ID, id), v);
            return () -> registry;
        }
        else if(variant.get() instanceof GullVariant v) {
            T registry = (T) Registry.register((Registry<GullVariant>) FowlPlayRegistries.GULL_VARIANT.get(), Identifier.of(FowlPlay.ID, id), v);
            return () -> registry;
        }
        else if(variant.get() instanceof PigeonVariant v) {
            T registry = (T) Registry.register((Registry<PigeonVariant>) FowlPlayRegistries.PIGEON_VARIANT.get(), Identifier.of(FowlPlay.ID, id), v);
            return () -> registry;
        }
        else if(variant.get() instanceof SparrowVariant v) {
            T registry = (T) Registry.register((Registry<SparrowVariant>) FowlPlayRegistries.SPARROW_VARIANT.get(), Identifier.of(FowlPlay.ID, id), v);
            return () -> registry;
        }
        return null;
    }

    public static Supplier<Activity> registerActivity(String id, Supplier<Activity> activity) {
        Activity registry = Registry.register(Registries.ACTIVITY, Identifier.of(FowlPlay.ID, id), activity.get());
        return () -> registry;
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType) {
        EntityType<T> registry = Registry.register(Registries.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id), entityType.get());
        return () -> registry;
    }

    public static Supplier<Item> registerItem(String id, Supplier<Item> item, RegistryKey<ItemGroup> group) {
        Item registry = Registry.register(Registries.ITEM, Identifier.of(FowlPlay.ID, id), item.get());
        addItemToItemGroup(registry, group);
        return () -> registry;
    }

    public static <T extends MobEntity> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(id, () -> new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, new Item.Settings()), ItemGroups.SPAWN_EGGS);
    }

    public static <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String id, Supplier<MemoryModuleType<T>> memoryModuleType) {
        MemoryModuleType<T> registry = Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(FowlPlay.ID, id), memoryModuleType.get());
        return () -> registry;
    }

    public static Supplier<DefaultParticleType> registerParticleType(String id, Supplier<DefaultParticleType> particleType) {
        DefaultParticleType registry = Registry.register(Registries.PARTICLE_TYPE, Identifier.of(FowlPlay.ID, id), particleType.get());
        return () -> registry;
    }

    public static <T extends Sensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<SensorType<T>> sensorType) {
        SensorType<T> registry = Registry.register(Registries.SENSOR_TYPE, Identifier.of(FowlPlay.ID, id), sensorType.get());
        return () -> registry;
    }

    public static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> soundEvent) {
        SoundEvent registry = Registry.register(Registries.SOUND_EVENT, Identifier.of(FowlPlay.ID, id), soundEvent.get());
        return () -> registry;
    }

    @SuppressWarnings("unchecked")
    public static <T> Supplier<CommonRegistry<T>> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        FabricRegistryBuilder<T, SimpleRegistry<T>> builder = FabricRegistryBuilder.createSimple(registryKey);
        if(sync) {
            builder.attribute(RegistryAttribute.SYNCED);
        }
        Registry<T> registry = builder.buildAndRegister();
        return () -> (CommonRegistry<T>) registry;
    }

    public static <T> void registerTrackedDataHandler(String id, TrackedDataHandler<T> handler) {
        TrackedDataHandlerRegistry.register(handler);
    }

    public static void addItemToItemGroup(Item item, RegistryKey<ItemGroup> itemGroup) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries ->
            entries.add(item)
        );
    }

    public static <T extends ParticleEffect> void registerParticleFactory(Supplier<ParticleType<T>> supplier, ParticleFactory<T> provider) {
        ParticleFactoryRegistry.getInstance().register(supplier.get(), provider);
    }

    public static <T> void writeRegistry(CommonRegistry<T> registry, T value, PacketByteBuf buf) {
        buf.writeRegistryValue(registry.fowlplay$getRegistry(), value);
    }

    public static <T> T readRegistry(CommonRegistry<T> registry, Class<T> clazz, PacketByteBuf buf) {
        return buf.readRegistryValue(registry.fowlplay$getRegistry());
    }
}
