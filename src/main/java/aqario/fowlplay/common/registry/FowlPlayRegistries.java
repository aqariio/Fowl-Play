package aqario.fowlplay.common.registry;

import aqario.fowlplay.common.entity.*;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

public class FowlPlayRegistries {
    public static final Registry<ChickenVariant> CHICKEN_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.CHICKEN_VARIANT)
        .attribute(RegistryAttribute.SYNCED)
        .buildAndRegister();
    public static final Registry<DuckVariant> DUCK_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.DUCK_VARIANT)
        .attribute(RegistryAttribute.SYNCED)
        .buildAndRegister();
    public static final Registry<GullVariant> GULL_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.GULL_VARIANT)
        .attribute(RegistryAttribute.SYNCED)
        .buildAndRegister();
    public static final Registry<PigeonVariant> PIGEON_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.PIGEON_VARIANT)
        .attribute(RegistryAttribute.SYNCED)
        .buildAndRegister();
    public static final Registry<SparrowVariant> SPARROW_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.SPARROW_VARIANT)
        .attribute(RegistryAttribute.SYNCED)
        .buildAndRegister();

    public static void init() {
    }
}
