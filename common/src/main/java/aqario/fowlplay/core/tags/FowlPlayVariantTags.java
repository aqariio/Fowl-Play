package aqario.fowlplay.core.tags;

import aqario.fowlplay.common.entity.GooseVariant;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class FowlPlayVariantTags {
    public static class Goose {
        public static final TagKey<GooseVariant> NATURAL = create("natural");
        public static final TagKey<GooseVariant> DOMESTIC = create("domestic");

        private static TagKey<GooseVariant> create(String id) {
            return TagKey.of(FowlPlayRegistryKeys.GOOSE_VARIANT, Identifier.of(FowlPlay.ID, id));
        }
    }
}
