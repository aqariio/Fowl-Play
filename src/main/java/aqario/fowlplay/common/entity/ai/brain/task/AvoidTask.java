package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.util.Unit;

import java.util.Optional;
import java.util.function.Predicate;

public class AvoidTask {
    public static <E extends BirdEntity> Task<E> run() {
        return run(bird -> true);
    }

    public static <E extends BirdEntity> Task<E> run(Predicate<E> predicate) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryValue(MemoryModuleType.AVOID_TARGET),
                    instance.queryMemoryAbsent(FowlPlayMemoryModuleType.IS_AVOIDING)
                )
                .apply(instance, (avoidTarget, isAvoiding) -> (world, entity, time) -> {
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    LivingEntity target = instance.getValue(avoidTarget);
                    if (entity.isInRange(
                        target,
                        entity.getFleeRange() * (target instanceof FlyingBirdEntity ? 2.0F : 1.0F)
                    )) {
                        isAvoiding.remember(Unit.INSTANCE);
                        return true;
                    }
                    return false;
                })
        );
    }

    public static <E extends BirdEntity> Task<E> forget() {
        return forget(bird -> true);
    }

    public static <E extends BirdEntity> Task<E> forget(Predicate<E> predicate) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryOptional(MemoryModuleType.AVOID_TARGET),
                    instance.queryMemoryValue(FowlPlayMemoryModuleType.IS_AVOIDING)
                )
                .apply(instance, (avoidTarget, isAvoiding) -> (world, entity, time) -> {
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    Optional<LivingEntity> target = instance.getOptionalValue(avoidTarget);
                    if (target.isPresent() && entity.isInRange(
                        target.get(),
                        entity.getFleeRange() * (target.get() instanceof FlyingBirdEntity ? 2.0F : 1.0F)
                    )) {
                        return false;
                    }
                    isAvoiding.forget();
                    return true;
                })
        );
    }
}
