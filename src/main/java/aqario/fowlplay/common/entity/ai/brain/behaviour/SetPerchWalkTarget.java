package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.ai.navigation.BirdRandomPos;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetPerchWalkTarget<E extends FlyingBirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    public static final CylindricalRadius RANGE = new CylindricalRadius(32, 32);

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected void start(E entity) {
        Brain<?> brain = entity.getBrain();
        Vec3 target = BirdRandomPos.getPerch(entity, RANGE);
        if(target != null) {
            BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(target, 1.0f, 0));
        }
        else {
            BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
        }
    }
}
