package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;

import java.util.function.Supplier;

public final class FowlPlaySensorTypes {
    public static final Supplier<SensorType<NearbyAdultsSensor<?>>> NEARBY_ADULTS = register("nearby_adults",
        NearbyAdultsSensor::new
    );
    public static final Supplier<SensorType<AttackedSensor<?>>> ATTACKED = register("attacked",
        AttackedSensor::new
    );
    public static final Supplier<SensorType<AvoidTargetSensor<?>>> AVOID_TARGETS = register("avoid_targets",
        AvoidTargetSensor::new
    );
    public static final Supplier<SensorType<AttackTargetSensor<?>>> ATTACK_TARGETS = register("attack_targets",
        AttackTargetSensor::new
    );
    public static final Supplier<SensorType<PigeonSpecificSensor>> PIGEON_SPECIFIC_SENSOR = register("pigeon_specific_sensor",
        PigeonSpecificSensor::new
    );

    private static <U extends Sensor<?>> Supplier<SensorType<U>> register(String id, Supplier<U> factory) {
        return PlatformHelper.registerSensorType(id, () -> new SensorType<>(factory));
    }

    public static void init() {
    }
}
