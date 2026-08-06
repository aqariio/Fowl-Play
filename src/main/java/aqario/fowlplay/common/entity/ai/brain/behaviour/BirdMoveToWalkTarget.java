package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.pathfinder.Path;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;

public class BirdMoveToWalkTarget<E extends BirdEntity> extends MoveToWalkTarget<E> {
    private boolean flyAlongPath;

    @Override
    protected boolean attemptNewPath(E entity, WalkTarget walkTarget, boolean reachedCurrentTarget) {
        this.flyAlongPath = false;
        boolean foundGroundPath = super.attemptNewPath(entity, walkTarget, reachedCurrentTarget);
        if(!foundGroundPath) {
            return !reachedCurrentTarget
                && entity instanceof FlyingBirdEntity bird
                && bird.canStartFlying()
                && this.prepareFlightPath(entity, bird, walkTarget);
        }
        if(entity instanceof FlyingBirdEntity bird
            && this.path != null
            && BirdUtils.shouldFlyAlongPath(bird, this.path, walkTarget.getTarget().currentPosition())
        ) {
            this.prepareFlightPath(entity, bird, walkTarget);
        }
        return true;
    }

    private boolean prepareFlightPath(E entity, FlyingBirdEntity bird, WalkTarget walkTarget) {
        Path flightPath = bird.createFlightPath(walkTarget.getTarget().currentBlockPosition(), 0);
        if(flightPath == null || !flightPath.canReach()) {
            return false;
        }
        this.path = flightPath;
        this.speedModifier = walkTarget.getSpeedModifier();
        this.flyAlongPath = true;
        entity.clearMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        return true;
    }

    @Override
    protected void startOnNewPath(E entity) {
        if(this.flyAlongPath
            && entity instanceof FlyingBirdEntity flyingBird
            && flyingBird.startFlyingAlongPath(this.path, this.speedModifier)
        ) {
            this.flyAlongPath = false;
            entity.setMemory(MemoryModuleType.PATH, this.path);
            return;
        }
        this.flyAlongPath = false;
        super.startOnNewPath(entity);
    }
}
