package aqario.fowlplay.common.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class YACLIntegration {
    protected static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
            .title(Text.translatable("config.title"))
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("config.visual"))
                .option(Option.<Boolean>createBuilder()
                    .name(Text.translatable("config.visual.customChickenModel"))
                    .description(OptionDescription.of(Text.translatable("config.info.restart").append("\n\n").append(Text.translatable("config.visual.customChickenModel.desc"))))
                    .binding(true, () -> FowlPlayConfig.customChickenModel, val -> FowlPlayConfig.customChickenModel = val)
                    .controller(BooleanControllerBuilder::create)
                    .build()
                )
                .build()
            )
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("config.audio"))
                .group(createSoundGroup(
                    "entity.fowlplay.blue_jay",
                    10,
                    () -> FowlPlayConfig.blueJayCallVolume,
                    val -> FowlPlayConfig.blueJayCallVolume = val,
                    0,
                    null,
                    null
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.cardinal",
                    2,
                    () -> FowlPlayConfig.cardinalCallVolume,
                    val -> FowlPlayConfig.cardinalCallVolume = val,
                    6,
                    () -> FowlPlayConfig.cardinalSongVolume,
                    val -> FowlPlayConfig.cardinalSongVolume = val
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.chickadee",
                    6,
                    () -> FowlPlayConfig.chickadeeCallVolume,
                    val -> FowlPlayConfig.chickadeeCallVolume = val,
                    6,
                    () -> FowlPlayConfig.chickadeeSongVolume,
                    val -> FowlPlayConfig.chickadeeSongVolume = val
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.duck",
                    2,
                    () -> FowlPlayConfig.duckCallVolume,
                    val -> FowlPlayConfig.duckCallVolume = val,
                    0,
                    null,
                    null
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.gull",
                    6,
                    () -> FowlPlayConfig.gullCallVolume,
                    val -> FowlPlayConfig.gullCallVolume = val,
                    7,
                    () -> FowlPlayConfig.gullSongVolume,
                    val -> FowlPlayConfig.gullSongVolume = val
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.penguin",
                    4,
                    () -> FowlPlayConfig.penguinCallVolume,
                    val -> FowlPlayConfig.penguinCallVolume = val,
                    0,
                    null,
                    null
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.pigeon",
                    1,
                    () -> FowlPlayConfig.pigeonCallVolume,
                    val -> FowlPlayConfig.pigeonCallVolume = val,
                    6,
                    () -> FowlPlayConfig.pigeonSongVolume,
                    val -> FowlPlayConfig.pigeonSongVolume = val
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.raven",
                    10,
                    () -> FowlPlayConfig.ravenCallVolume,
                    val -> FowlPlayConfig.ravenCallVolume = val,
                    0,
                    null,
                    null
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.robin",
                    2,
                    () -> FowlPlayConfig.robinCallVolume,
                    val -> FowlPlayConfig.robinCallVolume = val,
                    6,
                    () -> FowlPlayConfig.robinSongVolume,
                    val -> FowlPlayConfig.robinSongVolume = val
                ))
                .group(createSoundGroup(
                    "entity.fowlplay.sparrow",
                    2,
                    () -> FowlPlayConfig.sparrowCallVolume,
                    val -> FowlPlayConfig.sparrowCallVolume = val,
                    6,
                    () -> FowlPlayConfig.sparrowSongVolume,
                    val -> FowlPlayConfig.sparrowSongVolume = val
                ))
                .build()
            )
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("config.spawning"))
                .group(createSpawningGroup(
                    "entity.fowlplay.blue_jay",
                    25,
                    () -> FowlPlayConfig.blueJaySpawnWeight,
                    val -> FowlPlayConfig.blueJaySpawnWeight = val,
                    1,
                    () -> FowlPlayConfig.blueJayMinGroupSize,
                    val -> FowlPlayConfig.blueJayMinGroupSize = val,
                    2,
                    () -> FowlPlayConfig.blueJayMaxGroupSize,
                    val -> FowlPlayConfig.blueJayMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.cardinal",
                    35,
                    () -> FowlPlayConfig.cardinalSpawnWeight,
                    val -> FowlPlayConfig.cardinalSpawnWeight = val,
                    1,
                    () -> FowlPlayConfig.cardinalMinGroupSize,
                    val -> FowlPlayConfig.cardinalMinGroupSize = val,
                    2,
                    () -> FowlPlayConfig.cardinalMaxGroupSize,
                    val -> FowlPlayConfig.cardinalMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.chickadee",
                    50,
                    () -> FowlPlayConfig.chickadeeSpawnWeight,
                    val -> FowlPlayConfig.chickadeeSpawnWeight = val,
                    3,
                    () -> FowlPlayConfig.chickadeeMinGroupSize,
                    val -> FowlPlayConfig.chickadeeMinGroupSize = val,
                    5,
                    () -> FowlPlayConfig.chickadeeMaxGroupSize,
                    val -> FowlPlayConfig.chickadeeMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.duck",
                    20,
                    () -> FowlPlayConfig.duckSpawnWeight,
                    val -> FowlPlayConfig.duckSpawnWeight = val,
                    6,
                    () -> FowlPlayConfig.duckMinGroupSize,
                    val -> FowlPlayConfig.duckMinGroupSize = val,
                    12,
                    () -> FowlPlayConfig.duckMaxGroupSize,
                    val -> FowlPlayConfig.duckMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.gull",
                    30,
                    () -> FowlPlayConfig.gullSpawnWeight,
                    val -> FowlPlayConfig.gullSpawnWeight = val,
                    8,
                    () -> FowlPlayConfig.gullMinGroupSize,
                    val -> FowlPlayConfig.gullMinGroupSize = val,
                    12,
                    () -> FowlPlayConfig.gullMaxGroupSize,
                    val -> FowlPlayConfig.gullMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.penguin",
                    1,
                    () -> FowlPlayConfig.penguinSpawnWeight,
                    val -> FowlPlayConfig.penguinSpawnWeight = val,
                    16,
                    () -> FowlPlayConfig.penguinMinGroupSize,
                    val -> FowlPlayConfig.penguinMinGroupSize = val,
                    24,
                    () -> FowlPlayConfig.penguinMaxGroupSize,
                    val -> FowlPlayConfig.penguinMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.pigeon",
                    20,
                    () -> FowlPlayConfig.pigeonSpawnWeight,
                    val -> FowlPlayConfig.pigeonSpawnWeight = val,
                    4,
                    () -> FowlPlayConfig.pigeonMinGroupSize,
                    val -> FowlPlayConfig.pigeonMinGroupSize = val,
                    8,
                    () -> FowlPlayConfig.pigeonMaxGroupSize,
                    val -> FowlPlayConfig.pigeonMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.raven",
                    20,
                    () -> FowlPlayConfig.ravenSpawnWeight,
                    val -> FowlPlayConfig.ravenSpawnWeight = val,
                    1,
                    () -> FowlPlayConfig.ravenMinGroupSize,
                    val -> FowlPlayConfig.ravenMinGroupSize = val,
                    3,
                    () -> FowlPlayConfig.ravenMaxGroupSize,
                    val -> FowlPlayConfig.ravenMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.robin",
                    50,
                    () -> FowlPlayConfig.robinSpawnWeight,
                    val -> FowlPlayConfig.robinSpawnWeight = val,
                    3,
                    () -> FowlPlayConfig.robinMinGroupSize,
                    val -> FowlPlayConfig.robinMinGroupSize = val,
                    5,
                    () -> FowlPlayConfig.robinMaxGroupSize,
                    val -> FowlPlayConfig.robinMaxGroupSize = val
                ))
                .group(createSpawningGroup(
                    "entity.fowlplay.sparrow",
                    75,
                    () -> FowlPlayConfig.sparrowSpawnWeight,
                    val -> FowlPlayConfig.sparrowSpawnWeight = val,
                    6,
                    () -> FowlPlayConfig.sparrowMinGroupSize,
                    val -> FowlPlayConfig.sparrowMinGroupSize = val,
                    10,
                    () -> FowlPlayConfig.sparrowMaxGroupSize,
                    val -> FowlPlayConfig.sparrowMaxGroupSize = val
                ))
                .build()
            )
            .save(FowlPlayConfig::saveConfig)
            .build()
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
