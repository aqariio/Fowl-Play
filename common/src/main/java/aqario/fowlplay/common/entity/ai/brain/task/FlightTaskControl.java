package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.Aquatic;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.util.Unit;

import java.util.function.Predicate;

/**
 * A collection of tasks that control the flying behavior of birds.
 */
public class FlightTaskControl {
    public static <E extends FlyingBirdEntity> TaskControl<E> startFlying(Predicate<E> shouldRun) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.absentMemory(FowlPlayMemoryModuleType.IS_FLYING)
                )
                .apply(
                    instance,
                    (flying) -> (world, bird, l) -> {
                        if (!bird.isFlying() && shouldRun.test(bird)) {
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

    public static <E extends FlyingBirdEntity> TaskControl<E> stopFlying(Predicate<E> shouldRun) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(FowlPlayMemoryModuleType.IS_FLYING),
                    instance.registeredMemory(MemoryModuleType.WALK_TARGET)
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

    public static <E extends FlyingBirdEntity> TaskControl<E> stopFalling() {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.absentMemory(FowlPlayMemoryModuleType.IS_FLYING)
                )
                .apply(
                    instance,
                    (flying) -> (world, bird, l) -> {
                        if (bird.fallDistance > 1 && !bird.isFlying()) {
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
