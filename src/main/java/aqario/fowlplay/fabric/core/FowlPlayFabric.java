//? if fabric {
package aqario.fowlplay.fabric.core;

import aqario.fowlplay.core.FowlPlay;
import net.fabricmc.api.ModInitializer;

public final class FowlPlayFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FowlPlay.earlyInit();
        FowlPlay.init();
        FPDataAttachments.init();
    }
}
//?}
