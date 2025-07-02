package aqario.fowlplay.client.forge;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.core.FowlPlayParticleTypes;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FowlPlayForgeClient {
    public static void init(IEventBus modBus) {
        modBus.addListener(FowlPlayForgeClient::onClientSetup);
        modBus.addListener(FowlPlayForgeClient::onRegisterParticles);
        modBus.addListener(FowlPlayForgeClient::onRegisterEntityRenderers);
        modBus.addListener(FowlPlayForgeClient::onRegisterEntityLayers);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        FowlPlayClient.init();
    }

    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
//        FowlPlayClient.registerParticleFactories();
        event.registerSpriteSet(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
    }

    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        FowlPlayClient.registerEntityRenderers();
    }

    public static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        FowlPlayClient.registerModelLayers();
    }
}
