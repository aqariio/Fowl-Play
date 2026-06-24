package aqario.fowlplay.loaders.neoforge.common.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.function.Supplier;

public class ItemRegisterImpl {
    public static <T extends Mob> Item createSpawnEgg(
        Supplier<EntityType<T>> entity,
        int primaryColor,
        int secondaryColor,
        Item.Properties properties
    ) {
        return new DeferredSpawnEggItem(entity, primaryColor, secondaryColor, properties);
    }
}
