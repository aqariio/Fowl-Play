package aqario.fowlplay.client.forge;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.common.integration.YACLIntegration;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class FowlPlayForgeClient {
    @SuppressWarnings("removal")
    public FowlPlayForgeClient() {
        FowlPlayClient.init();

        ModLoadingContext.get().getContainer().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(
                (client, screen) -> YACLIntegration.createScreen(screen)
            )
        );
    }
}
