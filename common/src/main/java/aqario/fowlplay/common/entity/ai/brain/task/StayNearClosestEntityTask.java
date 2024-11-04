package aqario.fowlplay.common.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ReportingTaskControl;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.int_provider.UniformIntProvider;

import java.util.function.Function;

public class StayNearClosestEntityTask {
    public static ReportingTaskControl<PassiveEntity> create(UniformIntProvider executionRange, float speed) {
        return create(executionRange, livingEntity -> speed);
    }

    public static ReportingTaskControl<PassiveEntity> create(UniformIntProvider executionRange, Function<LivingEntity, Float> speedGetter) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT),
                    instance.registeredMemory(MemoryModuleType.LOOK_TARGET),
                    instance.absentMemory(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (nearestVisibleAdult, lookTarget, walkTarget) -> (world, entity, l) -> {
                        PassiveEntity nearest = instance.getValue(nearestVisibleAdult);
                        if (entity.isInRange(nearest, executionRange.getMax() + 1)
                            && !entity.isInRange(nearest, executionRange.getMin())) {
                            WalkTarget newWalkTarget = new WalkTarget(
                                new EntityLookTarget(nearest, false), speedGetter.apply(entity), executionRange.getMin() - 1
                            );
                            lookTarget.remember(new EntityLookTarget(nearest, true));
                            walkTarget.remember(newWalkTarget);
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                )
        );
    }
}
