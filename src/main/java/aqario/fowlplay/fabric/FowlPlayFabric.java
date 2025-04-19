//? if fabric {
package aqario.fowlplay.fabric;

import aqario.fowlplay.core.FowlPlay;
import net.fabricmc.api.ModInitializer;

public class FowlPlayFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FowlPlay.init();
    }
}
//?}