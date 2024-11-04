package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.GullBrain;
import aqario.fowlplay.common.entity.PenguinBrain;
import aqario.fowlplay.common.entity.PigeonBrain;
import aqario.fowlplay.common.tags.FowlPlayEntityTypeTags;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public final class FowlPlaySensorType {
    public static final SensorType<TemptationsSensor> PENGUIN_TEMPTATIONS = register("penguin_temptations",
        () -> new TemptationsSensor(PenguinBrain.getFood())
    );
    public static final SensorType<TemptationsSensor> PIGEON_TEMPTATIONS = register("pigeon_temptations",
        () -> new TemptationsSensor(PigeonBrain.getFood())
    );
    public static final SensorType<TemptationsSensor> GULL_TEMPTATIONS = register("gull_temptations",
        () -> new TemptationsSensor(GullBrain.getFood())
    );
    public static final SensorType<NearestVisibleAdultsSensor> NEAREST_ADULTS = register("nearest_adults",
        NearestVisibleAdultsSensor::new
    );
    public static final SensorType<AttackablesSensor> PENGUIN_ATTACKABLES = register("penguin_attackables",
        () -> new AttackablesSensor(target -> target.getType().isIn(FowlPlayEntityTypeTags.PENGUIN_HUNT_TARGETS))
    );
    public static final SensorType<AttackablesSensor> GULL_ATTACKABLES = register("gull_attackables",
        () -> new AttackablesSensor(target ->
            target.getType().isIn(FowlPlayEntityTypeTags.GULL_HUNT_TARGETS) ||
                (target.getType().isIn(FowlPlayEntityTypeTags.GULL_BABY_HUNT_TARGETS) && target.isBaby())
        )
    );
    public static final SensorType<FlyingBirdSensor> IS_FLYING = register("is_flying",
        FlyingBirdSensor::new
    );

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, Identifier.of(FowlPlay.ID, id), new SensorType<>(factory));
    }

    public static void init() {
    }
}
