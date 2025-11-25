package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.util.Birds;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.AllApplicableBehaviours;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomSwimTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.function.Predicate;

/**
 * A collection of preconfigured group behaviours for ease of use.
 */
public class CompositeBehaviours {
    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetWaterWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetWaterWalkTarget<E>()
                .radius(32, 24),
            new SetRandomFlightTarget<>()
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetNonAirWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetNonAirWalkTarget<E>()
                .setRadius(32)
                .dontAvoidWater(),
            new SetRandomFlightTarget<>()
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetGroundWalkTarget() {
        return new AllApplicableBehaviours<>(
            new SetNonAirWalkTarget<E>()
                .setRadius(32, 16),
            new SetRandomFlightTarget<>()
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> trySetWaterRestTarget() {
        return new AllApplicableBehaviours<>(
            new SetWaterWalkTarget<E>()
                .radius(64, 32),
            new SetRandomFlightTarget<>()
        )
            .startCondition(Predicate.not(Entity::isInWaterOrBubble))
            .stopIf(Entity::isInWaterOrBubble);
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> idleAndLookAround() {
        return new OneRandomBehaviour<>(
            new SetRandomLookTarget<>(),
            new Idle<>()
                .noTimeout()
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> tryPickUpFood() {
        return new AllApplicableBehaviours<>(
            CustomBehaviours.setNearestFoodWalkTarget(),
            new SetRandomFlightTarget<>()
        );
    }

    @SuppressWarnings("unchecked")
    public static ExtendedBehaviour<PenguinEntity> slideToWater() {
        return new AllApplicableBehaviours<>(
            Pair.of(
                SlideBehaviours.startSliding(),
                1
            ),
            Pair.of(
                new SetRandomSwimTarget<>()
                    .setRadius(64, 24),
                2
            )
        ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.HAS_HUNTING_COOLDOWN));
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> tryPerch() {
        return new OneRandomBehaviour<>(
            Pair.of(
                idleAndLookAround()
                    .runForBetween(30, 100)
                    .startCondition(Birds::isPerched)
                    .stopIf(Predicate.not(Birds::isPerched)),
                8
            ),
            Pair.of(
                new SetPerchWalkTarget<>(),
                1
            )
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> tryForage() {
        return new OneRandomBehaviour<>(
            Pair.of(
                idleAndLookAround()
                    .runForBetween(30, 100)
                    .startCondition(Entity::onGround)
                    .stopIf(Predicate.not(Entity::onGround)),
                2
            ),
            Pair.of(
                trySetGroundWalkTarget(),
                1
            )
        );
    }
}
