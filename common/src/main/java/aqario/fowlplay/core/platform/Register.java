package aqario.fowlplay.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class Register {
    @ExpectPlatform
    public static <T> Registry<T> registry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        throw new AssertionError();
    }

    @SafeVarargs
    @ExpectPlatform
    public static <T extends Item> void addToItemGroup(Supplier<T> item, ResourceKey<CreativeModeTab>... tab) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends ParticleOptions, P extends ParticleType<T>> void particleFactory(Supplier<P> supplier, WrappedParticleProvider<T> provider) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface WrappedParticleProvider<T extends ParticleOptions> {
        ParticleProvider<T> create(SpriteSet spriteSet);
    }
}
