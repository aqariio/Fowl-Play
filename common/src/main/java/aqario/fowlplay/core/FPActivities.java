package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.ExtendedActivity;
import aqario.fowlplay.common.registry.CommonRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;

import java.util.function.Supplier;

public final class FPActivities {
    public static final CommonRegister<Activity> REGISTRAR = CommonRegister.create(
        BuiltInRegistries.ACTIVITY,
        FowlPlay.ID
    );

    public static final Supplier<Activity> DELIVER = register("deliver");
    public static final Supplier<Activity> FOLLOW = register("follow");
    public static final Supplier<Activity> FORAGE = register("forage");
    public static final Supplier<Activity> HUNT = register("hunt");
    public static final Supplier<Activity> PICK_UP = register("pick_up");
    public static final Supplier<Activity> SOAR = register("soar");

    private static Supplier<Activity> register(String id) {
        return REGISTRAR.register(id, () -> new ExtendedActivity(FowlPlay.id(id)));
    }
}
