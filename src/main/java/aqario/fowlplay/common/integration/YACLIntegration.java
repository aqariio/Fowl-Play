package aqario.fowlplay.common.integration;

import aqario.fowlplay.common.config.FPConfig;
import aqario.fowlplay.common.entity.FPMobCategory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class YACLIntegration {
    public static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.create(FPConfig.HANDLER, (defaults, config, builder) -> builder
                .title(Component.translatable("config.title"))
                .category(ConfigCategory.createBuilder()
                    .name(Component.translatable("config.common"))
                    .option(createImprovementOption(
                        "entity.minecraft.chicken",
                        () -> config.replaceChicken,
                        val -> config.replaceChicken = val
                    ))
                    .option(createImprovementOption(
                        "entity.minecraft.parrot",
                        () -> config.replaceParrot,
                        val -> config.replaceParrot = val
                    ))
                    .build()
                )
                .category(ConfigCategory.createBuilder()
                    .name(Component.translatable("config.spawning"))
                    .group(createSpawnCapGroup(
                        defaults.ambientBirdsSpawnCap,
                        () -> config.ambientBirdsSpawnCap,
                        val -> config.ambientBirdsSpawnCap = val,
                        defaults.birdsSpawnCap,
                        () -> config.birdsSpawnCap,
                        val -> config.birdsSpawnCap = val
                    ))
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
                        "entity.fowlplay.goose",
                        defaults.gooseSpawnWeight,
                        () -> config.gooseSpawnWeight,
                        val -> config.gooseSpawnWeight = val,
                        defaults.gooseMinGroupSize,
                        () -> config.gooseMinGroupSize,
                        val -> config.gooseMinGroupSize = val,
                        defaults.gooseMaxGroupSize,
                        () -> config.gooseMaxGroupSize,
                        val -> config.gooseMaxGroupSize = val
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
                .save(FPConfig::save)
            )
            .generateScreen(parent);
    }

    private static Option<Boolean> createImprovementOption(
        String entity,
        Supplier<Boolean> get,
        Consumer<Boolean> set
    ) {
        Component entityName = Component.translatable(entity);

        return Option.<Boolean>createBuilder()
            .name(Component.translatable("config.common.customBird", entityName))
            .description(OptionDescription.of(
                Component.translatable("config.info.restart")
                    .append("\n\n")
                    .append(Component.translatable("config.common.customBird.desc"))
            ))
            .binding(true, get, set)
            .controller(BooleanControllerBuilder::create)
            .build();
    }

    private static OptionGroup createSpawnCapGroup(
        int ambientBirds,
        Supplier<Integer> getAmbientBirds,
        Consumer<Integer> setAmbientBirds,
        int birds,
        Supplier<Integer> getBirds,
        Consumer<Integer> setBirds
    ) {
        return OptionGroup.createBuilder()
            .name(Component.translatable("config.spawning.spawnCap"))
            .option(createSpawnCapOption(
                FPMobCategory.AMBIENT_BIRDS.mobCategory,
                ambientBirds,
                getAmbientBirds,
                setAmbientBirds
            ))
            .option(createSpawnCapOption(
                FPMobCategory.BIRDS.mobCategory,
                birds,
                getBirds,
                setBirds
            ))
            .build();
    }

    private static Option<Integer> createSpawnCapOption(MobCategory category, int defaultValue, Supplier<Integer> get, Consumer<Integer> set) {
        return Option.<Integer>createBuilder()
            .name(Component.translatable("text.betterf3.line." + category.getName()))
            .description(OptionDescription.of(Component.translatable("config.spawning.spawnCap.desc")))
            .binding(defaultValue, get, set)
            .controller(option -> IntegerSliderControllerBuilder.create(option)
                .range(0, 30)
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
            .name(Component.translatable(entity))
            .option(createSpawningOption(
                entity,
                "config.spawning.spawnWeight",
                "config.spawning.spawnWeight.desc",
                spawnWeight,
                getSpawnWeight,
                setSpawnWeight
            ))
            .option(createSpawningOption(
                entity,
                "config.spawning.minGroupSize",
                "config.spawning.minGroupSize.desc",
                minGroupSize,
                getMinGroupSize,
                setMinGroupSize
            ))
            .option(createSpawningOption(
                entity,
                "config.spawning.maxGroupSize",
                "config.spawning.maxGroupSize.desc",
                maxGroupSize,
                getMaxGroupSize,
                setMaxGroupSize
            ))
            .build();
    }

    private static Option<Integer> createSpawningOption(String entity, String name, String description, int defaultValue, Supplier<Integer> get, Consumer<Integer> set) {
        return Option.<Integer>createBuilder()
            .name(Component.translatable(name))
            .description(OptionDescription.of(
                Component.translatable("config.info.restart")
                    .append("\n\n")
                    .append(Component.translatable(description, Component.translatable(entity)))
            ))
            .binding(defaultValue, get, set)
            .controller(option -> IntegerSliderControllerBuilder.create(option)
                .range(0, 100)
                .step(1)
            )
            .build();
    }
}
