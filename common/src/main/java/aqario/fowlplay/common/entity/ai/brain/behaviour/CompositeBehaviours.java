package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import aqario.fowlplay.common.util.BirdUtils;
import com.google.common.base.Predicates;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomSwimTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.function.Predicate;

/**
 * A collection of preconfigured group behaviours for ease of use.
 */
public class CompositeBehaviours {
    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetPerchWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetPerchWalkTarget<>(),
            new SetRandomFlightTarget<>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopIf(Predicate.not(FlyingBirdEntity::isFlying))
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetWaterWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetWaterWalkTarget<E>()
                .radius(32, 24),
            new SetRandomFlightTarget<>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopIf(Predicate.not(FlyingBirdEntity::isFlying))
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetNonAirWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetNonAirWalkTarget<E>()
                .radius(32)
                .dontAvoidWater(),
            new SetRandomFlightTarget<>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopIf(Predicate.not(FlyingBirdEntity::isFlying))
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetGroundWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetNonAirWalkTarget<E>()
                .radius(32, 16),
            new SetRandomFlightTarget<>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopIf(Predicate.not(FlyingBirdEntity::isFlying))
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetPerchRestTarget() {
        return CompositeBehaviours.<E>trySetPerchWalkTarget()
            .startCondition(Predicate.not(BirdUtils::isPerched))
            .stopIf(BirdUtils::isPerched);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetWaterRestTarget() {
        return new AllApplicableBehaviours<>(
            new SetWaterWalkTarget<E>()
                .radius(64, 32),
            new SetNonAirWalkTarget<>()
                .radius(64, 32),
            new SetRandomFlightTarget<>()
                .startCondition(FlyingBirdEntity::isFlying)
                .stopIf(Predicate.not(FlyingBirdEntity::isFlying))
        )
            .startCondition(Predicate.not(Entity::isInWaterOrBubble))
            .stopIf(Entity::isInWaterOrBubble);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> tryPickUpFood() {
        return new AllApplicableBehaviours<>(
            CustomBehaviours.setNearestFoodWalkTarget(),
            new SetRandomFlightTarget<>()
                .startCondition(FlyingBirdEntity::isFlying)
        );
    }

    public static ExtendedBehaviour<PenguinEntity> slideToWater() {
        return new AllApplicableBehaviours<>(
            SlideBehaviours.startSliding(),
            new SetRandomSwimTarget<>()
                .setRadius(64, 24)
        )
            .startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.HAS_HUNTING_COOLDOWN));
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> perch() {
        return new OneRandomBehaviour<>(
            Pair.of(
                new OneRandomBehaviour<>(
                    Pair.of(
                        new SetRandomLookTarget<>(),
                        4
                    ),
                    Pair.of(
                        new Idle<>()
                            .runForBetween(300, 600),
                        5
                    ),
                    Pair.of(
                        new RepeatingBehaviour<>(
                            new Call<>()
                        )
                            .repeatNTimes(entity -> entity.getRandom().nextIntBetweenInclusive(3, 6)),
                        4
                    ),
                    Pair.of(
                        new RepeatingBehaviour<>(
                            new Sing<>()
                        )
                            .repeatNTimes(entity -> entity.getRandom().nextIntBetweenInclusive(1, 3)),
                        3
                    )
                )
                    .runForBetween(900, 1800)
                    .startCondition(BirdUtils::isPerched)
                    .stopIf(Predicate.not(BirdUtils::isPerched)),
                4
            ),
            Pair.of(
                trySetPerchWalkTarget(),
                1
            )
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> forage() {
        return new SequentialBehaviour<>(
            new OneRandomBehaviour<E>(
                new SetRandomLookTarget<>(),
                new Idle<>()
                    .noTimeout()
            )
                .runForBetween(30, 100)
                .startCondition(Predicates.and(
                    E::onGround,
                    Predicates.not(BirdUtils::isPerched)
                ))
                .stopIf(Predicates.or(
                    Predicates.not(E::onGround),
                    BirdUtils::isPerched
                )),
            trySetGroundWalkTarget()
        );
    }
}
