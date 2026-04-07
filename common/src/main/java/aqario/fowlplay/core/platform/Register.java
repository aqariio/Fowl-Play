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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class Register {
    @ExpectPlatform
    public static <T> void variant(String id, ResourceKey<T> key, Supplier<T> variant) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Block> block(String id, Supplier<Block> block) {
        throw new AssertionError();
    }

    @SafeVarargs
    @ExpectPlatform
    public static Supplier<Item> item(String id, Supplier<Item> item, ResourceKey<CreativeModeTab>... groups) {
        throw new AssertionError();
    }

    @SafeVarargs
    @ExpectPlatform
    public static Supplier<Item> blockItem(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... groups) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> Supplier<Item> spawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> Registry<T> registry(ResourceKey<Registry<T>> registryKey, boolean sync) {
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
