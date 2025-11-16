package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

/**
 * A collection of behaviours that control the flying behaviour of birds.
 */
public class FlightBehaviours {
    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> startFlying() {
        return new AnonymousBehaviour<E>(
            (bird, brain) -> {
                bird.startFlying();
                return true;
            }
        )
            .startCondition(FlyingBirdEntity::canStartFlying);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> stopFlying() {
        return new AnonymousBehaviour<>(
            (bird, brain) -> {
                bird.stopFlying();
                return true;
            }
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> stopFalling() {
        return new AnonymousBehaviour<E>(
            (bird, brain) -> {
                bird.startFlying();
                return true;
            }
        )
            .startCondition(bird -> bird.fallDistance > 1 && bird.canStartFlying());
    }
}
