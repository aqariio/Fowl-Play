package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.util.Unit;

/**
 * A collection of tasks that control the swimming behavior of birds.
 */
public class SwimControlTask {
    public static <E extends BirdEntity> Task<E> startSwimming() {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryAbsent(MemoryModuleType.IS_IN_WATER),
                    instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (swimming, walkTarget) -> (world, bird, l) -> {
                        if (bird.isInsideWaterOrBubbleColumn()) {
                            swimming.remember(Unit.INSTANCE);
                            walkTarget.forget();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends BirdEntity> Task<E> stopSwimming() {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryValue(MemoryModuleType.IS_IN_WATER),
                    instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (swimming, walkTarget) -> (world, bird, l) -> {
                        if (!bird.isInsideWaterOrBubbleColumn()) {
                            swimming.forget();
                            walkTarget.forget();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }
}
