package aqario.fowlplay.common.registry.fabric;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class ItemRegisterImpl {
    public static <T extends Mob> Item createSpawnEgg(
        Supplier<EntityType<T>> entity,
        int primaryColor,
        int secondaryColor,
        Item.Properties properties
    ) {
        return new SpawnEggItem(entity.get(), primaryColor, secondaryColor, properties);
    }
}
