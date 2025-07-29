package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.ScheduleBuilder;

import java.util.function.Supplier;

public class FowlPlaySchedules {
    public static final Supplier<Schedule> FORAGER = register("forager", new ScheduleBuilder(new Schedule())
        .withActivity(0, FowlPlayActivities.PERCH.get())
        .withActivity(1000, FowlPlayActivities.FORAGE.get())
        .withActivity(6000, FowlPlayActivities.PERCH.get())
        .withActivity(8000, FowlPlayActivities.FORAGE.get())
        .withActivity(11000, FowlPlayActivities.PERCH.get())
        .withActivity(12000, Activity.REST)
    );
    public static final Supplier<Schedule> HUNTER = register("hunter", new ScheduleBuilder(new Schedule())
        .withActivity(0, FowlPlayActivities.PERCH.get())
        .withActivity(1000, FowlPlayActivities.HUNT.get())
        .withActivity(6000, FowlPlayActivities.SOAR.get())
        .withActivity(8000, FowlPlayActivities.HUNT.get())
        .withActivity(11000, FowlPlayActivities.PERCH.get())
        .withActivity(12000, Activity.REST)
    );

    private static Supplier<Schedule> register(String id, ScheduleBuilder builder) {
        return PlatformHelper.registerSchedule(id, builder::build);
    }
}
