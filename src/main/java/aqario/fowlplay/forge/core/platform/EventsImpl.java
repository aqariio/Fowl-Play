//? if forge {
/*package aqario.fowlplay.forge.core.platform;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

import java.util.function.Consumer;

public class EventsImpl {
    public static void serverLevelTickPost(Consumer<ServerLevel> consumer) {
        MinecraftForge.EVENT_BUS.<TickEvent.LevelTickEvent>addListener(event -> {
            if(event.phase == TickEvent.LevelTickEvent.Phase.END && event.level instanceof ServerLevel level) {
                consumer.accept(level);
            }
        });
    }
}
*///?}
