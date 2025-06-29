package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Unit;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.function.Predicate;

/**
 * A collection of tasks that control the flying behavior of birds.
 */
public class FlightControlTask {
    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> startFlying() {
        return startFlying(bird -> true);
    }

    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> startFlying(Predicate<E> shouldRun) {
        return new SingleTickBehaviour<>(
            ImmutableList.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_ABSENT)
            ),
            (bird, brain) -> {
                if(bird.canStartFlying() && shouldRun.test(bird)) {
                    bird.startFlying();
                    BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.IS_FLYING.get(), Unit.INSTANCE);
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> stopFlying() {
        return new SingleTickBehaviour<>(
            ImmutableList.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.REGISTERED),
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)
            ),
            (bird, brain) -> {
                bird.stopFlying();
                return true;
            }
        );
    }

    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> stopFalling() {
        return new SingleTickBehaviour<>(
            ImmutableList.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_ABSENT)
            ),
            (bird, brain) -> {
                if(bird.fallDistance > 1 && bird.canStartFlying()) {
                    bird.startFlying();
                    BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.IS_FLYING.get(), Unit.INSTANCE);
                    return true;
                }
                return false;
            }
        );
    }
}
