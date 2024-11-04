package aqario.fowlplay.common.item;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public final class FowlPlayItems {
    public static final Item BLUE_JAY_SPAWN_EGG = register(
        "blue_jay_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.BLUE_JAY, 0x598FCC, 0xCED8E5, new Item.Settings())
    );
    public static final Item CARDINAL_SPAWN_EGG = register(
        "cardinal_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.CARDINAL, 0xDB2929, 0x42312F, new Item.Settings())
    );
    public static final Item GULL_SPAWN_EGG = register(
        "gull_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.GULL, 0xeaedf0, 0xffd850, new Item.Settings())
    );
    public static final Item PENGUIN_SPAWN_EGG = register(
        "penguin_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.PENGUIN, 0x151419, 0xfafafa, new Item.Settings())
    );
    public static final Item PIGEON_SPAWN_EGG = register(
        "pigeon_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.PIGEON, 0xBBBDBF, 0x467A58, new Item.Settings())
    );
    public static final Item ROBIN_SPAWN_EGG = register(
        "robin_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.ROBIN, 0x66696A, 0xFF823F, new Item.Settings())
    );
    public static final Item SPARROW_SPAWN_EGG = register(
        "sparrow_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.SPARROW, 0x6B2F12, 0xBCAE91, new Item.Settings())
    );

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(FowlPlay.ID, id), item);
    }

    public static void init() {
    }
}
