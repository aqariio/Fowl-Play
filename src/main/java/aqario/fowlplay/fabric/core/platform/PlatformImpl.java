//? if fabric {
package aqario.fowlplay.fabric.core.platform;

import net.fabricmc.loader.api.FabricLoader;

public class PlatformImpl {
    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static String getName() {
        return FabricLoader.getInstance().getModContainer("fowlplay").get().getMetadata().getName();
    }

    public static String getVersion() {
        return FabricLoader.getInstance().getModContainer("fowlplay").get().getMetadata().getVersion().getFriendlyString();
    }
}
//?}