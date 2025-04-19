//? if fabric {
package aqario.fowlplay.fabric;

import aqario.fowlplay.core.FowlPlay;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FowlPlayFabric implements ModInitializer {

    public static boolean isYACLLoaded() {
        return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
    }

    public static boolean isDebugUtilsLoaded() {
        return FabricLoader.getInstance().isModLoaded("debugutils");
    }

    @Override
    public void onInitialize() {
        FowlPlay.init();
    }
}
//?}