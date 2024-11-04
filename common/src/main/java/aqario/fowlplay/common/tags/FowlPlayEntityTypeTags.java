package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class FowlPlayEntityTypeTags {
    public static final TagKey<EntityType<?>> BIRDS = create("birds");
    public static final TagKey<EntityType<?>> GULL_BABY_HUNT_TARGETS = create("gull_baby_hunt_targets");
    public static final TagKey<EntityType<?>> GULL_HUNT_TARGETS = create("gull_hunt_targets");
    public static final TagKey<EntityType<?>> PASSERINES = create("passerines");
    public static final TagKey<EntityType<?>> PENGUIN_HUNT_TARGETS = create("penguin_hunt_targets");

    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id));
    }
}
