//? if forge {
/*package aqario.fowlplay.forge.client;

import aqario.fowlplay.client.RenderRegistry;
import aqario.fowlplay.forge.core.FowlPlayForge;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class RenderRegistryImpl {
    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        FowlPlayForge.eventBus().<EntityRenderersEvent.RegisterRenderers>addListener(event ->
            event.registerEntityRenderer(type.get(), provider)
        );
    }

    public static void modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        FowlPlayForge.eventBus().<EntityRenderersEvent.RegisterLayerDefinitions>addListener(event ->
            event.registerLayerDefinition(location, definition)
        );
    }

    public static <T extends ParticleOptions, P extends ParticleType<T>> void particleFactory(Supplier<P> type, RenderRegistry.WrappedParticleProvider<T> provider) {
        FowlPlayForge.eventBus().<RegisterParticleProvidersEvent>addListener(event ->
            event.registerSpriteSet(type.get(), provider::create)
        );
    }
}
*///?}