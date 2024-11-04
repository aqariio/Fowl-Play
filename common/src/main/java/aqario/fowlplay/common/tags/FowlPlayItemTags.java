package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class FowlPlayItemTags {
    public static final TagKey<Item> BLUE_JAY_FOOD = create("blue_jay_food");
    public static final TagKey<Item> CARDINAL_FOOD = create("cardinal_food");
    public static final TagKey<Item> GULL_FOOD = create("gull_food");
    public static final TagKey<Item> PENGUIN_FOOD = create("penguin_food");
    public static final TagKey<Item> PIGEON_FOOD = create("pigeon_food");
    public static final TagKey<Item> ROBIN_FOOD = create("robin_food");
    public static final TagKey<Item> SPARROW_FOOD = create("robin_food");

    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(FowlPlay.ID, id));
    }
}
