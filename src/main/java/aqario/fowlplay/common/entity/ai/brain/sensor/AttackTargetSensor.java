package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;

public class AttackTargetSensor extends NearestVisibleLivingEntitySensor {
    public static final float TARGET_DETECTION_DISTANCE = 24.0F;

    @Override
    protected boolean matches(LivingEntity entity, LivingEntity target) {
        return this.isInRange(entity, target)
            && ((BirdEntity) entity).canAttack(target)
            && Sensor.testAttackableTargetPredicate(entity, target);
    }

    private boolean isInRange(LivingEntity entity, LivingEntity target) {
        return target.squaredDistanceTo(entity) <= TARGET_DETECTION_DISTANCE * TARGET_DETECTION_DISTANCE;
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}