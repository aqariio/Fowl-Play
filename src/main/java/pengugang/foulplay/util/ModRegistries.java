package pengugang.foulplay.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import pengugang.foulplay.entity.InitEntity;
import pengugang.foulplay.entity.PenguinEntity;

public class ModRegistries {
    public static void registerModRegistries() {
        registerAttributes();
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(InitEntity.PENGUIN, PenguinEntity.createPenguinAttributes());
    }
}
