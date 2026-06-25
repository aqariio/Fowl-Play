//? if fabric {
package aqario.fowlplay.fabric.core.platform;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerLevel;

import java.util.function.Consumer;

public class EventsImpl {
    public static void serverLevelTickPost(Consumer<ServerLevel> consumer) {
        ServerTickEvents.END_WORLD_TICK.register(consumer::accept);
    }
}
//?}
