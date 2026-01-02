package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.bird.ChickenVariant;
import aqario.fowlplay.common.entity.bird.duck.DuckVariant;
import aqario.fowlplay.common.entity.bird.goose.GooseVariant;
import aqario.fowlplay.common.entity.bird.gull.GullVariant;
import aqario.fowlplay.common.entity.bird.pigeon.PigeonVariant;
import aqario.fowlplay.common.entity.bird.sparrow.SparrowVariant;
import aqario.fowlplay.common.util.RegistryBuilder;
import net.minecraft.core.DefaultedRegistry;
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
    public static final DefaultedRegistry<Variant> VARIANT = RegistryBuilder
        .createDefaulted(FowlPlayRegistries.VARIANT, "empty")
        .sync()
        .buildAndRegister();

    public static void init() {
    }
}
