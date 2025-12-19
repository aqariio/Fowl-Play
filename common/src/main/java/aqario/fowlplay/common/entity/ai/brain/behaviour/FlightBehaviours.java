package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.archaeopteryx.common.ai.behaviour.ExtendedBehaviour;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;

/**
 * A collection of behaviours that control the flying behaviour of birds.
 */
public class FlightBehaviours {
    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> startFlying() {
        return new AnonymousBehaviour<E>(
            bird -> {
                bird.startFlying();
                return true;
            }
        )
            .startCondition(FlyingBirdEntity::canStartFlying);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> stopFlying() {
        return new AnonymousBehaviour<>(
            bird -> {
                bird.stopFlying();
                return true;
            }
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> stopFalling() {
        return new AnonymousBehaviour<E>(
            bird -> {
                bird.startFlying();
                return true;
            }
        )
            .startCondition(bird -> bird.fallDistance > 1 && bird.canStartFlying());
    }
}
