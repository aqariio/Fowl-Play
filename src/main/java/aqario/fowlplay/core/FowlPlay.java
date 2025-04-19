package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";
    private static final String YACL_ID = "yet_another_config_lib_v3";
    private static final String DEBUG_UTILS_ID = "debugutils";

    public static boolean isYACLLoaded() {
        //? if fabric {
        return FabricLoader.getInstance().isModLoaded(YACL_ID);
        //?} else if forge {
        /*return ModList.get().isLoaded(YACL_ID);
         *///?} else if neoforge {
        /*return ModList.get().isLoaded(YACL_ID);
         *///?}
    }

    public static boolean isDebugUtilsLoaded() {
        //? if fabric {
        return FabricLoader.getInstance().isModLoaded(DEBUG_UTILS_ID);
        //?} else if forge {
        /*return ModList.get().isLoaded(DEBUG_UTILS_ID);
         *///?} else if neoforge {
        /*return ModList.get().isLoaded(DEBUG_UTILS_ID);
         *///?}
    }

    public static void init() {
        FowlPlayActivities.init();
        FowlPlayEntityType.init();
        ChickenVariant.init();
        DuckVariant.init();
        GullVariant.init();
        PigeonVariant.init();
        SparrowVariant.init();
        FowlPlayItems.init();
        FowlPlayMemoryModuleType.init();
        FowlPlayRegistries.init();
        FowlPlayRegistryKeys.init();
        FowlPlaySensorType.init();
        FowlPlaySoundEvents.init();
        FowlPlayTrackedDataHandlerRegistry.init();
    }
}
