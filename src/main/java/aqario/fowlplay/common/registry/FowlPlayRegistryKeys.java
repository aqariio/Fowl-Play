package aqario.fowlplay.common.registry;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.DuckVariant;
import aqario.fowlplay.common.entity.GullVariant;
import aqario.fowlplay.common.entity.PigeonVariant;
import aqario.fowlplay.common.entity.SparrowVariant;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class FowlPlayRegistryKeys {
    public static final RegistryKey<Registry<DuckVariant>> DUCK_VARIANT = of("duck_variant");
    public static final RegistryKey<Registry<GullVariant>> GULL_VARIANT = of("gull_variant");
    public static final RegistryKey<Registry<PigeonVariant>> PIGEON_VARIANT = of("pigeon_variant");
    public static final RegistryKey<Registry<SparrowVariant>> SPARROW_VARIANT = of("sparrow_variant");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(Identifier.of(FowlPlay.ID, id));
    }

    public static void init() {
    }
}
