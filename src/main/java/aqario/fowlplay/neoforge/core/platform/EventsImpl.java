//? if neoforge {
/*package aqario.fowlplay.neoforge.core.platform;

import aqario.fowlplay.neoforge.core.FowlPlayNeoForge;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.function.Consumer;

public class EventsImpl {
    public static void serverLevelTickPost(Consumer<ServerLevel> consumer) {
        FowlPlayNeoForge.eventBus().<LevelTickEvent.Post>addListener(event -> {
            if(event.getLevel() instanceof ServerLevel level) {
                consumer.accept(level);
            }
        });
    }
}
*///?}
