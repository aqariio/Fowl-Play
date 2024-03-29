package aqario.fowlplay.common.tags;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import aqario.fowlplay.common.FowlPlay;

public final class FowlPlayEntityTypeTags {
    public static final TagKey<EntityType<?>> BIRDS = register("birds");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(FowlPlay.ID, id));
    }
}

