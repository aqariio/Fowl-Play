package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.ExtendedActivity;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.schedule.Activity;

public final class FowlPlayActivities {
    public static final Holder<Activity> DELIVER = register("deliver");
    public static final Holder<Activity> FOLLOW = register("follow");
    public static final Holder<Activity> FORAGE = register("forage");
    public static final Holder<Activity> PERCH = register("perch");
    public static final Holder<Activity> PICK_UP = register("pick_up");
    public static final Holder<Activity> SOAR = register("soar");

    private static Holder<Activity> register(String id) {
        return PlatformHelper.registerActivity(id, () -> new ExtendedActivity(FowlPlay.id(id)));
    }

    public static void init() {
    }
}
