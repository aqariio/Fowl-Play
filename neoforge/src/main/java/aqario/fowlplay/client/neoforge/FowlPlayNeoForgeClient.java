package aqario.fowlplay.client.neoforge;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.core.FowlPlay;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod(value = FowlPlay.ID, dist = Dist.CLIENT)
public class FowlPlayNeoForgeClient {
    public FowlPlayNeoForgeClient(IEventBus modBus) {
        final var context = new NeoForgeLoadContext(modBus);
        BalmClient.initializeMod(FowlPlay.ID, context, FowlPlayClient::init);
    }

    public static void init(IEventBus modBus) {
//        modBus.addListener(FowlPlayNeoForgeClient::onClientSetup);
//        modBus.addListener(FowlPlayNeoForgeClient::onRegisterParticles);
//        modBus.addListener(FowlPlayNeoForgeClient::onRegisterEntityRenderers);
//        modBus.addListener(FowlPlayNeoForgeClient::onRegisterEntityLayers);
//
//        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(
//            IConfigScreenFactory.class, (client, parent) -> YACLIntegration.createScreen(parent)
//        );
    }

    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
//        FowlPlayClient.registerParticleFactories();
//        event.registerSpriteSet(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Provider::new);
    }

    @SuppressWarnings("unchecked")
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        FowlPlayClient.registerEntityRenderers();
//        PlatformHelperImpl.ENTITY_RENDERERS.forEach(pair ->
//            event.registerEntityRenderer(pair.getFirst().get(), (EntityRendererProvider<Entity>) pair.getSecond())
//        );
    }

    public static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        FowlPlayClient.registerModelLayers();
//        PlatformHelperImpl.MODEL_LAYERS.forEach(pair ->
//            event.registerLayerDefinition(pair.getFirst(), pair.getSecond())
//        );
    }
}
