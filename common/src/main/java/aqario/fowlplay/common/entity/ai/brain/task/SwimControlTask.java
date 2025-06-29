package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Unit;
import net.tslat.smartbrainlib.util.BrainUtils;

/**
 * A collection of tasks that control the swimming behavior of birds.
 */
public class SwimControlTask {
    public static <E extends BirdEntity> SingleTickBehaviour<E> startSwimming() {
        return new SingleTickBehaviour<>(
            ImmutableList.of(
                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)
            ),
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
            ImmutableList.of(
                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.REGISTERED),
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)
            ),
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
