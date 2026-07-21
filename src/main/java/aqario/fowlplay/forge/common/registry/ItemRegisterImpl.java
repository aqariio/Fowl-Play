<<<<<<<< HEAD:forge/src/main/java/aqario/fowlplay/common/registry/forge/ItemRegisterImpl.java
package aqario.fowlplay.common.registry.forge;
========
//? if forge {
/*package aqario.fowlplay.forge.common.registry;
>>>>>>>> origin/dev/1.2:src/main/java/aqario/fowlplay/neoforge/common/registry/ItemRegisterImpl.java

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class ItemRegisterImpl {
    public static <T extends Mob> Item createSpawnEgg(
        Supplier<EntityType<T>> entity,
        int primaryColor,
        int secondaryColor,
        Item.Properties properties
    ) {
        return new ForgeSpawnEggItem(entity, primaryColor, secondaryColor, properties);
    }
}
*///?}