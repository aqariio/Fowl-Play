package aqario.fowlplay.core.tags;

import aqario.fowlplay.core.FowlPlay;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class FowlPlayEntityTypeTags {
    public static final TagKey<EntityType<?>> BIRDS = create("birds");
    public static final TagKey<EntityType<?>> BLUE_JAY_AVOIDS = create("blue_jay_avoids");
    public static final TagKey<EntityType<?>> CARDINAL_AVOIDS = create("cardinal_avoids");
    public static final TagKey<EntityType<?>> CHICKADEE_AVOIDS = create("chickadee_avoids");
    public static final TagKey<EntityType<?>> CROW_AVOIDS = create("crow_avoids");
    public static final TagKey<EntityType<?>> CROW_ATTACK_TARGETS = create("crow_attack_targets");
    public static final TagKey<EntityType<?>> DUCK_AVOIDS = create("duck_avoids");
    public static final TagKey<EntityType<?>> FLIGHTLESS = create("flightless");
    public static final TagKey<EntityType<?>> GOOSE_AVOIDS = create("goose_avoids");
    public static final TagKey<EntityType<?>> GULL_AVOIDS = create("gull_avoids");
    public static final TagKey<EntityType<?>> GULL_BABY_HUNT_TARGETS = create("gull_baby_hunt_targets");
    public static final TagKey<EntityType<?>> GULL_HUNT_TARGETS = create("gull_hunt_targets");
    public static final TagKey<EntityType<?>> HAWK_AVOIDS = create("hawk_avoids");
    public static final TagKey<EntityType<?>> HAWK_BABY_HUNT_TARGETS = create("hawk_baby_hunt_targets");
    public static final TagKey<EntityType<?>> HAWK_HUNT_TARGETS = create("hawk_hunt_targets");
    public static final TagKey<EntityType<?>> PASSERINES = create("passerines");
    public static final TagKey<EntityType<?>> PENGUIN_AVOIDS = create("penguin_avoids");
    public static final TagKey<EntityType<?>> PENGUIN_HUNT_TARGETS = create("penguin_hunt_targets");
    public static final TagKey<EntityType<?>> PIGEON_AVOIDS = create("pigeon_avoids");
    public static final TagKey<EntityType<?>> RAVEN_AVOIDS = create("raven_avoids");
    public static final TagKey<EntityType<?>> RAVEN_ATTACK_TARGETS = create("raven_attack_targets");
    public static final TagKey<EntityType<?>> RAVEN_BABY_HUNT_TARGETS = create("raven_baby_hunt_targets");
    public static final TagKey<EntityType<?>> RAVEN_HUNT_TARGETS = create("raven_hunt_targets");
    public static final TagKey<EntityType<?>> ROBIN_AVOIDS = create("robin_avoids");
    public static final TagKey<EntityType<?>> SEABIRDS = create("seabirds");
    public static final TagKey<EntityType<?>> SPARROW_AVOIDS = create("sparrow_avoids");
    public static final TagKey<EntityType<?>> WATERBIRDS = create("waterbirds");
    public static final TagKey<EntityType<?>> WATERFOWL = create("waterfowl");

    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id));
    }
}
