package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class FowlPlayActivities {
    public static final Activity DELIVER = register("deliver");
    public static final Activity FLY = register("fly");
    public static final Activity FORAGE = register("forage");
    public static final Activity PERCH = register("perch");
    public static final Activity PICKUP_FOOD = register("pickup_food");
    public static final Activity SOAR = register("soar");

    private static Activity register(String id) {
        return Registry.register(Registries.ACTIVITY, new Identifier(FowlPlay.ID, id), new Activity(id));
    }

    public static void init() {
    }
}
