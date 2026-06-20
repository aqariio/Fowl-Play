package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.common.registry.CommonRegistry;
import aqario.fowlplay.common.registry.RegistryBuilder;

import java.util.function.Supplier;

public class FPBuiltInRegistries {
    public static final Supplier<CommonRegistry<ChickenVariant>> CHICKEN_VARIANT = RegistryBuilder
        .create(FPRegistries.CHICKEN_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<CommonRegistry<DuckVariant>> DUCK_VARIANT = RegistryBuilder
        .create(FPRegistries.DUCK_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<CommonRegistry<GooseVariant>> GOOSE_VARIANT = RegistryBuilder
        .create(FPRegistries.GOOSE_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<CommonRegistry<GullVariant>> GULL_VARIANT = RegistryBuilder
        .create(FPRegistries.GULL_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<CommonRegistry<PigeonVariant>> PIGEON_VARIANT = RegistryBuilder
        .create(FPRegistries.PIGEON_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<CommonRegistry<SparrowVariant>> SPARROW_VARIANT = RegistryBuilder
        .create(FPRegistries.SPARROW_VARIANT)
        .sync()
        .buildAndRegister();

    public static void init() {
    }
}
