package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.registry.CommonRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public final class FPSoundEvents {
    public static final CommonRegister<SoundEvent> REGISTRAR = CommonRegister.create(
        BuiltInRegistries.SOUND_EVENT,
        FowlPlay.ID
    );

    public static final Supplier<SoundEvent> BIRD_EAT = register("entity.bird.eat");
    public static final Supplier<SoundEvent> BIRD_FLAP = register("entity.bird.flap");

    public static final Supplier<SoundEvent> BLUE_JAY_CALL = register(
        "entity.blue_jay.call",
        FowlPlayConfig.blueJayCallRange
    );
    public static final Supplier<SoundEvent> BLUE_JAY_HURT = register(
        "entity.blue_jay.hurt",
        FowlPlayConfig.blueJayCallRange
    );

    public static final Supplier<SoundEvent> CARDINAL_CALL = register(
        "entity.cardinal.call",
        FowlPlayConfig.cardinalCallRange
    );
    public static final Supplier<SoundEvent> CARDINAL_SONG = register(
        "entity.cardinal.song",
        FowlPlayConfig.cardinalSongRange
    );
    public static final Supplier<SoundEvent> CARDINAL_HURT = register(
        "entity.cardinal.hurt",
        FowlPlayConfig.cardinalCallRange
    );

    public static final Supplier<SoundEvent> CHICKADEE_CALL = register(
        "entity.chickadee.call",
        FowlPlayConfig.chickadeeCallRange
    );
    public static final Supplier<SoundEvent> CHICKADEE_SONG = register(
        "entity.chickadee.song",
        FowlPlayConfig.chickadeeSongRange
    );
    public static final Supplier<SoundEvent> CHICKADEE_HURT = register(
        "entity.chickadee.hurt",
        FowlPlayConfig.chickadeeCallRange
    );

    public static final Supplier<SoundEvent> CROW_CALL = register(
        "entity.crow.call",
        FowlPlayConfig.crowCallRange
    );
    public static final Supplier<SoundEvent> CROW_HURT = register(
        "entity.crow.hurt",
        FowlPlayConfig.crowCallRange
    );

    public static final Supplier<SoundEvent> DUCK_CALL = register(
        "entity.duck.call",
        FowlPlayConfig.duckCallRange
    );
    public static final Supplier<SoundEvent> DUCK_HURT = register(
        "entity.duck.hurt",
        FowlPlayConfig.duckCallRange
    );

    public static final Supplier<SoundEvent> CANADA_GOOSE_CALL = register(
        "entity.canada_goose.call",
        FowlPlayConfig.gooseCallRange
    );
    public static final Supplier<SoundEvent> GREYLAG_GOOSE_CALL = register(
        "entity.greylag_goose.call",
        FowlPlayConfig.gooseCallRange
    );
    public static final Supplier<SoundEvent> SWAN_GOOSE_CALL = register(
        "entity.swan_goose.call",
        FowlPlayConfig.gooseCallRange
    );
    public static final Supplier<SoundEvent> CANADA_GOOSE_HURT = register(
        "entity.canada_goose.hurt",
        FowlPlayConfig.gooseCallRange
    );
    public static final Supplier<SoundEvent> GREYLAG_GOOSE_HURT = register(
        "entity.greylag_goose.hurt",
        FowlPlayConfig.gooseCallRange
    );
    public static final Supplier<SoundEvent> SWAN_GOOSE_HURT = register(
        "entity.swan_goose.hurt",
        FowlPlayConfig.gooseCallRange
    );

    public static final Supplier<SoundEvent> GULL_CALL = register(
        "entity.gull.call",
        FowlPlayConfig.gullCallRange
    );
    public static final Supplier<SoundEvent> GULL_LONG_CALL = register(
        "entity.gull.long_call",
        FowlPlayConfig.gullSongRange
    );
    public static final Supplier<SoundEvent> GULL_HURT = register(
        "entity.gull.hurt",
        FowlPlayConfig.gullCallRange
    );

    public static final Supplier<SoundEvent> HAWK_CALL = register(
        "entity.hawk.call",
        FowlPlayConfig.hawkCallRange
    );
    public static final Supplier<SoundEvent> HAWK_HURT = register(
        "entity.hawk.hurt",
        FowlPlayConfig.hawkCallRange
    );

    public static final Supplier<SoundEvent> PENGUIN_CALL = register(
        "entity.penguin.call",
        FowlPlayConfig.penguinCallRange
    );
    public static final Supplier<SoundEvent> PENGUIN_BABY_CALL = register(
        "entity.penguin.baby.call",
        FowlPlayConfig.penguinCallRange
    );
    public static final Supplier<SoundEvent> PENGUIN_SWIM = register(
        "entity.penguin.swim"
    );
    public static final Supplier<SoundEvent> PENGUIN_HURT = register(
        "entity.penguin.hurt",
        FowlPlayConfig.penguinCallRange
    );

    public static final Supplier<SoundEvent> PIGEON_CALL = register(
        "entity.pigeon.call",
        FowlPlayConfig.pigeonCallRange
    );
    public static final Supplier<SoundEvent> PIGEON_SONG = register(
        "entity.pigeon.song",
        FowlPlayConfig.pigeonSongRange
    );
    public static final Supplier<SoundEvent> PIGEON_HURT = register(
        "entity.pigeon.hurt",
        FowlPlayConfig.pigeonCallRange
    );

    public static final Supplier<SoundEvent> RAVEN_CALL = register(
        "entity.raven.call",
        FowlPlayConfig.ravenCallRange
    );
    public static final Supplier<SoundEvent> RAVEN_HURT = register(
        "entity.raven.hurt",
        FowlPlayConfig.ravenCallRange
    );

    public static final Supplier<SoundEvent> ROBIN_CALL = register(
        "entity.robin.call",
        FowlPlayConfig.robinCallRange
    );
    public static final Supplier<SoundEvent> ROBIN_SONG = register(
        "entity.robin.song",
        FowlPlayConfig.robinSongRange
    );
    public static final Supplier<SoundEvent> ROBIN_HURT = register(
        "entity.robin.hurt",
        FowlPlayConfig.robinCallRange
    );

    public static final Supplier<SoundEvent> SPARROW_CALL = register(
        "entity.sparrow.call",
        FowlPlayConfig.sparrowCallRange
    );
    public static final Supplier<SoundEvent> SPARROW_SONG = register(
        "entity.sparrow.song",
        FowlPlayConfig.sparrowSongRange
    );
    public static final Supplier<SoundEvent> SPARROW_HURT = register(
        "entity.sparrow.hurt",
        FowlPlayConfig.sparrowCallRange
    );

    public static final Supplier<SoundEvent> VULTURE_CALL = register(
        "entity.vulture.call",
        FowlPlayConfig.vultureCallRange
    );
    public static final Supplier<SoundEvent> VULTURE_HURT = register(
        "entity.vulture.hurt",
        FowlPlayConfig.vultureCallRange
    );

    private static Supplier<SoundEvent> register(String id) {
        return register(id, 16);
    }

    private static Supplier<SoundEvent> register(String id, int range) {
        return REGISTRAR.register(id, () -> SoundEvent.createFixedRangeEvent(FowlPlay.id(id), range + 16));
    }
}
