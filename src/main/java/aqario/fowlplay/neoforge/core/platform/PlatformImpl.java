//? if neoforge {
/*package aqario.fowlplay.neoforge.core.platform;

import net.neoforged.fml.ModList;

public class PlatformImpl {
    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static String getName() {
        return ModList.get().getModContainerById("fowlplay").get().getModInfo().getDisplayName();
    }

    public static String getVersion() {
        return ModList.get().getModContainerById("fowlplay").get().getModInfo().getVersion().getQualifier();
    }
}
*///?}