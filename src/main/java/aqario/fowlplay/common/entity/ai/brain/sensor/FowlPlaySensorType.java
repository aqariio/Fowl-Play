package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.tags.FowlPlayEntityTypeTags;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public final class FowlPlaySensorType {
    public static final SensorType<TemptationsSensor> DUCK_TEMPTATIONS = register("duck_temptations",
        () -> new TemptationsSensor(DuckBrain.getFood())
    );
    public static final SensorType<TemptationsSensor> GULL_TEMPTATIONS = register("gull_temptations",
        () -> new TemptationsSensor(GullBrain.getFood())
    );
    public static final SensorType<TemptationsSensor> PENGUIN_TEMPTATIONS = register("penguin_temptations",
        () -> new TemptationsSensor(PenguinBrain.getFood())
    );
    public static final SensorType<TemptationsSensor> PIGEON_TEMPTATIONS = register("pigeon_temptations",
        () -> new TemptationsSensor(PigeonBrain.getFood())
    );
    public static final SensorType<TemptationsSensor> RAVEN_TEMPTATIONS = register("raven_temptations",
        () -> new TemptationsSensor(RavenBrain.getFood())
    );
    public static final SensorType<NearestVisibleAdultsSensor> NEAREST_ADULTS = register("nearest_adults",
        NearestVisibleAdultsSensor::new
    );
    public static final SensorType<AttackTargetSensor> DUCK_ATTACKABLES = register("duck_attackables",
        () -> new AttackTargetSensor(target ->
            target.getType().isIn(FowlPlayEntityTypeTags.DUCK_HUNT_TARGETS) ||
                (target.getType().isIn(FowlPlayEntityTypeTags.DUCK_BABY_HUNT_TARGETS) && target.isBaby())
        )
    );
    public static final SensorType<AttackTargetSensor> GULL_ATTACKABLES = register("gull_attackables",
        () -> new AttackTargetSensor(target ->
            target.getType().isIn(FowlPlayEntityTypeTags.GULL_HUNT_TARGETS) ||
                (target.getType().isIn(FowlPlayEntityTypeTags.GULL_BABY_HUNT_TARGETS) && target.isBaby())
        )
    );
    public static final SensorType<AttackTargetSensor> PENGUIN_ATTACKABLES = register("penguin_attackables",
        () -> new AttackTargetSensor(target -> target.getType().isIn(FowlPlayEntityTypeTags.PENGUIN_HUNT_TARGETS))
    );
    public static final SensorType<AttackTargetSensor> RAVEN_ATTACKABLES = register("raven_attackables",
        () -> new AttackTargetSensor(target ->
            target.getType().isIn(FowlPlayEntityTypeTags.RAVEN_HUNT_TARGETS) ||
                (target.getType().isIn(FowlPlayEntityTypeTags.RAVEN_BABY_HUNT_TARGETS) && target.isBaby())
        )
    );
    public static final SensorType<PigeonSpecificSensor> PIGEON_SPECIFIC_SENSOR = register("pigeon_specific_sensor",
        PigeonSpecificSensor::new
    );
    public static final SensorType<FlyingStateSensor> IS_FLYING = register("is_flying",
        FlyingStateSensor::new
    );

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, new Identifier(FowlPlay.ID, id), new SensorType<>(factory));
    }

    public static void init() {
    }
}
