package aqario.fowlplay.core;

import aqario.fowlplay.common.item.ScarecrowItem;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKey;

import java.util.function.Supplier;

public final class FowlPlayItems {
    public static final Supplier<Item> BLUE_JAY_SPAWN_EGG = registerSpawnEgg(
        "blue_jay_spawn_egg",
        FowlPlayEntityType.BLUE_JAY,
        0x598FCC,
        0xCED8E5
    );
    public static final Supplier<Item> CARDINAL_SPAWN_EGG = registerSpawnEgg(
        "cardinal_spawn_egg",
        FowlPlayEntityType.CARDINAL,
        0xDB2929,
        0x42312F
    );
    public static final Supplier<Item> CHICKADEE_SPAWN_EGG = registerSpawnEgg(
        "chickadee_spawn_egg",
        FowlPlayEntityType.CHICKADEE,
        0xE8E5E1,
        0x8A8B8E
    );
    public static final Supplier<Item> CROW_SPAWN_EGG = registerSpawnEgg(
        "crow_spawn_egg",
        FowlPlayEntityType.CROW,
        0x3B3B3D,
        0x1C1C1E
    );
    public static final Supplier<Item> DUCK_SPAWN_EGG = registerSpawnEgg(
        "duck_spawn_egg",
        FowlPlayEntityType.DUCK,
        0xA58C7C,
        0x1D7F3C
    );
    public static final Supplier<Item> GOOSE_SPAWN_EGG = registerSpawnEgg(
        "goose_spawn_egg",
        FowlPlayEntityType.GOOSE,
        0xC6BFBC,
        0xA3958F
    );
    public static final Supplier<Item> GULL_SPAWN_EGG = registerSpawnEgg(
        "gull_spawn_egg",
        FowlPlayEntityType.GULL,
        0xeaedf0,
        0xffd850
    );
    public static final Supplier<Item> HAWK_SPAWN_EGG = registerSpawnEgg(
        "hawk_spawn_egg",
        FowlPlayEntityType.HAWK,
        0x544135,
        0xE5D8C0
    );
    public static final Supplier<Item> PENGUIN_SPAWN_EGG = registerSpawnEgg(
        "penguin_spawn_egg",
        FowlPlayEntityType.PENGUIN,
        0x151419,
        0xfafafa
    );
    public static final Supplier<Item> PIGEON_SPAWN_EGG = registerSpawnEgg(
        "pigeon_spawn_egg",
        FowlPlayEntityType.PIGEON,
        0xBBBDBF,
        0x467A58
    );
    public static final Supplier<Item> RAVEN_SPAWN_EGG = registerSpawnEgg(
        "raven_spawn_egg",
        FowlPlayEntityType.RAVEN,
        0x3B3B3D,
        0x1C1C1E
    );
    public static final Supplier<Item> ROBIN_SPAWN_EGG = registerSpawnEgg(
        "robin_spawn_egg",
        FowlPlayEntityType.ROBIN,
        0x66696A,
        0xFF823F
    );
    public static final Supplier<Item> SPARROW_SPAWN_EGG = registerSpawnEgg(
        "sparrow_spawn_egg",
        FowlPlayEntityType.SPARROW,
        0x5B3423,
        0xBCAE91
    );
    public static final Supplier<Item> SCARECROW = register(
        "scarecrow",
        () -> new ScarecrowItem(new Item.Settings()
            .maxCount(16)
        ),
        ItemGroups.FUNCTIONAL
    );

    private static <T extends MobEntity> Supplier<Item> registerSpawnEgg(String id, Supplier<EntityType<T>> type, int backgroundColor, int highlightColor) {
        return PlatformHelper.registerSpawnEggItem(id, type, backgroundColor, highlightColor);
    }

    private static Supplier<Item> register(String id, Supplier<Item> item, RegistryKey<ItemGroup> group) {
        return PlatformHelper.registerItem(id, item, group);
    }

    public static void init() {
    }
}
