package aqario.fowlplay.core.tags;

import aqario.fowlplay.common.entity.GooseVariant;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import net.minecraft.tags.TagKey;

public class FowlPlayVariantTags {
    public static class Goose {
        public static final TagKey<GooseVariant> NATURAL = create("natural");
        public static final TagKey<GooseVariant> DOMESTIC = create("domestic");

        private static TagKey<GooseVariant> create(String id) {
            return TagKey.create(FowlPlayRegistries.GOOSE_VARIANT, FowlPlay.id(id));
        }
    }
}
