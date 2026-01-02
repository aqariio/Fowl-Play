package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.ai.navigation.BirdRandomPos;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class SetWaterWalkTarget<E extends BirdEntity> extends SpeedModifiableBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    protected CylindricalRadius radius = new CylindricalRadius(32, 16);
    protected BiPredicate<E, Vec3> positionPredicate = (entity, pos) -> true;

    public SetWaterWalkTarget<E> radius(int radius) {
        return this.radius(radius, radius);
    }

    public SetWaterWalkTarget<E> radius(int xz, int y) {
        this.radius = new CylindricalRadius(xz, y);

        return this;
    }

    public SetWaterWalkTarget<E> walkTargetPredicate(BiPredicate<E, Vec3> predicate) {
        this.positionPredicate = predicate;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected void start(E entity) {
        Vec3 targetPos = this.getTargetPos(entity);

        if(!this.positionPredicate.test(entity, targetPos)) {
            targetPos = null;
        }

        if(targetPos != null) {
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, this.speedModifier.apply(entity, targetPos), 0));
        }
        else {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        }
    }

    @Nullable
    protected Vec3 getTargetPos(E entity) {
        return BirdRandomPos.getWater(entity, this.radius);
    }
}