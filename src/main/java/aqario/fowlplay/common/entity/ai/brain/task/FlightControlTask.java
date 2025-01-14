package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.Aquatic;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.util.Unit;

import java.util.function.Predicate;

/**
 * A collection of tasks that control the flying behavior of birds.
 */
public class FlightControlTask {
    public static <E extends FlyingBirdEntity> Task<E> startFlying(Predicate<E> shouldRun) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryAbsent(FowlPlayMemoryModuleType.IS_FLYING)
                )
                .apply(
                    instance,
                    (flying) -> (world, bird, l) -> {
                        if (bird.canStartFlying() && shouldRun.test(bird)) {
                            bird.getJumpControl().setActive();
                            bird.startFlying();
                            flying.remember(Unit.INSTANCE);
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends FlyingBirdEntity> Task<E> tryStopFlying(Predicate<E> shouldRun) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryValue(FowlPlayMemoryModuleType.IS_FLYING),
                    instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (flying, walkTarget) -> (world, bird, l) -> {
                        if ((bird.isOnGround() || (bird instanceof Aquatic aquaticBird ? aquaticBird.isFloating() : bird.isInsideWaterOrBubbleColumn())) && shouldRun.test(bird)) {
                            bird.stopFlying();
                            flying.forget();
                            walkTarget.forget();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends FlyingBirdEntity> Task<E> stopFlying(Predicate<E> shouldRun) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryValue(FowlPlayMemoryModuleType.IS_FLYING),
                    instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (flying, walkTarget) -> (world, bird, l) -> {
                        if (shouldRun.test(bird)) {
                            bird.stopFlying();
                            flying.forget();
                            walkTarget.forget();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends FlyingBirdEntity> Task<E> stopFalling() {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryAbsent(FowlPlayMemoryModuleType.IS_FLYING)
                )
                .apply(
                    instance,
                    (flying) -> (world, bird, l) -> {
                        if (bird.fallDistance > 1 && bird.canStartFlying()) {
                            bird.startFlying();
                            flying.remember(Unit.INSTANCE);
                            return true;
                        }
                        return false;
                    }
                )
        );
    }
}
