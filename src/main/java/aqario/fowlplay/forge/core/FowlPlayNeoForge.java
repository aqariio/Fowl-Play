//? if forge {
/*package aqario.fowlplay.forge.core;

import aqario.fowlplay.common.integration.YACLIntegration;
import aqario.fowlplay.core.FowlPlay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import java.util.Objects;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(IEventBus modBus) {
        FowlPlay.init();

        modBus.addListener(FowlPlayNeoForge::onNewRegistry);
        FPDataAttachments.ATTACHMENT_TYPES.register(modBus);

        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(
            IConfigScreenFactory.class, (client, parent) -> YACLIntegration.createScreen(parent)
        );
    }

    private static void onNewRegistry(NewRegistryEvent event) {
        FowlPlay.initRegistries();
    }

    public static IEventBus eventBus() {
        return Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus());
    }
}
*///?}