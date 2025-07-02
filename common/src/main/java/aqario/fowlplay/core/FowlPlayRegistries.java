package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.util.RegistryBuilder;
import net.minecraft.registry.Registry;

import java.util.function.Supplier;

public class FowlPlayRegistries {
    public static final Supplier<Registry<ChickenVariant>> CHICKEN_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.CHICKEN_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<Registry<DuckVariant>> DUCK_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.DUCK_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<Registry<GullVariant>> GULL_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.GULL_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<Registry<PigeonVariant>> PIGEON_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.PIGEON_VARIANT)
        .sync()
        .buildAndRegister();
    public static final Supplier<Registry<SparrowVariant>> SPARROW_VARIANT = RegistryBuilder
        .create(FowlPlayRegistryKeys.SPARROW_VARIANT)
        .sync()
        .buildAndRegister();

    public static void init() {
    }
}
