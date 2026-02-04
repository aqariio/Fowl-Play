package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.archaeopteryx.common.ai.behaviour.AnonymousBehaviour;
import aqario.archaeopteryx.common.ai.behaviour.ExtendedBehaviour;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;

/**
 * A collection of behaviours that control the flying behaviour of birds.
 */
public class FlightBehaviours {
    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> startFlying() {
        return new AnonymousBehaviour<E>(
            FlyingBirdEntity::startFlying
        )
            .startCondition(FlyingBirdEntity::canStartFlying);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> stopFlying() {
        return new AnonymousBehaviour<>(
            FlyingBirdEntity::stopFlying
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> stopFalling() {
        return new AnonymousBehaviour<E>(
            FlyingBirdEntity::startFlying
        )
            .startCondition(bird -> bird.fallDistance > 1 && bird.canStartFlying());
    }
}
