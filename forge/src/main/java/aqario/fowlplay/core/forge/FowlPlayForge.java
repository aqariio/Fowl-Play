package aqario.fowlplay.core.forge;

import aqario.fowlplay.core.FowlPlay;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;

@Mod(FowlPlay.ID)
public final class FowlPlayForge {
    public FowlPlayForge() {
        FowlPlay.init();
    }

    @SuppressWarnings("removal")
    public static IEventBus eventBus() {
        return Objects.requireNonNull(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
