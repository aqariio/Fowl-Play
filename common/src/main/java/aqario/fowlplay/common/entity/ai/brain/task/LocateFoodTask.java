package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.GullBrain;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;

public class LocateFoodTask {
    public static TaskControl<BirdEntity> run() {
        return TaskBuilder.task(
            builder -> builder.group(
                    builder.presentMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                    builder.absentMemory(FowlPlayMemoryModuleType.SEES_FOOD),
                    builder.absentMemory(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD)
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
