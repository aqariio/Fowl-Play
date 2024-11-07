package aqario.fowlplay.neoforge.init;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class FowlPlaySoundEvents {
    public static final SoundEvent ENTITY_BLUE_JAY_CALL = register("entity.blue_jay.call");
    public static final SoundEvent ENTITY_BLUE_JAY_HURT = register("entity.blue_jay.hurt");

    public static final SoundEvent ENTITY_CARDINAL_CALL = register("entity.cardinal.call");
    public static final SoundEvent ENTITY_CARDINAL_SONG = register("entity.cardinal.song");
    public static final SoundEvent ENTITY_CARDINAL_HURT = register("entity.cardinal.hurt");

    public static final SoundEvent ENTITY_GULL_CALL = register("entity.gull.call");
    public static final SoundEvent ENTITY_GULL_LONG_CALL = register("entity.gull.long_call");
    public static final SoundEvent ENTITY_GULL_HURT = register("entity.gull.hurt");

    public static final SoundEvent ENTITY_PENGUIN_CALL = register("entity.penguin.call");
    public static final SoundEvent ENTITY_PENGUIN_BABY_CALL = register("entity.penguin_baby.call");
    public static final SoundEvent ENTITY_PENGUIN_SWIM = register("entity.penguin.swim");
    public static final SoundEvent ENTITY_PENGUIN_HURT = register("entity.penguin.hurt");
    public static final SoundEvent ENTITY_PENGUIN_DEATH = register("entity.penguin.death");

    public static final SoundEvent ENTITY_PIGEON_CALL = register("entity.pigeon.call");
    public static final SoundEvent ENTITY_PIGEON_SONG = register("entity.pigeon.song");

    public static final SoundEvent ENTITY_ROBIN_CALL = register("entity.robin.call");
    public static final SoundEvent ENTITY_ROBIN_SONG = register("entity.robin.song");
    public static final SoundEvent ENTITY_ROBIN_HURT = register("entity.robin.hurt");

    public static final SoundEvent ENTITY_SPARROW_CALL = register("entity.sparrow.call");
    public static final SoundEvent ENTITY_SPARROW_SONG = register("entity.sparrow.song");
    public static final SoundEvent ENTITY_SPARROW_HURT = register("entity.sparrow.hurt");

    private static SoundEvent register(String id) {
        Identifier identifier = Identifier.of(FowlPlay.ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init() {
    }
}
