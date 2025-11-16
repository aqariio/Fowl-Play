package aqario.fowlplay.client.forge;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.common.integration.YACLIntegration;
import aqario.fowlplay.core.FowlPlayParticleTypes;
import aqario.fowlplay.core.platform.forge.PlatformHelperImpl;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FowlPlayForgeClient {
    @SuppressWarnings("removal")
    public static void init(IEventBus modBus) {
        modBus.addListener(FowlPlayForgeClient::onClientSetup);
        modBus.addListener(FowlPlayForgeClient::onRegisterParticles);
        modBus.addListener(FowlPlayForgeClient::onRegisterEntityRenderers);
        modBus.addListener(FowlPlayForgeClient::onRegisterEntityLayers);

        ModLoadingContext.get().getContainer().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(
                (client, screen) -> YACLIntegration.createScreen(screen)
            )
        );
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        FowlPlayClient.init();
    }

    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
//        FowlPlayClient.registerParticleFactories();
        event.registerSpriteSet(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
    }

    @SuppressWarnings("unchecked")
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        FowlPlayClient.registerEntityRenderers();
        PlatformHelperImpl.ENTITY_RENDERERS.forEach(pair ->
            event.registerEntityRenderer(pair.getFirst().get(), (EntityRendererProvider<Entity>) pair.getSecond())
        );
    }

    public static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        FowlPlayClient.registerModelLayers();
        PlatformHelperImpl.MODEL_LAYERS.forEach(pair ->
            event.registerLayerDefinition(pair.getFirst(), pair.getSecond())
        );
    }
}
