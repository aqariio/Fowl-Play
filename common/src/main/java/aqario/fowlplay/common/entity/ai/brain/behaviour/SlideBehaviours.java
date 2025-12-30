package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.archaeopteryx.common.ai.behaviour.AnonymousBehaviour;
import aqario.archaeopteryx.common.ai.behaviour.ExtendedBehaviour;
import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;

/**
 * A collection of behaviours that control the sliding behaviour of penguins.
 */
public class SlideBehaviours {
    public static ExtendedBehaviour<PenguinEntity> startSliding() {
        return new AnonymousBehaviour<>(
            PenguinEntity::startSliding
        )
            .startCondition(bird -> !bird.isSliding() && bird.canStartSliding());
    }

    public static ExtendedBehaviour<PenguinEntity> stopSliding() {
        return new AnonymousBehaviour<>(
            PenguinEntity::stopSliding
        )
            .startCondition(PenguinEntity::isSliding);
    }

    public static ExtendedBehaviour<PenguinEntity> toggleSliding(int seconds) {
        return new AnonymousBehaviour<PenguinEntity>(
            bird -> {
                if(bird.isSliding()) {
                    bird.stopSliding();
                }
                else {
                    bird.startSliding();
                }
            }
        )
            .startCondition(bird ->
                (!bird.canStartSliding() && !bird.isSliding()) || bird.getLastPoseTickDelta() < (long) seconds * 20
            );
    }
}
