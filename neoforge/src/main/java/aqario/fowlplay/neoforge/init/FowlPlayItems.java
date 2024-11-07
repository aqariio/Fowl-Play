package aqario.fowlplay.neoforge.init;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import net.minecraft.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public final class FowlPlayItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FowlPlay.ID);

    public static final DeferredItem<Item> BLUE_JAY_SPAWN_EGG = ITEMS.register(
        "blue_jay_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> FowlPlayEntityType.BLUE_JAY, 0x598FCC, 0xCED8E5, new Item.Settings())
    );
    public static final DeferredItem<Item> CARDINAL_SPAWN_EGG = ITEMS.register(
        "cardinal_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> FowlPlayEntityType.CARDINAL, 0xDB2929, 0x42312F, new Item.Settings())
    );
    public static final DeferredItem<Item> GULL_SPAWN_EGG = ITEMS.register(
        "gull_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> FowlPlayEntityType.GULL, 0xeaedf0, 0xffd850, new Item.Settings())
    );
    public static final DeferredItem<Item> PENGUIN_SPAWN_EGG = ITEMS.register(
        "penguin_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> FowlPlayEntityType.PENGUIN, 0x151419, 0xfafafa, new Item.Settings())
    );
    public static final DeferredItem<Item> PIGEON_SPAWN_EGG = ITEMS.register(
        "pigeon_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> FowlPlayEntityType.PIGEON, 0xBBBDBF, 0x467A58, new Item.Settings())
    );
    public static final DeferredItem<Item> ROBIN_SPAWN_EGG = ITEMS.register(
        "robin_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> FowlPlayEntityType.ROBIN, 0x66696A, 0xFF823F, new Item.Settings())
    );
    public static final DeferredItem<Item> SPARROW_SPAWN_EGG = ITEMS.register(
        "sparrow_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> FowlPlayEntityType.SPARROW, 0x6B2F12, 0xBCAE91, new Item.Settings())
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
