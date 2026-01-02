package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.bird.ChickenVariant;
import aqario.fowlplay.common.entity.bird.duck.DuckVariant;
import aqario.fowlplay.common.entity.bird.goose.GooseVariant;
import aqario.fowlplay.common.entity.bird.gull.GullVariant;
import aqario.fowlplay.common.entity.bird.pigeon.PigeonVariant;
import aqario.fowlplay.common.entity.bird.sparrow.SparrowVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FowlPlayRegistries {
    public static final ResourceKey<Registry<ChickenVariant>> CHICKEN_VARIANT = createRegistryKey("chicken_variant");
    public static final ResourceKey<Registry<DuckVariant>> DUCK_VARIANT = createRegistryKey("duck_variant");
    public static final ResourceKey<Registry<GooseVariant>> GOOSE_VARIANT = createRegistryKey("goose_variant");
    public static final ResourceKey<Registry<GullVariant>> GULL_VARIANT = createRegistryKey("gull_variant");
    public static final ResourceKey<Registry<PigeonVariant>> PIGEON_VARIANT = createRegistryKey("pigeon_variant");
    public static final ResourceKey<Registry<SparrowVariant>> SPARROW_VARIANT = createRegistryKey("sparrow_variant");
    public static final ResourceKey<Registry<Variant>> VARIANT = createRegistryKey("variant");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String id) {
        return ResourceKey.createRegistryKey(FowlPlay.id(id));
    }

    public static void init() {
    }
}
