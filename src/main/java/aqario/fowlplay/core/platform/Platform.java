//~ expect_platform

package aqario.fowlplay.core.platform;

import aqario.fowlplay.fabric.core.platform.PlatformImpl;

import java.nio.file.Path;

public class Platform {
    public static boolean isModLoaded(String modId) {
        return PlatformImpl.isModLoaded(modId);
    }

    public static String getName() {
        return PlatformImpl.getName();
    }

    public static String getVersion() {
        return PlatformImpl.getVersion();
    }

    public static Path getConfigDirectory() {
        return PlatformImpl.getConfigDirectory();
    }
}
