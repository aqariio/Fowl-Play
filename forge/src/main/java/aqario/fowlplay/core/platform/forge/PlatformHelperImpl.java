package aqario.fowlplay.core.platform.forge;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PlatformHelperImpl {
    public static final Object2ObjectOpenHashMap<Supplier<Item>, RegistryKey<ItemGroup>> ITEM_TO_GROUPS = new Object2ObjectOpenHashMap<>();
    public static final DeferredRegister<ChickenVariant> CHICKEN_VARIANTS = DeferredRegister.create(
        FowlPlayRegistryKeys.CHICKEN_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<DuckVariant> DUCK_VARIANTS = DeferredRegister.create(
        FowlPlayRegistryKeys.DUCK_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<GullVariant> GULL_VARIANTS = DeferredRegister.create(
        FowlPlayRegistryKeys.GULL_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<PigeonVariant> PIGEON_VARIANTS = DeferredRegister.create(
        FowlPlayRegistryKeys.PIGEON_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<SparrowVariant> SPARROW_VARIANTS = DeferredRegister.create(
        FowlPlayRegistryKeys.SPARROW_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create(
        ForgeRegistries.ACTIVITIES,
        FowlPlay.ID
    );
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
        ForgeRegistries.ENTITY_TYPES,
        FowlPlay.ID
    );
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS,
        FowlPlay.ID
    );
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(
        ForgeRegistries.MEMORY_MODULE_TYPES,
        FowlPlay.ID
    );
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
        ForgeRegistries.PARTICLE_TYPES,
        FowlPlay.ID
    );
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(
        ForgeRegistries.SENSOR_TYPES,
        FowlPlay.ID
    );
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
        ForgeRegistries.SOUND_EVENTS,
        FowlPlay.ID
    );
    public static final DeferredRegister<TrackedDataHandler<?>> TRACKED_DATA_HANDLERS = DeferredRegister.create(
        ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS,
        FowlPlay.ID
    );

    @SuppressWarnings("unchecked")
    public static <T> T registerVariant(String id, Supplier<T> variant) {
        if(variant.get() instanceof ChickenVariant) {
            return (T) CHICKEN_VARIANTS.register(id, (Supplier<ChickenVariant>) variant).get();
        }
        else if(variant.get() instanceof DuckVariant) {
            return (T) DUCK_VARIANTS.register(id, (Supplier<DuckVariant>) variant).get();
        }
        else if(variant.get() instanceof GullVariant) {
            return (T) GULL_VARIANTS.register(id, (Supplier<GullVariant>) variant).get();
        }
        else if(variant.get() instanceof PigeonVariant) {
            return (T) PIGEON_VARIANTS.register(id, (Supplier<PigeonVariant>) variant).get();
        }
        else if(variant.get() instanceof SparrowVariant) {
            return (T) SPARROW_VARIANTS.register(id, (Supplier<SparrowVariant>) variant).get();
        }
        return null;
    }

    public static Supplier<Activity> registerActivity(String id, Supplier<Activity> activity) {
        return ACTIVITIES.register(id, activity);
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType) {
        return ENTITY_TYPES.register(id, entityType);
    }

    // TODO: Add items to group automatically
    public static Supplier<Item> registerItem(String id, Supplier<Item> item, RegistryKey<ItemGroup> group) {
        Supplier<Item> registry = ITEMS.register(id, item);
//        addItemToItemGroup(registry, group);
        return registry;
    }

    public static <T extends MobEntity> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(id, () -> new ForgeSpawnEggItem(entityType, backgroundColor, highlightColor, new Item.Settings()), ItemGroups.SPAWN_EGGS);
    }

    public static <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String id, Supplier<MemoryModuleType<T>> memoryModuleType) {
        return MEMORY_MODULE_TYPES.register(id, memoryModuleType);
    }

    public static Supplier<DefaultParticleType> registerParticleType(String id, Supplier<DefaultParticleType> particleType) {
        return PARTICLE_TYPES.register(id, particleType);
    }

    public static <T extends Sensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<SensorType<T>> sensorType) {
        return SENSOR_TYPES.register(id, sensorType);
    }

    public static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> soundEvent) {
        return SOUND_EVENTS.register(id, soundEvent);
    }

    @SuppressWarnings("unchecked")
    public static <T> Registry<T> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        RegistryBuilder<T> builder = new RegistryBuilder<>();
        builder.setName(registryKey.getValue());
        if(!sync) {
            builder.disableSync();
        }
        if(registryKey.equals(FowlPlayRegistryKeys.CHICKEN_VARIANT)) {
            return (Registry<T>) CHICKEN_VARIANTS.makeRegistry(() -> (RegistryBuilder<ChickenVariant>) builder).get();
        }
        else if(registryKey.equals(FowlPlayRegistryKeys.DUCK_VARIANT)) {
            return (Registry<T>) DUCK_VARIANTS.makeRegistry(() -> (RegistryBuilder<DuckVariant>) builder).get();
        }
        else if(registryKey.equals(FowlPlayRegistryKeys.GULL_VARIANT)) {
            return (Registry<T>) GULL_VARIANTS.makeRegistry(() -> (RegistryBuilder<GullVariant>) builder).get();
        }
        else if(registryKey.equals(FowlPlayRegistryKeys.PIGEON_VARIANT)) {
            return (Registry<T>) PIGEON_VARIANTS.makeRegistry(() -> (RegistryBuilder<PigeonVariant>) builder).get();
        }
        else if(registryKey.equals(FowlPlayRegistryKeys.SPARROW_VARIANT)) {
            return (Registry<T>) SPARROW_VARIANTS.makeRegistry(() -> (RegistryBuilder<SparrowVariant>) builder).get();
        }
        return null;
    }

    public static <T> void registerTrackedDataHandler(String id, TrackedDataHandler<T> handler) {
        TRACKED_DATA_HANDLERS.register(id, () -> handler);
    }

    public static void addItemToItemGroup(Supplier<Item> item, RegistryKey<ItemGroup> itemGroup) {
        ITEM_TO_GROUPS.put(item, itemGroup);
    }

    public static <T extends ParticleEffect> void registerParticleFactory(Supplier<ParticleType<T>> supplier, ParticleFactory<T> provider) {
    }
}
