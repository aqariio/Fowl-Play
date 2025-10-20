package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.core.FowlPlaySensorType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.predicate.entity.EntityPredicates;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.SensoryUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class AttackTargetSensor<E extends BirdEntity> extends EntityFilteringSensor<LivingEntity, E> {
    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return List.of(this.getMemory(), MemoryModuleType.VISIBLE_MOBS);
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorType.ATTACK_TARGETS.get();
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, self) -> {
            if (self.shouldAttack(target) && canAttack(self, target)) {
                return true;
            }
            return self.canHunt(target) && canHunt(self, target);
        };
    }

    @Nullable
    @Override
    protected LivingEntity findMatches(E entity, LivingTargetCache matcher) {
        return matcher.findFirst(target -> predicate().test(target, entity)).orElse(null);
    }

    private static boolean canAttack(BirdEntity bird, LivingEntity target) {
        return SensoryUtils.isEntityAttackable(bird, target)
            && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target);
    }

    private static boolean canHunt(BirdEntity bird, LivingEntity target) {
        return !BrainUtils.hasMemory(bird, MemoryModuleType.HAS_HUNTING_COOLDOWN)
            && canAttack(bird, target);
    }
}