package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Function;

public class SetWalkTargetAwayFromTask<E extends BirdEntity, T> extends SpeedModifiableBehaviour<E> {
    private final List<Pair<MemoryModuleType<?>, MemoryModuleState>> memoryRequirements;
    protected final MemoryModuleType<T> memoryType;
    protected final Function<T, Vec3d> targetPosition;

    public SetWalkTargetAwayFromTask(MemoryModuleType<T> memoryType, Function<T, Vec3d> targetPosition) {
        this.memoryType = memoryType;
        this.targetPosition = targetPosition;
        this.memoryRequirements = MemoryList.create(2)
            .registered(
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.WALK_TARGET
            )
            .present(memoryType);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return this.memoryRequirements == null ? List.of() : this.memoryRequirements;
    }

    @Override
    protected void start(E entity) {
        Brain<?> brain = entity.getBrain();
        WalkTarget walkTarget = BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET);
        Vec3d curPos = entity.getPos();
        Vec3d fleeTargetPos = this.targetPosition.apply(BrainUtils.getMemory(brain, this.memoryType));
        if(walkTarget != null && walkTarget.getSpeed() == this.speedModifier.apply(entity, walkTarget.getLookTarget().getPos())) {
            Vec3d vec3d3 = walkTarget.getLookTarget().getPos().subtract(curPos);
            Vec3d distanceVec = fleeTargetPos.subtract(curPos);
            if(vec3d3.dotProduct(distanceVec) < 0.0) {
                return;
            }
        }

        for(int j = 0; j < 10; j++) {
            Vec3d target = FuzzyTargeting.findFrom(entity, 16, 16, fleeTargetPos);
            if(target != null) {
                BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(target));
                BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(target, this.speedModifier.apply(entity, target), 0));
                break;
            }
        }
    }
}
