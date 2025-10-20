package aqario.fowlplay.core.tags;

import aqario.fowlplay.core.FowlPlay;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class FowlPlayItemTags {
    public static final TagKey<Item> BLUE_JAY_FOOD = create("blue_jay_food");
    public static final TagKey<Item> CARDINAL_FOOD = create("cardinal_food");
    public static final TagKey<Item> CHICKADEE_FOOD = create("chickadee_food");
    public static final TagKey<Item> CROW_FOOD = create("crow_food");
    public static final TagKey<Item> DUCK_FOOD = create("duck_food");
    public static final TagKey<Item> GOOSE_FOOD = create("goose_food");
    public static final TagKey<Item> GULL_FOOD = create("gull_food");
    public static final TagKey<Item> HAWK_FOOD = create("hawk_food");
    public static final TagKey<Item> PENGUIN_FOOD = create("penguin_food");
    public static final TagKey<Item> PIGEON_FOOD = create("pigeon_food");
    public static final TagKey<Item> RAVEN_FOOD = create("raven_food");
    public static final TagKey<Item> ROBIN_FOOD = create("robin_food");
    public static final TagKey<Item> SPARROW_FOOD = create("sparrow_food");

    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(FowlPlay.ID, id));
    }
}
