package aqario.fowlplay.common.entity.ai.brain.sensor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;

import java.util.function.Predicate;

public class AttackablesSensor extends NearestVisibleLivingEntitySensor {
    public static final float TARGET_DETECTION_DISTANCE = 32.0F;
    private final Predicate<LivingEntity> canHunt;

    public AttackablesSensor(Predicate<LivingEntity> canHunt) {
        this.canHunt = canHunt;
    }

    @Override
    protected boolean matches(LivingEntity entity, LivingEntity target) {
        return this.isInRange(entity, target)
            && this.canHunt(entity, target)
            && Sensor.testAttackableTargetPredicate(entity, target);
    }

    private boolean canHunt(LivingEntity entity, LivingEntity target) {
        return !entity.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN) && canHunt.test(target);
    }

    private boolean isInRange(LivingEntity entity, LivingEntity target) {
        return target.squaredDistanceTo(entity) <= TARGET_DETECTION_DISTANCE * TARGET_DETECTION_DISTANCE;
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}