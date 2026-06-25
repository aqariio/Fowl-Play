//? if neoforge {
package aqario.fowlplay.neoforge.core;

import aqario.fowlplay.core.FowlPlay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import java.util.Objects;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(IEventBus modBus) {
        FowlPlay.init();

        modBus.addListener(FowlPlayNeoForge::onNewRegistry);
        FPDataAttachments.ATTACHMENT_TYPES.register(modBus);
    }

    private static void onNewRegistry(NewRegistryEvent event) {
        FowlPlay.earlyInit();
    }

    public static IEventBus eventBus() {
        return Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus());
    }
}
//?}