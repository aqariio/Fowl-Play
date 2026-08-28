//~ expect_platform

package aqario.fowlplay.client;

import aqario.fowlplay.fabric.client.RenderRegistryImpl;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class RenderRegistry {
    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, BiFunction<EntityRendererProvider.Context, Supplier<EntityType<T>>, EntityRenderer<T>> renderer) {
        EntityRendererProvider<T> provider = context -> renderer.apply(context, type);
        entityRenderer(type, provider);
    }

    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        RenderRegistryImpl.entityRenderer(type, provider);
    }

    public static void modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        RenderRegistryImpl.modelLayer(location, definition);
    }

    public static <T extends ParticleOptions, P extends ParticleType<T>> void particleFactory(Supplier<P> supplier, WrappedParticleProvider<T> provider) {
        RenderRegistryImpl.particleFactory(supplier, provider);
    }

    @FunctionalInterface
    public interface WrappedParticleProvider<T extends ParticleOptions> {
        ParticleProvider<T> create(SpriteSet spriteSet);
    }
}