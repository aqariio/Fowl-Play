package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;

public class BirdMoveToWalkTarget<E extends BirdEntity> extends MoveToWalkTarget<E> {
    @Override
    protected boolean attemptNewPath(E entity, WalkTarget walkTarget, boolean reachedCurrentTarget) {
        return super.attemptNewPath(entity, walkTarget, reachedCurrentTarget);
        // TODO: patch for flying birds so there's less path recalculation
//        BlockPos targetPos = walkTarget.getTarget().currentBlockPosition();
//        this.path = entity.getNavigation().createPath(targetPos, 0);
//        this.speedModifier = walkTarget.getSpeedModifier();
//
//        if(reachedCurrentTarget) {
//            entity.clearMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
//
//            return false;
//        }
//
//        if(this.path != null && this.path.canReach()) {
//            entity.clearMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
//        }
//        else {
//            entity.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, entity.level().getGameTime());
//        }
//
//        if(this.path != null) {
//            return true;
//        }
//
//        Vec3 newTargetPos = DefaultRandomPos.getPosTowards(entity, 10, 7, Vec3.atBottomCenterOf(targetPos), Mth.HALF_PI);
//
//        if(newTargetPos != null) {
//            this.path = entity.getNavigation().createPath(newTargetPos.x(), newTargetPos.y(), newTargetPos.z(), 0);
//
//            return this.path != null;
//        }
//
//        return false;
    }
}
