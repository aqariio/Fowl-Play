//? if neoforge {
/*package aqario.fowlplay.loaders.neoforge.client;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.common.integration.YACLIntegration;
import aqario.fowlplay.core.FowlPlay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = FowlPlay.ID, dist = Dist.CLIENT)
public class FowlPlayNeoForgeClient {
    public FowlPlayNeoForgeClient(IEventBus modBus) {
        FowlPlayClient.init();

        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(
            IConfigScreenFactory.class, (client, parent) -> YACLIntegration.createScreen(parent)
        );
    }
}
*///?}
