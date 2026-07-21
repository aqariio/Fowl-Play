//? if forge {
/*package aqario.fowlplay.forge.core.platform;

import aqario.fowlplay.core.FowlPlay;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class PlatformImpl {
    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static String getName() {
        return ModList.get().getModContainerById(FowlPlay.ID).get().getModInfo().getDisplayName();
    }

    public static String getVersion() {
        return ModList.get().getModContainerById(FowlPlay.ID).get().getModInfo().getVersion().toString();
    }

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
*///?}