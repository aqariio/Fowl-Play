package aqario.fowlplay.common.config;

import aqario.fowlplay.core.FowlPlay;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class YACLIntegration {
    protected static final ConfigClassHandler<FowlPlayConfig> HANDLED_CONFIG = ConfigClassHandler.createBuilder(FowlPlayConfig.class)
        .id(Identifier.of(FowlPlay.ID, "config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(FabricLoader.getInstance().getConfigDir().resolve(FowlPlay.ID + ".json5"))
            .setJson5(true)
            .build())
        .build();

    protected static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.create(HANDLED_CONFIG, (defaults, config, builder) -> builder
                .title(Text.translatable("config.title"))
                .category(ConfigCategory.createBuilder()
                    .name(Text.translatable("config.visual"))
                    .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("config.visual.customChickenModel"))
                        .description(OptionDescription.of(Text.translatable("config.info.restart").append("\n\n").append(Text.translatable("config.visual.customChickenModel.desc"))))
                        .binding(true, () -> config.customChickenModel, val -> config.customChickenModel = val)
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    .build()
                )
                .category(ConfigCategory.createBuilder()
                    .name(Text.translatable("config.audio"))
                    .group(createSoundGroup(
                        "entity.fowlplay.blue_jay",
                        defaults.blueJayCallVolume,
                        () -> config.blueJayCallVolume,
                        val -> config.blueJayCallVolume = val,
                        0,
                        null,
                        null
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.cardinal",
                        defaults.cardinalCallVolume,
                        () -> config.cardinalCallVolume,
                        val -> config.cardinalCallVolume = val,
                        defaults.cardinalSongVolume,
                        () -> config.cardinalSongVolume,
                        val -> config.cardinalSongVolume = val
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.chickadee",
                        defaults.chickadeeCallVolume,
                        () -> config.chickadeeCallVolume,
                        val -> config.chickadeeCallVolume = val,
                        defaults.chickadeeSongVolume,
                        () -> config.chickadeeSongVolume,
                        val -> config.chickadeeSongVolume = val
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.crow",
                        defaults.crowCallVolume,
                        () -> config.crowCallVolume,
                        val -> config.crowCallVolume = val,
                        0,
                        null,
                        null
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.duck",
                        defaults.duckCallVolume,
                        () -> config.duckCallVolume,
                        val -> config.duckCallVolume = val,
                        0,
                        null,
                        null
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.gull",
                        defaults.gullCallVolume,
                        () -> config.gullCallVolume,
                        val -> config.gullCallVolume = val,
                        defaults.gullSongVolume,
                        () -> config.gullSongVolume,
                        val -> config.gullSongVolume = val
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.hawk",
                        defaults.hawkCallVolume,
                        () -> config.hawkCallVolume,
                        val -> config.hawkCallVolume = val,
                        0,
                        null,
                        null
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.penguin",
                        defaults.penguinCallVolume,
                        () -> config.penguinCallVolume,
                        val -> config.penguinCallVolume = val,
                        0,
                        null,
                        null
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.pigeon",
                        defaults.pigeonCallVolume,
                        () -> config.pigeonCallVolume,
                        val -> config.pigeonCallVolume = val,
                        defaults.pigeonSongVolume,
                        () -> config.pigeonSongVolume,
                        val -> config.pigeonSongVolume = val
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.raven",
                        defaults.ravenCallVolume,
                        () -> config.ravenCallVolume,
                        val -> config.ravenCallVolume = val,
                        0,
                        null,
                        null
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.robin",
                        defaults.robinCallVolume,
                        () -> config.robinCallVolume,
                        val -> config.robinCallVolume = val,
                        defaults.robinSongVolume,
                        () -> config.robinSongVolume,
                        val -> config.robinSongVolume = val
                    ))
                    .group(createSoundGroup(
                        "entity.fowlplay.sparrow",
                        defaults.sparrowCallVolume,
                        () -> config.sparrowCallVolume,
                        val -> config.sparrowCallVolume = val,
                        defaults.sparrowSongVolume,
                        () -> config.sparrowSongVolume,
                        val -> config.sparrowSongVolume = val
                    ))
                    .build()
                )
                .category(ConfigCategory.createBuilder()
                    .name(Text.translatable("config.spawning"))
                    .group(createSpawningGroup(
                        "entity.fowlplay.blue_jay",
                        defaults.blueJaySpawnWeight,
                        () -> config.blueJaySpawnWeight,
                        val -> config.blueJaySpawnWeight = val,
                        defaults.blueJayMinGroupSize,
                        () -> config.blueJayMinGroupSize,
                        val -> config.blueJayMinGroupSize = val,
                        defaults.blueJayMaxGroupSize,
                        () -> config.blueJayMaxGroupSize,
                        val -> config.blueJayMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.cardinal",
                        defaults.cardinalSpawnWeight,
                        () -> config.cardinalSpawnWeight,
                        val -> config.cardinalSpawnWeight = val,
                        defaults.cardinalMinGroupSize,
                        () -> config.cardinalMinGroupSize,
                        val -> config.cardinalMinGroupSize = val,
                        defaults.cardinalMaxGroupSize,
                        () -> config.cardinalMaxGroupSize,
                        val -> config.cardinalMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.chickadee",
                        defaults.chickadeeSpawnWeight,
                        () -> config.chickadeeSpawnWeight,
                        val -> config.chickadeeSpawnWeight = val,
                        defaults.chickadeeMinGroupSize,
                        () -> config.chickadeeMinGroupSize,
                        val -> config.chickadeeMinGroupSize = val,
                        defaults.chickadeeMaxGroupSize,
                        () -> config.chickadeeMaxGroupSize,
                        val -> config.chickadeeMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.crow",
                        defaults.crowSpawnWeight,
                        () -> config.crowSpawnWeight,
                        val -> config.crowSpawnWeight = val,
                        defaults.crowMinGroupSize,
                        () -> config.crowMinGroupSize,
                        val -> config.crowMinGroupSize = val,
                        defaults.crowMaxGroupSize,
                        () -> config.crowMaxGroupSize,
                        val -> config.crowMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.duck",
                        defaults.duckSpawnWeight,
                        () -> config.duckSpawnWeight,
                        val -> config.duckSpawnWeight = val,
                        defaults.duckMinGroupSize,
                        () -> config.duckMinGroupSize,
                        val -> config.duckMinGroupSize = val,
                        defaults.duckMaxGroupSize,
                        () -> config.duckMaxGroupSize,
                        val -> config.duckMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.gull",
                        defaults.gullSpawnWeight,
                        () -> config.gullSpawnWeight,
                        val -> config.gullSpawnWeight = val,
                        defaults.gullMinGroupSize,
                        () -> config.gullMinGroupSize,
                        val -> config.gullMinGroupSize = val,
                        defaults.gullMaxGroupSize,
                        () -> config.gullMaxGroupSize,
                        val -> config.gullMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.hawk",
                        defaults.hawkSpawnWeight,
                        () -> config.hawkSpawnWeight,
                        val -> config.hawkSpawnWeight = val,
                        defaults.hawkMinGroupSize,
                        () -> config.hawkMinGroupSize,
                        val -> config.hawkMinGroupSize = val,
                        defaults.hawkMaxGroupSize,
                        () -> config.hawkMaxGroupSize,
                        val -> config.hawkMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.penguin",
                        defaults.penguinSpawnWeight,
                        () -> config.penguinSpawnWeight,
                        val -> config.penguinSpawnWeight = val,
                        defaults.penguinMinGroupSize,
                        () -> config.penguinMinGroupSize,
                        val -> config.penguinMinGroupSize = val,
                        defaults.penguinMaxGroupSize,
                        () -> config.penguinMaxGroupSize,
                        val -> config.penguinMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.pigeon",
                        defaults.pigeonSpawnWeight,
                        () -> config.pigeonSpawnWeight,
                        val -> config.pigeonSpawnWeight = val,
                        defaults.pigeonMinGroupSize,
                        () -> config.pigeonMinGroupSize,
                        val -> config.pigeonMinGroupSize = val,
                        defaults.pigeonMaxGroupSize,
                        () -> config.pigeonMaxGroupSize,
                        val -> config.pigeonMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.raven",
                        defaults.ravenSpawnWeight,
                        () -> config.ravenSpawnWeight,
                        val -> config.ravenSpawnWeight = val,
                        defaults.ravenMinGroupSize,
                        () -> config.ravenMinGroupSize,
                        val -> config.ravenMinGroupSize = val,
                        defaults.ravenMaxGroupSize,
                        () -> config.ravenMaxGroupSize,
                        val -> config.ravenMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.robin",
                        defaults.robinSpawnWeight,
                        () -> config.robinSpawnWeight,
                        val -> config.robinSpawnWeight = val,
                        defaults.robinMinGroupSize,
                        () -> config.robinMinGroupSize,
                        val -> config.robinMinGroupSize = val,
                        defaults.robinMaxGroupSize,
                        () -> config.robinMaxGroupSize,
                        val -> config.robinMaxGroupSize = val
                    ))
                    .group(createSpawningGroup(
                        "entity.fowlplay.sparrow",
                        defaults.sparrowSpawnWeight,
                        () -> config.sparrowSpawnWeight,
                        val -> config.sparrowSpawnWeight = val,
                        defaults.sparrowMinGroupSize,
                        () -> config.sparrowMinGroupSize,
                        val -> config.sparrowMinGroupSize = val,
                        defaults.sparrowMaxGroupSize,
                        () -> config.sparrowMaxGroupSize,
                        val -> config.sparrowMaxGroupSize = val
                    ))
                    .build()
                )
                .save(FowlPlayConfig::save)
            )
            .generateScreen(parent);
    }

    private static OptionGroup createSoundGroup(
        String entity,
        int callRange,
        Supplier<Integer> getCallVolume,
        Consumer<Integer> setCallVolume,
        int songRange,
        Supplier<Integer> getSongVolume,
        Consumer<Integer> setSongVolume
    ) {
        OptionGroup.Builder builder = OptionGroup.createBuilder()
            .name(Text.translatable(entity));

        if (getCallVolume != null && setCallVolume != null) {
            builder.option(createSoundOption(
                entity,
                "config.audio.generic.call",
                "config.audio.generic.call.desc",
                callRange,
                getCallVolume,
                setCallVolume
            ));
        }

        if (getSongVolume != null && setSongVolume != null) {
            builder.option(createSoundOption(
                entity,
                "config.audio.generic.song",
                "config.audio.generic.song.desc",
                songRange,
                getSongVolume,
                setSongVolume
            ));
        }

        return builder.build();
    }

    private static Option<Integer> createSoundOption(String entity, String name, String description, int defaultValue, Supplier<Integer> get, Consumer<Integer> set) {
        return Option.<Integer>createBuilder()
            .name(Text.translatable(name))
            .description(OptionDescription.of(Text.translatable(description, Text.translatable(entity))))
            .binding(defaultValue, get, set)
            .controller(option -> IntegerSliderControllerBuilder.create(option)
                .range(0, 20)
                .step(1)
            )
            .build();
    }

    private static OptionGroup createSpawningGroup(
        String entity,
        int spawnWeight,
        Supplier<Integer> getSpawnWeight,
        Consumer<Integer> setSpawnWeight,
        int minGroupSize,
        Supplier<Integer> getMinGroupSize,
        Consumer<Integer> setMinGroupSize,
        int maxGroupSize,
        Supplier<Integer> getMaxGroupSize,
        Consumer<Integer> setMaxGroupSize
    ) {
        return OptionGroup.createBuilder()
            .name(Text.translatable(entity))
            .option(createSpawningOption(
                entity,
                "config.spawning.generic.spawnWeight",
                "config.spawning.generic.spawnWeight.desc",
                spawnWeight,
                getSpawnWeight,
                setSpawnWeight
            ))
            .option(createSpawningOption(
                entity,
                "config.spawning.generic.minGroupSize",
                "config.spawning.generic.minGroupSize.desc",
                minGroupSize,
                getMinGroupSize,
                setMinGroupSize
            ))
            .option(createSpawningOption(
                entity,
                "config.spawning.generic.maxGroupSize",
                "config.spawning.generic.maxGroupSize.desc",
                maxGroupSize,
                getMaxGroupSize,
                setMaxGroupSize
            ))
            .build();
    }

    private static Option<Integer> createSpawningOption(String entity, String name, String description, int defaultValue, Supplier<Integer> get, Consumer<Integer> set) {
        return Option.<Integer>createBuilder()
            .name(Text.translatable(name))
            .description(OptionDescription.of(Text.translatable("config.info.restart").append("\n\n").append(Text.translatable(description, Text.translatable(entity)))))
            .binding(defaultValue, get, set)
            .controller(option -> IntegerSliderControllerBuilder.create(option)
                .range(0, 100)
                .step(1)
            )
            .build();
    }
}
