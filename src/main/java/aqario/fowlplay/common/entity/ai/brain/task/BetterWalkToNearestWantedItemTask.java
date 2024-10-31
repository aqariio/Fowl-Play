package aqario.fowlplay.common.entity.ai.brain.task;

import com.mojang.datafixers.kinds.K1;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.MemoryQueryResult;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;

import java.util.function.Function;
import java.util.function.Predicate;

public class BetterWalkToNearestWantedItemTask {
    public static <E extends LivingEntity> Task<E> create(Predicate<E> startPredicate, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget, int radius) {
        return TaskTriggerer.task(
            instance -> {
                TaskTriggerer<E, ? extends MemoryQueryResult<? extends K1, WalkTarget>> TaskTriggerer = requiresWalkTarget
                    ? instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                    : instance.queryMemoryAbsent(MemoryModuleType.WALK_TARGET);
                return instance.group(
                        instance.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                        TaskTriggerer,
                        instance.queryMemoryValue(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                        instance.queryMemoryOptional(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)
                    )
                    .apply(
                        instance,
                        (memoryAccessor, memoryAccessor2, memoryAccessor3, memoryAccessor4) -> (world, livingEntity, l) -> {
                            ItemEntity itemEntity = instance.getValue(memoryAccessor3);
                            if (instance.getOptionalValue(memoryAccessor4).isEmpty()
                                && startPredicate.test(livingEntity)
                                && itemEntity.isInRange(livingEntity, radius)
                                && livingEntity.getWorld().getWorldBorder().contains(itemEntity.getBlockPos())) {
                                WalkTarget walkTarget = new WalkTarget(new EntityLookTarget(itemEntity, false), entitySpeedGetter.apply(livingEntity), 0);
                                memoryAccessor.remember(new EntityLookTarget(itemEntity, true));
                                memoryAccessor2.remember(walkTarget);
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                    );
            }
        );
    }
}