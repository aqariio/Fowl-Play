package pengugang.foulplay.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pengugang.foulplay.FoulPlay;
import pengugang.foulplay.entity.InitEntity;

public class InitItem {

    //Penguin Spawn Egg
    public static final net.minecraft.item.Item PENGUIN_SPAWN_EGG = registerItem("penguin_spawn_egg",
            new SpawnEggItem(InitEntity.PENGUIN, 0x151419, 0xfafafa,
                    new FabricItemSettings().group(ItemGroup.MISC)));



    // Registering Item
    private static net.minecraft.item.Item registerItem(String name, net.minecraft.item.Item item) {
        return Registry.register(Registry.ITEM, new Identifier(FoulPlay.MODID, name), item);
    }

    //Initialize Message
    public static void initialize() {
        FoulPlay.LOGGER.info("Registering Items for " + FoulPlay.MODID);
    }

}
