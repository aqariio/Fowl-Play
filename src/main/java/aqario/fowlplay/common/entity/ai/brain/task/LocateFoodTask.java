package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.GullBrain;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;

public class LocateFoodTask {
    public static Task<BirdEntity> run() {
        return TaskTriggerer.task(
            builder -> builder.group(
                    builder.queryMemoryValue(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                    builder.queryMemoryAbsent(FowlPlayMemoryModuleType.SEES_FOOD),
                    builder.queryMemoryAbsent(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD)
                )
                .apply(builder, (nearestVisibleWantedItem, seesFood, cannotEatFood) -> (world, entity, time) -> {
                    ItemEntity item = builder.getValue(nearestVisibleWantedItem);
                    if (!GullBrain.getFood().test(item.getStack())) {
                        return false;
                    }
                    else {
                        seesFood.remember(true);
                        return true;
                    }
                })
        );
    }
}
