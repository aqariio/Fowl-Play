package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.Optional;
import java.util.function.Function;

/**
 * Improved {@link net.minecraft.entity.ai.brain.task.GoToRememberedPositionTask GoToRememberedPositionTask} with a speedGetter
 */
public class MoveAwayFromTargetTask {
    public static <E extends BirdEntity> SingleTickBehaviour<E> block(MemoryModuleType<BlockPos> memoryType, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget) {
        return create(memoryType, entitySpeedGetter, requiresWalkTarget, Vec3d::ofBottomCenter);
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> entity(MemoryModuleType<? extends Entity> memoryType, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget) {
        return create(memoryType, entitySpeedGetter, requiresWalkTarget, Entity::getPos);
    }

    private static <T, E extends BirdEntity> SingleTickBehaviour<E> create(
        MemoryModuleType<T> memoryType, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget, Function<T, Vec3d> targetPositionGetter
    ) {
        return new SingleTickBehaviour<>(
            MemoryList.create(2)
                .registered(MemoryModuleType.WALK_TARGET)
                .present(memoryType),
            (bird, brain) -> {
                Optional<WalkTarget> optional = Optional.ofNullable(BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET));
                if(optional.isPresent() && !requiresWalkTarget) {
                    return false;
                }
                Vec3d entityPos = bird.getPos();
                Vec3d fleeTargetPos = targetPositionGetter.apply(BrainUtils.getMemory(brain, memoryType));
                if(optional.isPresent() && optional.get().getSpeed() == entitySpeedGetter.apply(bird)) {
                    Vec3d vec3d3 = optional.get().getLookTarget().getPos().subtract(entityPos);
                    Vec3d distanceVec = fleeTargetPos.subtract(entityPos);
                    if(vec3d3.dotProduct(distanceVec) < 0.0) {
                        return false;
                    }
                }

                for(int j = 0; j < 10; j++) {
                    Vec3d vec3d = FuzzyTargeting.findFrom(bird, 16, 16, fleeTargetPos);
                    if(vec3d != null) {
                        BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, entitySpeedGetter.apply(bird), 0));
                        break;
                    }
                }

                return true;
            }
        );
    }
}