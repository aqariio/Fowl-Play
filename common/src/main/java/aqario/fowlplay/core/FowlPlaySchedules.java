package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.ExtendedSchedule;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.entity.ai.brain.Activity;

import java.util.function.Supplier;

public class FowlPlaySchedules {
    public static final Supplier<ExtendedSchedule> FORAGER = register("forager", new ExtendedSchedule()
        .activityAt(0, FowlPlayActivities.PERCH)
        .activityAt(1000, FowlPlayActivities.FORAGE)
        .activityAt(6000, FowlPlayActivities.PERCH)
        .activityAt(8000, FowlPlayActivities.FORAGE)
        .activityAt(11000, FowlPlayActivities.PERCH)
        .activityAt(12500, Activity.REST)
        .activityAt(23000, FowlPlayActivities.PERCH)
    );
    public static final Supplier<ExtendedSchedule> RAPTOR = register("raptor", new ExtendedSchedule()
        .activityAt(0, FowlPlayActivities.PERCH)
        .activityAt(1000, FowlPlayActivities.SOAR)
        .activityAt(6000, FowlPlayActivities.PERCH)
        .activityAt(8000, FowlPlayActivities.SOAR)
        .activityAt(11000, FowlPlayActivities.PERCH)
        .activityAt(12500, Activity.REST)
        .activityAt(23000, FowlPlayActivities.PERCH)
    );
    public static final Supplier<ExtendedSchedule> SEABIRD = register("seabird", new ExtendedSchedule()
        .activityAt(0, Activity.IDLE)
        .activityAt(1000, FowlPlayActivities.SOAR)
        .activityAt(6000, FowlPlayActivities.FORAGE)
        .activityAt(8000, FowlPlayActivities.SOAR)
        .activityAt(11000, Activity.IDLE)
        .activityAt(12500, Activity.REST)
        .activityAt(23000, Activity.IDLE)
    );
    public static final Supplier<ExtendedSchedule> WATERFOWL = register("waterfowl", new ExtendedSchedule()
        .activityAt(0, Activity.IDLE)
        .activityAt(1000, FowlPlayActivities.FORAGE)
        .activityAt(6000, Activity.IDLE)
        .activityAt(8000, FowlPlayActivities.FORAGE)
        .activityAt(11000, Activity.IDLE)
        .activityAt(12500, Activity.REST)
        .activityAt(23000, Activity.IDLE)
    );

    private static Supplier<ExtendedSchedule> register(String id, ExtendedSchedule schedule) {
        return PlatformHelper.registerSchedule(id, () -> schedule);
    }

    public static void init() {
    }
}
