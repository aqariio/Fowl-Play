package aqario.fowlplay.core.fabric;

import aqario.fowlplay.core.FowlPlay;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.fabricmc.api.ModInitializer;

public final class FowlPlayFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(FowlPlay.ID, EmptyLoadContext.INSTANCE, FowlPlay::init);
//        FowlPlayDataAttachments.init();
    }
}
