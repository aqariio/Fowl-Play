package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.pathing.BirdTargeting;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetRandomFlightTarget<E extends FlyingBirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    private static final CylindricalRadius RANGE = new CylindricalRadius(64, 16);

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected void start(E entity) {
        Brain<?> brain = entity.getBrain();
        Vec3d target = BirdTargeting.findAir(entity, RANGE);
        if(target != null) {
            BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(target, 1.0f, 0));
        }
        else {
            BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
        }
    }
}
