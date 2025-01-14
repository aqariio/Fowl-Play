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

/**
 * Improved {@link net.minecraft.entity.ai.brain.task.WalkToNearestVisibleWantedItemTask WalkToNearestVisibleWantedItemTask} with a speedGetter
 */
public class GoToNearestWantedItemTask {
    public static <E extends LivingEntity> Task<E> create(Predicate<E> startPredicate, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget, int radius) {
        return TaskTriggerer.task(
            instance -> {
                TaskTriggerer<E, ? extends MemoryQueryResult<? extends K1, WalkTarget>> taskBuilder = requiresWalkTarget
                    ? instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                    : instance.queryMemoryAbsent(MemoryModuleType.WALK_TARGET);
                return instance.group(
                        instance.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                        taskBuilder,
                        instance.queryMemoryValue(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                        instance.queryMemoryOptional(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)
                    )
                    .apply(
                        instance,
                        (lookTarget, walkTarget, nearestWantedItem, pickupCooldownTicks) -> (world, entity, l) -> {
                            ItemEntity itemEntity = instance.getValue(nearestWantedItem);
                            if (instance.getOptionalValue(pickupCooldownTicks).isEmpty()
                                && startPredicate.test(entity)
                                && itemEntity.isInRange(entity, radius)
                                && entity.getWorld().getWorldBorder().contains(itemEntity.getBlockPos())) {
                                WalkTarget newWalkTarget = new WalkTarget(new EntityLookTarget(itemEntity, false), entitySpeedGetter.apply(entity), 0);
                                lookTarget.remember(new EntityLookTarget(itemEntity, true));
                                walkTarget.remember(newWalkTarget);
                                return true;
                            }
                            return false;
                        }
                    );
            }
        );
    }
}