package aqario.fowlplay.client;

//? if neoforge

/*import aqario.fowlplay.loaders.neoforge.core.FowlPlayNeoForge;*/
//? if fabric

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class RenderRegistry {
    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        //? if fabric {
        EntityRendererRegistry.register(type.get(), provider);
         //?}
        //? if neoforge {
        /*FowlPlayNeoForge.eventBus().<EntityRenderersEvent.RegisterRenderers>addListener(event ->
            event.registerEntityRenderer(type.get(), provider)
        );
        *///?}
    }

    public static void modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        //? if fabric {
        EntityModelLayerRegistry.registerModelLayer(location, definition::get);
         //?}
        //? if neoforge {
        /*FowlPlayNeoForge.eventBus().<EntityRenderersEvent.RegisterLayerDefinitions>addListener(event ->
            event.registerLayerDefinition(location, definition)
        );
        *///?}
    }

    public static <T extends ParticleOptions, P extends ParticleType<T>> void particleFactory(Supplier<P> type, WrappedParticleProvider<T> provider) {
        //? if fabric {
        ParticleFactoryRegistry.getInstance().register(type.get(), provider::create);
         //?}
        //? if neoforge {
        /*FowlPlayNeoForge.eventBus().<RegisterParticleProvidersEvent>addListener(event ->
            event.registerSpriteSet(type.get(), provider::create)
        );
        *///?}
    }

    @FunctionalInterface
    public interface WrappedParticleProvider<T extends ParticleOptions> {
        ParticleProvider<T> create(SpriteSet spriteSet);
    }
}
