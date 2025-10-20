package aqario.fowlplay.core.platform;

import aqario.fowlplay.common.entity.ai.brain.ExtendedSchedule;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
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
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;

import java.util.function.Supplier;

public class PlatformHelper {
    @ExpectPlatform
    public static <T> Supplier<T> registerVariant(String id, Supplier<T> variant) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Activity> registerActivity(String id, Supplier<Activity> activity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Item> registerItem(String id, Supplier<Item> item, RegistryKey<ItemGroup> group) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends MobEntity> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String id, Supplier<MemoryModuleType<T>> memoryModuleType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<DefaultParticleType> registerParticleType(String id, Supplier<DefaultParticleType> particleType) {
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
    public static <T> Supplier<CommonRegistry<T>> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> void registerTrackedDataHandler(String id, TrackedDataHandler<T> handler) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addItemToItemGroup(Supplier<Item> item, RegistryKey<ItemGroup> itemGroup) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> type, EntityRendererFactory<T> provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerModelLayer(EntityModelLayer location, Supplier<TexturedModelData> definition) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends ParticleEffect> void registerParticleFactory(Supplier<ParticleType<T>> supplier, ParticleFactory<T> provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> void writeRegistry(CommonRegistry<T> registry, T value, PacketByteBuf buf) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> T readRegistry(CommonRegistry<T> registry, Class<T> clazz, PacketByteBuf buf) {
        throw new AssertionError();
    }
}
