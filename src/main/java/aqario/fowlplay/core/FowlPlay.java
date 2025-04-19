package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * //TODO RENAME ME!
 * This is the entry point for your mod's common side, called by each modloader specific side.
 */
public class FowlPlay {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

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
