package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.util.RegistryBuilder.Properties;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import aqario.fowlplay.core.platform.PlatformHelper;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;

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
    public static final DeferredRegister<GooseVariant> GOOSE_VARIANTS = DeferredRegister.create(
        FowlPlayRegistryKeys.GOOSE_VARIANT,
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
        Registries.ACTIVITY,
        FowlPlay.ID
    );
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
        Registries.ENTITY_TYPE,
        FowlPlay.ID
    );
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(
        FowlPlay.ID
    );
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(
        Registries.MEMORY_MODULE_TYPE,
        FowlPlay.ID
    );
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
        Registries.PARTICLE_TYPE,
        FowlPlay.ID
    );
    public static final DeferredRegister<Schedule> SCHEDULES = DeferredRegister.create(
        Registries.SCHEDULE,
        FowlPlay.ID
    );
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(
        Registries.SENSOR_TYPE,
        FowlPlay.ID
    );
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
        Registries.SOUND_EVENT,
        FowlPlay.ID
    );
    public static final ObjectArrayList<Registry<?>> REGISTRIES = new ObjectArrayList<>();
    public static final DeferredRegister<TrackedDataHandler<?>> TRACKED_DATA_HANDLERS = DeferredRegister.create(
        NeoForgeRegistries.ENTITY_DATA_SERIALIZERS,
        FowlPlay.ID
    );
    public static final ObjectArrayList<Pair<EntityModelLayer, Supplier<TexturedModelData>>> MODEL_LAYERS = new ObjectArrayList<>();
    public static final ObjectArrayList<Pair<Supplier<EntityType<?>>, EntityRendererFactory<?>>> ENTITY_RENDERERS = new ObjectArrayList<>();

    @SuppressWarnings("unchecked")
    public static <T> void registerVariant(String id, RegistryKey<T> key, Supplier<T> variant) {
        if(key.isOf(FowlPlayRegistryKeys.CHICKEN_VARIANT)) {
            CHICKEN_VARIANTS.register(id, (Supplier<ChickenVariant>) variant);
        }
        else if(key.isOf(FowlPlayRegistryKeys.DUCK_VARIANT)) {
            DUCK_VARIANTS.register(id, (Supplier<DuckVariant>) variant);
        }
        else if(key.isOf(FowlPlayRegistryKeys.GOOSE_VARIANT)) {
            GOOSE_VARIANTS.register(id, (Supplier<GooseVariant>) variant);
        }
        else if(key.isOf(FowlPlayRegistryKeys.GULL_VARIANT)) {
            GULL_VARIANTS.register(id, (Supplier<GullVariant>) variant);
        }
        else if(key.isOf(FowlPlayRegistryKeys.PIGEON_VARIANT)) {
            PIGEON_VARIANTS.register(id, (Supplier<PigeonVariant>) variant);
        }
        else if(key.isOf(FowlPlayRegistryKeys.SPARROW_VARIANT)) {
            SPARROW_VARIANTS.register(id, (Supplier<SparrowVariant>) variant);
        }
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
        PlatformHelper.addItemToItemGroup(registry, group);
        return registry;
    }

    public static <T extends MobEntity> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(id, () -> new DeferredSpawnEggItem(entityType, backgroundColor, highlightColor, new Item.Settings()), ItemGroups.SPAWN_EGGS);
    }

    public static <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String id, Supplier<MemoryModuleType<T>> memoryModuleType) {
        return MEMORY_MODULE_TYPES.register(id, memoryModuleType);
    }

    public static Supplier<SimpleParticleType> registerParticleType(String id, Supplier<SimpleParticleType> particleType) {
        return PARTICLE_TYPES.register(id, particleType);
    }

    public static Supplier<SmartBrainSchedule> registerSchedule(String id, Supplier<SmartBrainSchedule> schedule) {
        return SCHEDULES.register(id, schedule);
    }

    public static <T extends Sensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<SensorType<T>> sensorType) {
        return SENSOR_TYPES.register(id, sensorType);
    }

    public static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> soundEvent) {
        return SOUND_EVENTS.register(id, soundEvent);
    }

    public static <T, R extends MutableRegistry<T>> R registerRegistry(RegistryKey<Registry<T>> registryKey, Properties properties) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        if(properties.sync()) {
            builder.sync(true);
        }
        if(properties.defaultId() != null) {
            builder.defaultKey(properties.defaultId());
        }
        R registry = (R) builder.create();
        REGISTRIES.add(registry);
        return registry;
    }

    public static <T> void registerTrackedDataHandler(String id, TrackedDataHandler<T> handler) {
        TRACKED_DATA_HANDLERS.register(id, () -> handler);
    }

    public static void addItemToItemGroup(Supplier<Item> item, RegistryKey<ItemGroup> itemGroup) {
        ITEM_TO_GROUPS.put(item, itemGroup);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> type, EntityRendererFactory<T> provider) {
        ENTITY_RENDERERS.add(Pair.of((Supplier<EntityType<?>>) (Supplier<?>) type, provider));
    }

    public static void registerModelLayer(EntityModelLayer location, Supplier<TexturedModelData> definition) {
        MODEL_LAYERS.add(Pair.of(location, definition));
    }

    public static <T extends ParticleEffect> void registerParticleFactory(Supplier<ParticleType<T>> supplier, ParticleFactory<T> provider) {
    }
}
