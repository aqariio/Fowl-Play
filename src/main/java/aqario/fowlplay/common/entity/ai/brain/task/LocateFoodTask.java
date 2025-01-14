package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;

import java.util.function.Predicate;

public class LocateFoodTask {
    public static <E extends BirdEntity> Task<E> run() {
        return run(bird -> true);
    }

    public static <E extends BirdEntity> Task<E> run(Predicate<E> predicate) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryValue(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                    instance.queryMemoryAbsent(FowlPlayMemoryModuleType.SEES_FOOD),
                    instance.queryMemoryAbsent(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD)
                )
                .apply(instance, (nearestVisibleWantedItem, seesFood, cannotEatFood) -> (world, entity, time) -> {
                    ItemEntity item = instance.getValue(nearestVisibleWantedItem);
                    if (!entity.getFood().test(item.getStack())) {
                        return false;
                    }
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    seesFood.remember(true);
                    return true;
                })
        );
    }
}
