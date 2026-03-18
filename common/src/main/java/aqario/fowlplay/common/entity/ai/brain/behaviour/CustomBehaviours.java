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
        return new SetItemWalkTarget<E>()
            .radius(BirdUtils.ITEM_PICK_UP_RANGE)
            .speed(BirdUtils.FAST_SPEED);
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> setAvoidEntityWalkTarget() {
        return new SetWalkTargetAwayFrom<E, LivingEntity>(MemoryModuleType.AVOID_TARGET, Entity::position)
            .speed(BirdUtils.FAST_SPEED);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> idleIfNotFlying() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(entity -> !entity.isFlying() && !BirdUtils.isPerched(entity))
            .stopIf(entity -> entity.isFlying() || BirdUtils.isPerched(entity));
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> idleIfPerched() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(BirdUtils::isPerched)
            .stopIf(Predicate.not(BirdUtils::isPerched));
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> idleIfInWater() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(Entity::isInWaterOrBubble)
            .stopIf(Predicate.not(Entity::isInWaterOrBubble));
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> forgetUnderwaterAttackTarget() {
        return new InvalidateMemory<E, LivingEntity>(MemoryModuleType.ATTACK_TARGET)
            .invalidateIf(((entity, target) ->
                entity.isInWaterOrBubble() && target.isUnderWater() && target.position().y < entity.position().y
            ));
    }
}
