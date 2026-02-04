package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.common.util.RegistryBuilder;
import net.minecraft.core.Registry;

public class FowlPlayBuiltInRegistries {
    public static final Registry<ChickenVariant> CHICKEN_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.CHICKEN_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<DuckVariant> DUCK_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.DUCK_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<GooseVariant> GOOSE_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.GOOSE_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<GullVariant> GULL_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.GULL_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<PigeonVariant> PIGEON_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.PIGEON_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<SparrowVariant> SPARROW_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.SPARROW_VARIANT)
        .sync()
        .buildAndRegister();

    public static void init() {
    }
}
