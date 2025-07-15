package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Unit;
import net.tslat.smartbrainlib.util.BrainUtils;

/**
 * A collection of tasks that control the swimming behavior of birds.
 */
public class SwimTasks {
    public static <E extends BirdEntity> SingleTickBehaviour<E> startSwimming() {
        return new SingleTickBehaviour<>(
            MemoryList.create(2)
                .absent(MemoryModuleType.IS_IN_WATER)
                .registered(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                if(bird.isInsideWaterOrBubbleColumn()) {
                    BrainUtils.setMemory(brain, MemoryModuleType.IS_IN_WATER, Unit.INSTANCE);
                    BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> stopSwimming() {
        return new SingleTickBehaviour<>(
            MemoryList.create(2)
                .present(MemoryModuleType.IS_IN_WATER)
                .registered(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                if(!bird.isInsideWaterOrBubbleColumn()) {
                    BrainUtils.clearMemory(brain, MemoryModuleType.IS_IN_WATER);
                    BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
                    return true;
                }
                return false;
            }
        );
    }
}
