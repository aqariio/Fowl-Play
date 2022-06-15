package pengugang.fowlplay.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import pengugang.fowlplay.entity.InitEntity;
import pengugang.fowlplay.entity.PenguinEntity;
import pengugang.fowlplay.entity.SeagullEntity;

public class ModRegistries {
    public static void registerModRegistries() {
        registerAttributes();
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(InitEntity.PENGUIN, PenguinEntity.createPenguinAttributes());
        FabricDefaultAttributeRegistry.register(InitEntity.SEAGULL, SeagullEntity.createSeagullAttributes());
    }
}
