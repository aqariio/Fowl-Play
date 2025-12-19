package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.archaeopteryx.common.ai.behaviour.AnonymousBehaviour;
import aqario.archaeopteryx.common.ai.behaviour.ExtendedBehaviour;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;

/**
 * A collection of behaviours that control the sliding behaviour of penguins.
 */
public class SlideBehaviours {
    public static <E extends PenguinEntity & BirdBrain<E>> ExtendedBehaviour<E> startSliding() {
        return new AnonymousBehaviour<E>(
            bird -> {
                bird.startSliding();
            }
        )
            .startCondition(bird -> !bird.isSliding() && bird.canStartSliding());
    }

    public static <E extends PenguinEntity & BirdBrain<E>> ExtendedBehaviour<E> stopSliding() {
        return new AnonymousBehaviour<E>(
            bird -> {
                bird.stopSliding();
                return true;
            }
        )
            .startCondition(PenguinEntity::isSliding);
    }

    public static <E extends PenguinEntity & BirdBrain<E>> ExtendedBehaviour<E> toggleSliding(int seconds) {
        return new AnonymousBehaviour<E>(
            bird -> {
                if(bird.isSliding()) {
                    bird.stopSliding();
                }
                else {
                    bird.startSliding();
                }
                return true;
            }
        )
            .startCondition(bird ->
                (!bird.canStartSliding() && !bird.isSliding()) || bird.getLastPoseTickDelta() < (long) seconds * 20
            );
    }
}
