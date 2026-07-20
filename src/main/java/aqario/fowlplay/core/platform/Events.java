//~ expect_platform

package aqario.fowlplay.core.platform;

import aqario.fowlplay.fabric.core.platform.EventsImpl;
import net.minecraft.server.level.ServerLevel;

import java.util.function.Consumer;

public class Events {
    public static void serverLevelTickPost(Consumer<ServerLevel> consumer) {
        EventsImpl.serverLevelTickPost(consumer);
    }
}
