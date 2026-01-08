package aqario.fowlplay.core.tags;

import aqario.fowlplay.common.entity.variant.DuckVariant;
import aqario.fowlplay.common.entity.variant.GooseVariant;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import net.minecraft.tags.TagKey;

public class FowlPlayVariantTags {
    public static class Duck {
        public static final TagKey<DuckVariant> NATURAL = create("natural");
        public static final TagKey<DuckVariant> DOMESTIC = create("domestic");

        private static TagKey<DuckVariant> create(String id) {
            return TagKey.create(FowlPlayRegistries.DUCK_VARIANT, FowlPlay.id(id));
        }
    }

    public static class Goose {
        public static final TagKey<GooseVariant> NATURAL = create("natural");
        public static final TagKey<GooseVariant> DOMESTIC = create("domestic");

        private static TagKey<GooseVariant> create(String id) {
            return TagKey.create(FowlPlayRegistries.GOOSE_VARIANT, FowlPlay.id(id));
        }
    }
}
