package aqario.fowlplay.common.entity.ai.brain.task;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ReportingTaskControl;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;
import java.util.function.Function;

public class BetterGoToRememberedPositionTask {
    public static <E extends PathAwareEntity> TaskControl<E> toBlock(MemoryModuleType<BlockPos> memoryType, Function<E, Float> entitySpeedGetter, int range, boolean requiresWalkTarget) {
        return create(memoryType, entitySpeedGetter, range, requiresWalkTarget, Vec3d::ofBottomCenter);
    }

    public static <E extends PathAwareEntity> ReportingTaskControl<E> toEntity(MemoryModuleType<? extends Entity> memoryType, Function<E, Float> entitySpeedGetter, int range, boolean requiresWalkTarget) {
        return create(memoryType, entitySpeedGetter, range, requiresWalkTarget, Entity::getPos);
    }

    private static <T, E extends PathAwareEntity> ReportingTaskControl<E> create(
        MemoryModuleType<T> memoryType, Function<E, Float> entitySpeedGetter, int range, boolean requiresWalkTarget, Function<T, Vec3d> targetPositionGetter
    ) {
        return TaskBuilder.task(
            instance -> instance.group(instance.registeredMemory(MemoryModuleType.WALK_TARGET), instance.presentMemory(memoryType))
                .apply(instance, (memoryAccessor, memoryAccessor2) -> (world, entity, l) -> {
                    Optional<WalkTarget> optional = instance.getValueOptional(memoryAccessor);
                    if (optional.isPresent() && !requiresWalkTarget) {
                        return false;
                    }
                    Vec3d entityPos = entity.getPos();
                    Vec3d targetPos = targetPositionGetter.apply(instance.getValue(memoryAccessor2));
                    if (!entityPos.withinRange(targetPos, range)) {
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
                            memoryAccessor.remember(new WalkTarget(vec3d4, entitySpeedGetter.apply(entity), 0));
                            break;
                        }
                    }

                    return true;


                })
        );
    }
}