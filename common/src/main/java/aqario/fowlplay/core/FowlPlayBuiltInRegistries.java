package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.util.RegistryBuilder;
import aqario.fowlplay.core.platform.CommonRegistry;

import java.util.function.Supplier;

public class FowlPlayBuiltInRegistries {
    public static /*final*/ Supplier<CommonRegistry<ChickenVariant>> CHICKEN_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.CHICKEN_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<DuckVariant>> DUCK_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.DUCK_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<GooseVariant>> GOOSE_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.GOOSE_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<GullVariant>> GULL_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.GULL_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<PigeonVariant>> PIGEON_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.PIGEON_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<SparrowVariant>> SPARROW_VARIANT = RegistryBuilder
        .create(FowlPlayRegistries.SPARROW_VARIANT)
        .sync()
        .buildAndRegister();

    public static void init() {
    }
}
