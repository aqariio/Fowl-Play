package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.archaeopteryx.common.ai.behaviour.AllApplicableBehaviours;
import aqario.archaeopteryx.common.ai.behaviour.ExtendedBehaviour;
import aqario.archaeopteryx.common.ai.behaviour.OneRandomBehaviour;
import aqario.archaeopteryx.common.ai.behaviour.misc.Idle;
import aqario.archaeopteryx.common.ai.behaviour.path.SetRandomSwimTarget;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import aqario.fowlplay.common.util.BirdUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.function.Predicate;

/**
 * A collection of preconfigured group behaviours for ease of use.
 */
public class CompositeBehaviours {
    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> trySetPerchWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetPerchWalkTarget<>(),
            new SetRandomFlightTarget<E>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopCondition(Predicate.not(FlyingBirdEntity::isFlying))
        );
    }

    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> trySetWaterWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetWaterWalkTarget<E>()
                .radius(32, 24),
            new SetRandomFlightTarget<E>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopCondition(Predicate.not(FlyingBirdEntity::isFlying))
        );
    }

    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> trySetNonAirWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetNonAirWalkTarget<E>()
                .radius(32)
                .dontAvoidWater(),
            new SetRandomFlightTarget<E>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopCondition(Predicate.not(FlyingBirdEntity::isFlying))
        );
    }

    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> trySetGroundWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetNonAirWalkTarget<E>()
                .radius(32, 16),
            new SetRandomFlightTarget<E>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopCondition(Predicate.not(FlyingBirdEntity::isFlying))
        );
    }

    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> trySetPerchRestTarget() {
        return CompositeBehaviours.<E>trySetPerchWalkTarget()
            .startCondition(Predicate.not(BirdUtils::isPerched))
            .stopCondition(BirdUtils::isPerched);
    }

    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> trySetWaterRestTarget() {
        return new AllApplicableBehaviours<>(
            new SetWaterWalkTarget<E>()
                .radius(64, 32),
            new SetNonAirWalkTarget<>()
                .radius(64, 32),
            new SetRandomFlightTarget<E>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopCondition(Predicate.not(FlyingBirdEntity::isFlying))
        )
            .startCondition(Predicate.not(Entity::isInWaterOrBubble))
            .stopCondition(Entity::isInWaterOrBubble);
    }

    public static <E extends BirdEntity & BirdBrain<E>> ExtendedBehaviour<E> idleAndLookAround() {
        return new OneRandomBehaviour<>(
            new SetRandomLookTarget<E>(),
            new Idle<E>()
                .noTimeout()
        );
    }

    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> tryPickUpFood() {
        return new AllApplicableBehaviours<E>(
            CustomBehaviours.setNearestFoodWalkTarget(),
            new SetRandomFlightTarget<E>()
                .startCondition(FlyingBirdEntity::isFlying)
        );
    }

    public static ExtendedBehaviour<PenguinEntity> slideToWater() {
        return new AllApplicableBehaviours<>(
            Pair.of(
                SlideBehaviours.startSliding(),
                1
            ),
            Pair.of(
                new SetRandomSwimTarget<PenguinEntity>()
                    .setRadius(64, 24),
                2
            )
        ).startCondition(entity -> !entity.isMemoryPresent(MemoryModuleType.HAS_HUNTING_COOLDOWN));
    }

    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> tryPerch() {
        return new OneRandomBehaviour<>(
            Pair.of(
                CompositeBehaviours.<E>idleAndLookAround()
                    .runtime(30, 100)
                    .startCondition(BirdUtils::isPerched)
                    .stopCondition(Predicate.not(BirdUtils::isPerched)),
                8
            ),
            Pair.of(
                CompositeBehaviours.trySetPerchWalkTarget(),
                1
            )
        );
    }

    public static <E extends FlyingBirdEntity & BirdBrain<E>> ExtendedBehaviour<E> tryForage() {
        return new OneRandomBehaviour<>(
            Pair.of(
                CompositeBehaviours.<E>idleAndLookAround()
                    .runtime(30, 100)
                    .startCondition(Entity::onGround)
                    .stopCondition(Predicate.not(Entity::onGround)),
                2
            ),
            Pair.of(
                trySetGroundWalkTarget(),
                1
            )
        );
    }
}
