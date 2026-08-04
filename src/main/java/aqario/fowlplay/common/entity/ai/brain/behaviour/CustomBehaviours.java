package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.InvalidateMemory;

import java.util.function.Predicate;

/**
 * A collection of preconfigured behaviours for ease of use.
 */
public class CustomBehaviours {
    public static <E extends BirdEntity> ExtendedBehaviour<E> setNearestFoodWalkTarget() {
        return new SetFoodWalkTarget<E>()
            .speed(BirdUtils.FAST_SPEED);
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> setAvoidEntityWalkTarget() {
        return new SetWalkTargetAwayFrom<E, LivingEntity>(MemoryModuleType.AVOID_TARGET, Entity::position)
            .speed(BirdUtils.FAST_SPEED);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> idleIfNotMoving() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(entity -> !entity.isFlying()
                && !BirdUtils.isPerched(entity)
                && !entity.isMemoryPresent(MemoryModuleType.WALK_TARGET)
            )
            .stopIf(entity -> entity.isFlying()
                || BirdUtils.isPerched(entity)
                || entity.isMemoryPresent(MemoryModuleType.WALK_TARGET)
            );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> sleepIfPerched() {
        return new Sleep<E>()
            .noTimeout()
            .startCondition(BirdUtils::isPerched)
            .stopIf(Predicate.not(BirdUtils::isPerched));
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> sleepIfInWater() {
        return new Sleep<E>()
            .noTimeout()
            .startCondition(E::isInWaterOrBubble)
            .stopIf(Predicate.not(E::isInWaterOrBubble));
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> forgetUnderwaterAttackTarget() {
        return new InvalidateMemory<E, LivingEntity>(MemoryModuleType.ATTACK_TARGET)
            .invalidateIf(BirdUtils::isSelfAndTargetInWater);
    }
}
