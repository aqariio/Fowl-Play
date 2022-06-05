package pengugang.foulplay;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pengugang.foulplay.item.InitItem;
import pengugang.foulplay.util.ModRegistries;
import pengugang.foulplay.world.gen.WorldGen;
import software.bernie.geckolib3.GeckoLib;

public class FoulPlay  implements ModInitializer {
    public static final String MODID = "foulplay";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        InitItem.initialize();
        ModRegistries.registerModRegistries();
        WorldGen.generateWorldGen();
        GeckoLib.initialize();


    }
}
