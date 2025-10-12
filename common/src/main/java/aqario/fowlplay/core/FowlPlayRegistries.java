package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.util.RegistryBuilder;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registry;

public class FowlPlayRegistries {
    public static final Registry<ChickenVariant> CHICKEN_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.CHICKEN_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<DuckVariant> DUCK_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.DUCK_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<GooseVariant> GOOSE_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.GOOSE_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<GullVariant> GULL_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.GULL_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<PigeonVariant> PIGEON_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.PIGEON_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<SparrowVariant> SPARROW_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.SPARROW_VARIANT)
        .sync()
        .buildAndRegister();
    public static final DefaultedRegistry<Variant> VARIANT = RegistryBuilder
        .createDefaulted(FowlPlayRegistryKeys.VARIANT, "empty")
        .sync()
        .buildAndRegister();

    public static void init() {
    }
}
