package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.common.entity.ai.brain.ExtendedSchedule;
import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
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
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PlatformHelperImpl {
    public static final Object2ObjectOpenHashMap<Supplier<Item>, ResourceKey<CreativeModeTab>> ITEM_TO_GROUPS = new Object2ObjectOpenHashMap<>();
    public static final DeferredRegister<ChickenVariant> CHICKEN_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.CHICKEN_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<DuckVariant> DUCK_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.DUCK_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<GooseVariant> GOOSE_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.GOOSE_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<GullVariant> GULL_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.GULL_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<PigeonVariant> PIGEON_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.PIGEON_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<SparrowVariant> SPARROW_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.SPARROW_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create(
        BuiltInRegistries.ACTIVITY,
        FowlPlay.ID
    );
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(
        FowlPlay.ID
    );
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
        BuiltInRegistries.ENTITY_TYPE,
        FowlPlay.ID
    );
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(
        FowlPlay.ID
    );
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(
        BuiltInRegistries.MEMORY_MODULE_TYPE,
        FowlPlay.ID
    );
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
        BuiltInRegistries.PARTICLE_TYPE,
        FowlPlay.ID
    );
    public static final DeferredRegister<Schedule> SCHEDULES = DeferredRegister.create(
        BuiltInRegistries.SCHEDULE,
        FowlPlay.ID
    );
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(
        BuiltInRegistries.SENSOR_TYPE,
        FowlPlay.ID
    );
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
        BuiltInRegistries.SOUND_EVENT,
        FowlPlay.ID
    );
    public static final ObjectArrayList<Registry<?>> REGISTRIES = new ObjectArrayList<>();
    public static final DeferredRegister<EntityDataSerializer<?>> TRACKED_DATA_HANDLERS = DeferredRegister.create(
        NeoForgeRegistries.ENTITY_DATA_SERIALIZERS,
        FowlPlay.ID
    );
    public static final ObjectArrayList<Pair<ModelLayerLocation, Supplier<LayerDefinition>>> MODEL_LAYERS = new ObjectArrayList<>();
    public static final ObjectArrayList<Pair<Supplier<EntityType<?>>, EntityRendererProvider<?>>> ENTITY_RENDERERS = new ObjectArrayList<>();

    @SuppressWarnings("unchecked")
    public static <T> void registerVariant(String id, ResourceKey<T> key, Supplier<T> variant) {
        if(key.isFor(FowlPlayRegistries.CHICKEN_VARIANT)) {
            CHICKEN_VARIANTS.register(id, (Supplier<ChickenVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.DUCK_VARIANT)) {
            DUCK_VARIANTS.register(id, (Supplier<DuckVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.GOOSE_VARIANT)) {
            GOOSE_VARIANTS.register(id, (Supplier<GooseVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.GULL_VARIANT)) {
            GULL_VARIANTS.register(id, (Supplier<GullVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.PIGEON_VARIANT)) {
            PIGEON_VARIANTS.register(id, (Supplier<PigeonVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.SPARROW_VARIANT)) {
            SPARROW_VARIANTS.register(id, (Supplier<SparrowVariant>) variant);
        }
    }

    public static Supplier<Activity> registerActivity(String id, Supplier<Activity> activity) {
        return ACTIVITIES.register(id, activity);
    }

    public static Supplier<Block> registerBlock(String id, Supplier<Block> block) {
        return BLOCKS.register(id, block);
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType) {
        return ENTITY_TYPES.register(id, entityType);
    }

    @SafeVarargs
    public static Supplier<Item> registerItem(String id, Supplier<Item> item, ResourceKey<CreativeModeTab>... groups) {
        Supplier<Item> registry = ITEMS.register(id, item);
        for(ResourceKey<CreativeModeTab> group : groups) {
            addItemToItemGroup(registry, group);
        }
        return registry;
    }

    @SafeVarargs
    public static Supplier<Item> registerBlockItem(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... groups) {
        return registerItem(id, () -> new BlockItem(block.get(), new Item.Properties()), groups);
    }

    public static <T extends Mob> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(id, () -> new DeferredSpawnEggItem(entityType, backgroundColor, highlightColor, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
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

    public static <T> Registry<T> registerRegistry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        if(sync) {
            builder.sync(true);
        }
        Registry<T> registry = builder.create();
        REGISTRIES.add(registry);
        return registry;
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
}
