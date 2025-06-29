package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PenguinEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;

/**
 * A collection of tasks that control the sliding behavior of penguins.
 */
public class SlideControlTask {
    public static <E extends PenguinEntity> SingleTickBehaviour<E> startSliding() {
        return new SingleTickBehaviour<>(
            ImmutableList.of(
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)
            ),
            (bird, brain) -> {
                if(!bird.isSliding() && bird.canStartSliding()) {
                    bird.startSliding();
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends PenguinEntity> SingleTickBehaviour<E> stopSliding() {
        return new SingleTickBehaviour<>(
            ImmutableList.of(
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)
            ),
            (bird, brain) -> {
                if(bird.isSliding()) {
                    bird.stopSliding();
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends PenguinEntity> SingleTickBehaviour<E> toggleSliding(int seconds) {
        return new SingleTickBehaviour<>(
            ImmutableList.of(
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)
            ),
            (bird, brain) -> {
                if((!bird.canStartSliding() && !bird.isSliding()) || bird.getLastPoseTickDelta() < (long) seconds * 20) {
                    return false;
                }
                if(bird.isSliding()) {
                    bird.stopSliding();
                }
                else {
                    bird.startSliding();
                }
                return true;
            }
        );
    }
}
