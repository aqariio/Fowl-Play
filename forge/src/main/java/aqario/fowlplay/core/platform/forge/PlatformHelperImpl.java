package aqario.fowlplay.core.platform.forge;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.entity.ai.brain.ExtendedSchedule;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.CommonRegistry;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PlatformHelperImpl {
    public static final Object2ObjectOpenHashMap<Supplier<Item>, ResourceKey<CreativeModeTab>> ITEM_TO_GROUPS = new Object2ObjectOpenHashMap<>();
    public static final DeferredRegister<ChickenVariant> CHICKEN_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.CHICKEN_VARIANT.location(),
        FowlPlay.ID
    );
    public static final DeferredRegister<DuckVariant> DUCK_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.DUCK_VARIANT.location(),
        FowlPlay.ID
    );
    public static final DeferredRegister<GooseVariant> GOOSE_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.GOOSE_VARIANT.location(),
        FowlPlay.ID
    );
    public static final DeferredRegister<GullVariant> GULL_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.GULL_VARIANT.location(),
        FowlPlay.ID
    );
    public static final DeferredRegister<PigeonVariant> PIGEON_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.PIGEON_VARIANT.location(),
        FowlPlay.ID
    );
    public static final DeferredRegister<SparrowVariant> SPARROW_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.SPARROW_VARIANT.location(),
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
    public static final DeferredRegister<Schedule> SCHEDULES = DeferredRegister.create(
        ForgeRegistries.SCHEDULES,
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
    public static final DeferredRegister<EntityDataSerializer<?>> TRACKED_DATA_HANDLERS = DeferredRegister.create(
        ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS,
        FowlPlay.ID
    );
    public static final ObjectArrayList<Pair<ModelLayerLocation, Supplier<LayerDefinition>>> MODEL_LAYERS = new ObjectArrayList<>();
    public static final ObjectArrayList<Pair<Supplier<EntityType<?>>, EntityRendererProvider<?>>> ENTITY_RENDERERS = new ObjectArrayList<>();

    @SuppressWarnings("unchecked")
    public static <T> Supplier<T> registerVariant(String id, Supplier<T> variant) {
        if(variant.get() instanceof ChickenVariant) {
            return (Supplier<T>) CHICKEN_VARIANTS.register(id, (Supplier<ChickenVariant>) variant);
        }
        else if(variant.get() instanceof DuckVariant) {
            return (Supplier<T>) DUCK_VARIANTS.register(id, (Supplier<DuckVariant>) variant);
        }
        else if(variant.get() instanceof GooseVariant) {
            return (Supplier<T>) GOOSE_VARIANTS.register(id, (Supplier<GooseVariant>) variant);
        }
        else if(variant.get() instanceof GullVariant) {
            return (Supplier<T>) GULL_VARIANTS.register(id, (Supplier<GullVariant>) variant);
        }
        else if(variant.get() instanceof PigeonVariant) {
            return (Supplier<T>) PIGEON_VARIANTS.register(id, (Supplier<PigeonVariant>) variant);
        }
        else if(variant.get() instanceof SparrowVariant) {
            return (Supplier<T>) SPARROW_VARIANTS.register(id, (Supplier<SparrowVariant>) variant);
        }
        return null;
    }

    public static Supplier<Activity> registerActivity(String id, Supplier<Activity> activity) {
        return ACTIVITIES.register(id, activity);
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType) {
        return ENTITY_TYPES.register(id, entityType);
    }

    public static Supplier<Item> registerItem(String id, Supplier<Item> item, ResourceKey<CreativeModeTab> group) {
        Supplier<Item> registry = ITEMS.register(id, item);
        addItemToItemGroup(registry, group);
        return registry;
    }

    public static <T extends Mob> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(id, () -> new ForgeSpawnEggItem(entityType, backgroundColor, highlightColor, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    }

    public static <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String id, Supplier<MemoryModuleType<T>> memoryModuleType) {
        return MEMORY_MODULE_TYPES.register(id, memoryModuleType);
    }

    public static Supplier<SimpleParticleType> registerParticleType(String id, Supplier<SimpleParticleType> particleType) {
        return PARTICLE_TYPES.register(id, particleType);
    }

    public static Supplier<ExtendedSchedule> registerSchedule(String id, Supplier<ExtendedSchedule> schedule) {
        return SCHEDULES.register(id, schedule);
    }

    public static <T extends Sensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<SensorType<T>> sensorType) {
        return SENSOR_TYPES.register(id, sensorType);
    }

    public static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> soundEvent) {
        return SOUND_EVENTS.register(id, soundEvent);
    }

    public static <T> Supplier<CommonRegistry<T>> registerRegistry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        return () -> null;
    }

    public static <T> void registerTrackedDataHandler(String id, EntityDataSerializer<T> handler) {
        TRACKED_DATA_HANDLERS.register(id, () -> handler);
    }

    public static void addItemToItemGroup(Supplier<Item> item, ResourceKey<CreativeModeTab> itemGroup) {
        ITEM_TO_GROUPS.put(item, itemGroup);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        ENTITY_RENDERERS.add(Pair.of((Supplier<EntityType<?>>) (Supplier<?>) type, provider));
    }

    public static void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        MODEL_LAYERS.add(Pair.of(location, definition));
    }

    public static <T extends ParticleOptions> void registerParticleFactory(Supplier<ParticleType<T>> supplier, ParticleProvider<T> provider) {
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeRegistry(CommonRegistry<T> registry, T value, FriendlyByteBuf buf) {
        buf.writeRegistryId((IForgeRegistry<T>) registry, value);
    }

    public static <T> T readRegistry(CommonRegistry<T> registry, Class<T> clazz, FriendlyByteBuf buf) {
        return buf.readRegistryIdSafe(clazz);
    }
}
