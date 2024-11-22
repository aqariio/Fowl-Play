package aqario.fowlplay.common.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class FowlPlayConfig extends MidnightConfig {
    public static final String VISUAL = "visual";
    public static final String AUDIO = "audio";
    public static final String SPAWNING = "spawning";

    // Visual

    @Entry(category = VISUAL)
    public static boolean customChickenModel = true;

    // Spawning

    @Comment(category = SPAWNING, centered = true)
    public static Comment spacer1;

    @Comment(category = SPAWNING, centered = true)
    public static Comment info;

    @Comment(category = SPAWNING, centered = true)
    public static Comment spacer2;

    @Comment(category = SPAWNING, centered = true)
    public static Comment blueJay;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int blueJaySpawnWeight = 25;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int blueJayMinGroupSize = 1;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int blueJayMaxGroupSize = 2;

    @Comment(category = SPAWNING, centered = true)
    public static Comment cardinal;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int cardinalSpawnWeight = 35;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int cardinalMinGroupSize = 1;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int cardinalMaxGroupSize = 2;

    @Comment(category = SPAWNING, centered = true)
    public static Comment chickadee;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int chickadeeSpawnWeight = 50;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int chickadeeMinGroupSize = 3;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int chickadeeMaxGroupSize = 5;

    @Comment(category = SPAWNING, centered = true)
    public static Comment duck;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int duckSpawnWeight = 30;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int duckMinGroupSize = 6;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int duckMaxGroupSize = 12;

    @Comment(category = SPAWNING, centered = true)
    public static Comment gull;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int gullSpawnWeight = 30;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int gullMinGroupSize = 8;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int gullMaxGroupSize = 12;

    @Comment(category = SPAWNING, centered = true)
    public static Comment penguin;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int penguinSpawnWeight = 1;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int penguinMinGroupSize = 16;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int penguinMaxGroupSize = 24;

    @Comment(category = SPAWNING, centered = true)
    public static Comment pigeon;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int pigeonSpawnWeight = 20;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int pigeonMinGroupSize = 4;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int pigeonMaxGroupSize = 8;

    @Comment(category = SPAWNING, centered = true)
    public static Comment raven;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int ravenSpawnWeight = 20;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int ravenMinGroupSize = 1;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int ravenMaxGroupSize = 3;

    @Comment(category = SPAWNING, centered = true)
    public static Comment robin;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int robinSpawnWeight = 50;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int robinMinGroupSize = 3;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int robinMaxGroupSize = 5;

    @Comment(category = SPAWNING, centered = true)
    public static Comment sparrow;

    @Entry(category = SPAWNING, min = 0, name = "Spawn Weight")
    public static int sparrowSpawnWeight = 75;

    @Entry(category = SPAWNING, min = 0, name = "Min Group Size")
    public static int sparrowMinGroupSize = 6;

    @Entry(category = SPAWNING, min = 0, name = "Max Group Size")
    public static int sparrowMaxGroupSize = 10;
}
