package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.ExtendedSchedule;
import aqario.fowlplay.common.registry.CommonRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;

import java.util.function.Supplier;

public class FPSchedules {
    public static final CommonRegister<Schedule> REGISTRAR = CommonRegister.create(
        BuiltInRegistries.SCHEDULE,
        FowlPlay.ID
    );

    // TODO: have separate schedules for babies and adults, and separate schedules for domestic and wild variants
    public static final Supplier<ExtendedSchedule> FORAGER = register("forager", new ExtendedSchedule()
        .activityAt(0, Activity.IDLE)
        .activityAt(1000, FPActivities.FORAGE)
        .activityAt(6000, Activity.IDLE)
        .activityAt(8000, FPActivities.FORAGE)
        .activityAt(11000, Activity.IDLE)
        .activityAt(13000, Activity.REST)
        .activityAt(23000, Activity.IDLE)
    );
    public static final Supplier<ExtendedSchedule> PENGUIN = register("penguin", new ExtendedSchedule()
        .activityAt(0, Activity.IDLE)
        .activityAt(4000, FPActivities.HUNT)
        .activityAt(8000, Activity.IDLE)
        .activityAt(13000, Activity.REST)
        .activityAt(23000, Activity.IDLE)
    );
    public static final Supplier<ExtendedSchedule> RAPTOR = register("raptor", new ExtendedSchedule()
        .activityAt(0, Activity.IDLE)
        .activityAt(1000, FPActivities.HUNT)
        .activityAt(6000, Activity.IDLE)
        .activityAt(8000, FPActivities.HUNT)
        .activityAt(11000, Activity.IDLE)
        .activityAt(13000, Activity.REST)
        .activityAt(23000, Activity.IDLE)
    );
    public static final Supplier<ExtendedSchedule> SEABIRD = register("seabird", new ExtendedSchedule()
        .activityAt(0, Activity.IDLE)
        .activityAt(1000, FPActivities.SOAR)
        .activityAt(6000, FPActivities.FORAGE)
        .activityAt(8000, FPActivities.SOAR)
        .activityAt(11000, Activity.IDLE)
        .activityAt(13000, Activity.REST)
        .activityAt(23000, Activity.IDLE)
    );
    public static final Supplier<ExtendedSchedule> WATERFOWL = register("waterfowl", new ExtendedSchedule()
        .activityAt(0, Activity.IDLE)
        .activityAt(1000, FPActivities.FORAGE)
        .activityAt(6000, Activity.IDLE)
        .activityAt(8000, FPActivities.FORAGE)
        .activityAt(11000, Activity.IDLE)
        .activityAt(13000, Activity.REST)
        .activityAt(23000, Activity.IDLE)
    );
    public static final Supplier<ExtendedSchedule> WATERFOWL_DOMESTIC = register("waterfowl_domestic", new ExtendedSchedule()
        .activityAt(0, Activity.IDLE)
        .activityAt(12500, Activity.REST)
        .activityAt(23000, Activity.IDLE)
    );

    private static Supplier<ExtendedSchedule> register(String id, ExtendedSchedule schedule) {
        return REGISTRAR.register(id, () -> schedule);
    }
}
