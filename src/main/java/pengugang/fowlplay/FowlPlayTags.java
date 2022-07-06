package pengugang.fowlplay;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FowlPlayTags {
    // Items
    public static final TagKey<Item> SEAGULL_TEMPT_ITEMS = FowlPlayTags.register("seagull_tempt_items");

    // Register
    private static TagKey<Item> register(String name) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(FowlPlay.MODID, name));
    }
}
