package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
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
    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> setWaterWalkTarget() {
        return new SetWaterWalkTarget<E>()
            .radius(32, 24);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> setGroundWalkTarget() {
        return new SetNonAirWalkTarget<E>()
            .setRadius(32, 16);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> setWaterRestTarget() {
        return new SetWaterWalkTarget<E>()
            .radius(64, 32)
            .startCondition(Predicate.not(Entity::isInWaterOrBubble))
            .stopIf(Entity::isInWaterOrBubble);
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> setNearestFoodWalkTarget() {
        return new SetItemWalkTarget<E>()
            .radius(Birds.ITEM_PICK_UP_RANGE)
            .speed(Birds.FAST_SPEED);
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> setAvoidEntityWalkTarget() {
        return new SetWalkTargetAwayFrom<E, LivingEntity>(MemoryModuleType.AVOID_TARGET, Entity::position)
            .speed(Birds.FAST_SPEED);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> idleIfNotFlying() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(entity -> !entity.isFlying() && !Birds.isPerched(entity))
            .stopIf(entity -> entity.isFlying() || Birds.isPerched(entity));
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> idleIfPerched() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(Birds::isPerched)
            .stopIf(Predicate.not(Birds::isPerched));
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
