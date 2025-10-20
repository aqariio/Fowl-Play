package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.util.RegistryBuilder;
import aqario.fowlplay.core.platform.CommonRegistry;

import java.util.function.Supplier;

public class FowlPlayRegistries {
    public static /*final*/ Supplier<CommonRegistry<ChickenVariant>> CHICKEN_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.CHICKEN_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<DuckVariant>> DUCK_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.DUCK_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<GooseVariant>> GOOSE_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.GOOSE_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<GullVariant>> GULL_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.GULL_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<PigeonVariant>> PIGEON_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.PIGEON_VARIANT)
        .sync()
        .buildAndRegister();
    public static /*final*/ Supplier<CommonRegistry<SparrowVariant>> SPARROW_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.SPARROW_VARIANT)
        .sync()
        .buildAndRegister();

    public static void init() {
    }
}
