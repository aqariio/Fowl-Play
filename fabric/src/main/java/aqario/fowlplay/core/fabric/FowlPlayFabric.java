package aqario.fowlplay.core.fabric;

import aqario.fowlplay.core.FowlPlay;
import net.fabricmc.api.ModInitializer;

public final class FowlPlayFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FowlPlay.earlyInit();
        FowlPlay.init();
        FowlPlayDataAttachments.init();
    }
}
