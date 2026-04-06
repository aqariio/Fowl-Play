package aqario.fowlplay.client.neoforge;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.common.integration.YACLIntegration;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayParticleTypes;
import aqario.fowlplay.core.platform.neoforge.RegisterImpl;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = FowlPlay.ID, dist = Dist.CLIENT)
public class FowlPlayNeoForgeClient {
    public FowlPlayNeoForgeClient(IEventBus modBus) {
        modBus.addListener(FowlPlayNeoForgeClient::onClientSetup);
        modBus.addListener(FowlPlayNeoForgeClient::onRegisterParticles);
        modBus.addListener(FowlPlayNeoForgeClient::onRegisterEntityRenderers);
        modBus.addListener(FowlPlayNeoForgeClient::onRegisterEntityLayers);

        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(
            IConfigScreenFactory.class, (client, parent) -> YACLIntegration.createScreen(parent)
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
        RegisterImpl.ENTITY_RENDERERS.forEach(pair ->
            event.registerEntityRenderer(pair.getFirst().get(), (EntityRendererProvider<Entity>) pair.getSecond())
        );
    }

    public static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        FowlPlayClient.registerModelLayers();
        RegisterImpl.MODEL_LAYERS.forEach(pair ->
            event.registerLayerDefinition(pair.getFirst(), pair.getSecond())
        );
    }
}
