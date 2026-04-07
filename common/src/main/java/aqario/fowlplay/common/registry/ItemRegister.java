package aqario.fowlplay.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ItemRegister {
    private final CommonRegister<Item> items;

    private ItemRegister(String namespace) {
        this.items = CommonRegister.create(BuiltInRegistries.ITEM, namespace);
    }

    public static ItemRegister create(String namespace) {
        return new ItemRegister(namespace);
    }

    @SafeVarargs
    public final <T extends Item> Supplier<T> register(String id, Supplier<T> item, ResourceKey<CreativeModeTab>... tabs) {
        Supplier<T> entry = this.register(id, item);
        CreativeTab.add(entry, tabs);
        return entry;
    }

    public <T extends Item> Supplier<T> register(String id, Supplier<T> item) {
        return this.items.register(id, item);
    }

    public <T extends Mob> Supplier<Item> spawnEgg(
        String id,
        Supplier<EntityType<T>> entity,
        int primaryColor,
        int secondaryColor
    ) {
        return this.spawnEgg(id, entity, primaryColor, secondaryColor, new Item.Properties());
    }

    public <T extends Mob> Supplier<Item> spawnEgg(
        String id,
        Supplier<EntityType<T>> entity,
        int primaryColor,
        int secondaryColor,
        Item.Properties properties
    ) {
        return this.register(id, () -> createSpawnEgg(entity, primaryColor, secondaryColor, properties), CreativeModeTabs.SPAWN_EGGS);
    }

    @ExpectPlatform
    public static <T extends Mob> Item createSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties properties) {
        throw new AssertionError();
    }

    public void register() {
        this.items.register();
    }
}
