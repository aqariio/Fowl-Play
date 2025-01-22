package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;
import java.util.function.Function;

/**
 * Improved {@link net.minecraft.entity.ai.brain.task.GoToRememberedPositionTask GoToRememberedPositionTask} with a speedGetter
 */
public class MoveAwayFromTargetTask {
    public static <E extends BirdEntity> Task<E> block(MemoryModuleType<BlockPos> memoryType, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget) {
        return create(memoryType, entitySpeedGetter, requiresWalkTarget, Vec3d::ofBottomCenter);
    }

    public static <E extends BirdEntity> SingleTickTask<E> entity(MemoryModuleType<? extends Entity> memoryType, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget) {
        return create(memoryType, entitySpeedGetter, requiresWalkTarget, Entity::getPos);
    }

    private static <T, E extends BirdEntity> SingleTickTask<E> create(
        MemoryModuleType<T> memoryType, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget, Function<T, Vec3d> targetPositionGetter
    ) {
        return TaskTriggerer.task(
            instance -> instance.group(instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET), instance.queryMemoryValue(memoryType))
                .apply(instance, (walkTarget, targetType) -> (world, entity, l) -> {
                    Optional<WalkTarget> optional = instance.getOptionalValue(walkTarget);
                    if (optional.isPresent() && !requiresWalkTarget) {
                        return false;
                    }
                    Vec3d entityPos = entity.getPos();
                    Vec3d targetPos = targetPositionGetter.apply(instance.getValue(targetType));
                    if (!entityPos.isInRange(targetPos, entity.getFleeRange())) {
                        return false;
                    }
                    if (optional.isPresent() && optional.get().getSpeed() == entitySpeedGetter.apply(entity)) {
                        Vec3d vec3d3 = optional.get().getLookTarget().getPos().subtract(entityPos);
                        Vec3d vec3d4 = targetPos.subtract(entityPos);
                        if (vec3d3.dotProduct(vec3d4) < 0.0) {
                            return false;
                        }
                    }

                    for (int j = 0; j < 10; j++) {
                        Vec3d vec3d4 = FuzzyTargeting.findFrom(entity, 16, 16, targetPos);
                        if (vec3d4 != null) {
                            walkTarget.remember(new WalkTarget(vec3d4, entitySpeedGetter.apply(entity), 0));
                            break;
                        }
                    }

                    return true;
                })
        );
    }
}