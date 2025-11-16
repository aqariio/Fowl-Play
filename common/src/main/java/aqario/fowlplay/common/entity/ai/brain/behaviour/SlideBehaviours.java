package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.PenguinEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

/**
 * A collection of behaviours that control the sliding behaviour of penguins.
 */
public class SlideBehaviours {
    public static <E extends PenguinEntity> ExtendedBehaviour<E> startSliding() {
        return new AnonymousBehaviour<E>(
            (bird, brain) -> {
                bird.startSliding();
                return true;
            }
        )
            .startCondition(bird -> !bird.isSliding() && bird.canStartSliding());
    }

    public static <E extends PenguinEntity> ExtendedBehaviour<E> stopSliding() {
        return new AnonymousBehaviour<E>(
            (bird, brain) -> {
                bird.stopSliding();
                return true;
            }
        )
            .startCondition(PenguinEntity::isSliding);
    }

    public static <E extends PenguinEntity> ExtendedBehaviour<E> toggleSliding(int seconds) {
        return new AnonymousBehaviour<E>(
            (bird, brain) -> {
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
