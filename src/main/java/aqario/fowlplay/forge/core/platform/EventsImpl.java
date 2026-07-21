//? if forge {
/*package aqario.fowlplay.forge.core.platform;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.function.Consumer;

public class EventsImpl {
    public static void serverLevelTickPost(Consumer<ServerLevel> consumer) {
        NeoForge.EVENT_BUS.<LevelTickEvent.Post>addListener(event -> {
            if(event.getLevel() instanceof ServerLevel level) {
                consumer.accept(level);
            }
        });
    }
}
*///?}
