package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FowlPlayRegistryKeys {
    public static final ResourceKey<Registry<ChickenVariant>> CHICKEN_VARIANT = of("chicken_variant");
    public static final ResourceKey<Registry<DuckVariant>> DUCK_VARIANT = of("duck_variant");
    public static final ResourceKey<Registry<GooseVariant>> GOOSE_VARIANT = of("goose_variant");
    public static final ResourceKey<Registry<GullVariant>> GULL_VARIANT = of("gull_variant");
    public static final ResourceKey<Registry<PigeonVariant>> PIGEON_VARIANT = of("pigeon_variant");
    public static final ResourceKey<Registry<SparrowVariant>> SPARROW_VARIANT = of("sparrow_variant");

    private static <T> ResourceKey<Registry<T>> of(String id) {
        return ResourceKey.createRegistryKey(FowlPlay.id(id));
    }

    public static void init() {
    }
}
