//? if forge {
/*package aqario.fowlplay.forge.core;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.forge.client.FowlPlayForgeClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Objects;

@Mod(FowlPlay.ID)
public final class FowlPlayForge {
    public FowlPlayForge() {
        FowlPlay.init();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            new FowlPlayForgeClient();
        }
    }

    @SuppressWarnings("removal")
    public static IEventBus eventBus() {
        return Objects.requireNonNull(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
*///?}