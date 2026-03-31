package aqario.fowlplay.client.fabric;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.core.FowlPlay;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.fabricmc.api.ClientModInitializer;

public final class FowlPlayFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BalmClient.initializeMod(FowlPlay.ID, EmptyLoadContext.INSTANCE, FowlPlayClient::init);
//        FowlPlayClient.registerModelLayers();
//        FowlPlayClient.registerEntityRenderers();
//        FowlPlayClient.registerParticleFactories();
//        ParticleFactoryRegistry.getInstance().register(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
    }
}
