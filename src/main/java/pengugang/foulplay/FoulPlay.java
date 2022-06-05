package pengugang.foulplay;

import net.fabricmc.api.ModInitializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pengugang.foulplay.item.InitItem;
import pengugang.foulplay.util.ModRegistries;
import pengugang.foulplay.world.gen.WorldGen;
import software.bernie.geckolib3.GeckoLib;

public class FoulPlay  implements ModInitializer {
    public static final String MODID = "foulplay";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    //SOUNDS
    public static SoundEvent ENTITY_PENGUIN_AMBIENT = new SoundEvent(new Identifier(MODID, "entity.penguin.ambient"));
    public static SoundEvent ENTITY_PENGUIN_BABY_AMBIENT = new SoundEvent(new Identifier(MODID, "entity.penguin_baby.ambient"));
    public static SoundEvent ENTITY_PENGUIN_SWIM_AMBIENT = new SoundEvent(new Identifier(MODID, "entity.penguin_swim.ambient"));
    public static SoundEvent ENTITY_PENGUIN_HURT = new SoundEvent(new Identifier(MODID, "entity.penguin.hurt"));
    public static SoundEvent ENTITY_PENGUIN_DEATH = new SoundEvent(new Identifier(MODID, "entity.penguin.death"));

    @Override
    public void onInitialize() {
        InitItem.initialize();
        ModRegistries.registerModRegistries();
        WorldGen.generateWorldGen();
        GeckoLib.initialize();
    }
}
