package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import net.minecraft.entity.ai.brain.MemoryModuleType;

/**
 * A collection of tasks that control the flying behavior of birds.
 */
public class FlightTasks {
    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> startFlying() {
        return new SingleTickBehaviour<>(
            MemoryList.create(1)
                .absent(FowlPlayMemoryModuleType.IS_FLYING.get()),
            (bird, brain) -> {
                if(bird.canStartFlying()) {
                    bird.startFlying();
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> stopFlying() {
        return new SingleTickBehaviour<>(
            MemoryList.create(2)
                .present(FowlPlayMemoryModuleType.IS_FLYING.get())
                .registered(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                bird.stopFlying();
                return true;
            }
        );
    }

    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> stopFalling() {
        return new SingleTickBehaviour<>(
            MemoryList.create(1)
                .absent(FowlPlayMemoryModuleType.IS_FLYING.get()),
            (bird, brain) -> {
                if(bird.fallDistance > 1 && bird.canStartFlying()) {
                    bird.startFlying();
                    return true;
                }
                return false;
            }
        );
    }
}
