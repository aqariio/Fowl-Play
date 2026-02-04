package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.archaeopteryx.common.ai.behaviour.ExtendedBehaviour;
import aqario.archaeopteryx.core.util.MemoryList;
import aqario.fowlplay.common.entity.ai.navigation.BirdRandomPos;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.CylindricalRadius;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

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
        Vec3 target = BirdRandomPos.getPerch(entity, RANGE);
        if(target != null) {
            entity.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(target, 1.0f, 0));
        }
        else {
            entity.clearMemory(MemoryModuleType.WALK_TARGET);
        }
    }
}
