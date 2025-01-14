package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;

/**
 * A collection of tasks that control the sliding behavior of penguins.
 */
public class SlideControlTask {
    public static <E extends PenguinEntity> Task<E> startSliding() {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (walkTarget) -> (world, penguin, l) -> {
                        if (!penguin.isSliding() && penguin.canStartSliding()) {
                            penguin.startSliding();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends PenguinEntity> Task<E> stopSliding() {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (walkTarget) -> (world, penguin, l) -> {
                        if (penguin.isSliding()) {
                            penguin.stopSliding();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends PenguinEntity> Task<E> toggleSliding(int seconds) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (walkTarget) -> (world, penguin, l) -> {
                        if ((!penguin.canStartSliding() && !penguin.isSliding()) || penguin.getAnimationTicks() < (long) seconds * 20) {
                            return false;
                        }
                        if (penguin.isSliding()) {
                            penguin.stopSliding();
                        }
                        else {
                            penguin.startSliding();
                        }
                        return true;
                    }
                )
        );
    }
}
