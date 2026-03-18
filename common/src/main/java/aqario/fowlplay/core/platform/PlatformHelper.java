package aqario.fowlplay.core.platform;

import aqario.fowlplay.common.entity.ai.brain.ExtendedSchedule;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
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
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class PlatformHelper {
    @ExpectPlatform
    public static <T> void registerVariant(String id, ResourceKey<T> key, Supplier<T> variant) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Activity> registerActivity(String id, Supplier<Activity> activity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Block> registerBlock(String id, Supplier<Block> block) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType) {
        throw new AssertionError();
    }

    @SafeVarargs
    @ExpectPlatform
    public static Supplier<Item> registerItem(String id, Supplier<Item> item, ResourceKey<CreativeModeTab>... groups) {
        throw new AssertionError();
    }

    @SafeVarargs
    @ExpectPlatform
    public static Supplier<Item> registerBlockItem(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... groups) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String id, Supplier<MemoryModuleType<T>> memoryModuleType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<SimpleParticleType> registerParticleType(String id, Supplier<SimpleParticleType> particleType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<ExtendedSchedule> registerSchedule(String id, Supplier<ExtendedSchedule> schedule) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Sensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<SensorType<T>> sensorType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> soundEvent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> Registry<T> registerRegistry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> void registerTrackedDataHandler(String id, EntityDataSerializer<T> handler) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addItemToItemGroup(Supplier<Item> item, ResourceKey<CreativeModeTab> itemGroup) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends ParticleOptions> void registerParticleFactory(Supplier<ParticleType<T>> supplier, ParticleProvider<T> provider) {
        throw new AssertionError();
    }
}
