package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;

public class BirdMoveToWalkTarget<E extends BirdEntity> extends MoveToWalkTarget<E> {
    @Override
    protected boolean attemptNewPath(E entity, WalkTarget walkTarget, boolean reachedCurrentTarget) {
        // TODO: patch for flying birds so there's less path recalculation
        return super.attemptNewPath(entity, walkTarget, reachedCurrentTarget);
    }
}
