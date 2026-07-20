//? if neoforge {
/*package aqario.fowlplay.neoforge.core.platform;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

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

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
*///?}