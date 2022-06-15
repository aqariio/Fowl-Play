package pengugang.fowlplay;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pengugang.fowlplay.entity.InitEntity;
import pengugang.fowlplay.util.ModRegistries;
import pengugang.fowlplay.world.gen.WorldGen;
import software.bernie.geckolib3.GeckoLib;

public class FowlPlay  implements ModInitializer {
    public static final String MODID = "fowlplay";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    // Items
    public static final Item PENGUIN_SPAWN_EGG = registerItem("penguin_spawn_egg",
            new SpawnEggItem(InitEntity.PENGUIN, 0x151419, 0xfafafa,
                    new Item.Settings().group(ItemGroup.MISC)));
    public static final Item SEAGULL_SPAWN_EGG = registerItem("seagull_spawn_egg",
            new SpawnEggItem(InitEntity.SEAGULL, 0xEAEDF0, 0x414547,
                    new Item.Settings().group(ItemGroup.MISC)));

    // Registering Item
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(FowlPlay.MODID, name), item);
    }


    // Sounds
    public static SoundEvent ENTITY_PENGUIN_AMBIENT = new SoundEvent(new Identifier(MODID, "entity.penguin.ambient"));
    public static SoundEvent ENTITY_PENGUIN_BABY_AMBIENT = new SoundEvent(new Identifier(MODID, "entity.penguin_baby.ambient"));
    public static SoundEvent ENTITY_PENGUIN_SWIM = new SoundEvent(new Identifier(MODID, "entity.penguin.swim"));
    public static SoundEvent ENTITY_PENGUIN_HURT = new SoundEvent(new Identifier(MODID, "entity.penguin.hurt"));
    public static SoundEvent ENTITY_PENGUIN_DEATH = new SoundEvent(new Identifier(MODID, "entity.penguin.death"));

    @Override
    public void onInitialize() {
        ModRegistries.registerModRegistries();
        WorldGen.generateWorldGen();
        GeckoLib.initialize();
    }
}
