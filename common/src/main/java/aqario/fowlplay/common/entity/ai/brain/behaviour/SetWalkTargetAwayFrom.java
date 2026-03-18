package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Function;

public class SetWalkTargetAwayFrom<E extends BirdEntity, T> extends SpeedModifiableBehaviour<E> {
    private final List<Pair<MemoryModuleType<?>, MemoryStatus>> memoryRequirements;
    protected final MemoryModuleType<T> memoryType;
    protected final Function<T, Vec3> targetPosition;

    public SetWalkTargetAwayFrom(MemoryModuleType<T> memoryType, Function<T, Vec3> targetPosition) {
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
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return this.memoryRequirements == null ? List.of() : this.memoryRequirements;
    }

    @Override
    protected void start(E entity) {
        Brain<?> brain = entity.getBrain();
        WalkTarget walkTarget = BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET);
        Vec3 curPos = entity.position();
        Vec3 fleeTargetPos = this.targetPosition.apply(BrainUtils.getMemory(brain, this.memoryType));
        if(walkTarget != null && walkTarget.getSpeedModifier() == this.speedModifier.apply(entity, walkTarget.getTarget().currentPosition())) {
            Vec3 vec3d3 = walkTarget.getTarget().currentPosition().subtract(curPos);
            Vec3 distanceVec = fleeTargetPos.subtract(curPos);
            if(vec3d3.dot(distanceVec) < 0.0) {
                return;
            }
        }

        for(int j = 0; j < 10; j++) {
            Vec3 target = LandRandomPos.getPosAway(entity, 16, 16, fleeTargetPos);
            if(target != null) {
                BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(target));
                BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(target, this.speedModifier.apply(entity, target), 0));
                break;
            }
        }
    }
}
