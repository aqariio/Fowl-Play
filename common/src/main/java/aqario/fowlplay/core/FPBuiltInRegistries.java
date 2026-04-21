package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.common.registry.RegistryBuilder;
import net.minecraft.core.Registry;

public final class FPBuiltInRegistries {
    public static final Registry<ChickenVariant> CHICKEN_VARIANT = RegistryBuilder
        .create(FPRegistries.CHICKEN_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<DuckVariant> DUCK_VARIANT = RegistryBuilder
        .create(FPRegistries.DUCK_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<GooseVariant> GOOSE_VARIANT = RegistryBuilder
        .create(FPRegistries.GOOSE_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<GullVariant> GULL_VARIANT = RegistryBuilder
        .create(FPRegistries.GULL_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<PigeonVariant> PIGEON_VARIANT = RegistryBuilder
        .create(FPRegistries.PIGEON_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Registry<SparrowVariant> SPARROW_VARIANT = RegistryBuilder
        .create(FPRegistries.SPARROW_VARIANT)
        .sync()
        .buildAndRegister();

    public static void init() {
    }
}
