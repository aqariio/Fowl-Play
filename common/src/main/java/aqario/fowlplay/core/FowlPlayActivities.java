package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.entity.ai.brain.Activity;

import java.util.function.Supplier;

public final class FowlPlayActivities {
    public static final Supplier<Activity> DELIVER = register("deliver");
    public static final Supplier<Activity> FORAGE = register("forage");
    public static final Supplier<Activity> PERCH = register("perch");
    public static final Supplier<Activity> PICK_UP = register("pick_up");
    public static final Supplier<Activity> SOAR = register("soar");

    private static Supplier<Activity> register(String id) {
        return PlatformHelper.registerActivity(id, () -> new Activity(id));
    }

    public static void init() {
    }
}
