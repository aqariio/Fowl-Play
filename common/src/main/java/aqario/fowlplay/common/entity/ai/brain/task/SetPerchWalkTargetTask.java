package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.pathing.FlightTargeting;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetPerchWalkTargetTask<E extends FlyingBirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    public static final SquareRadius PERCH_RANGE = new SquareRadius(32, 32);
    public static final SquareRadius GROUND_RANGE = new SquareRadius(8, 64);

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected void start(E entity) {
        Brain<?> brain = entity.getBrain();
        Vec3d target = FlightTargeting.findPerchOrGround(entity, PERCH_RANGE, GROUND_RANGE);
        if(target == null) {
            BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
        }
        else {
            BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(target, 1.0f, 0));
        }
    }
}
