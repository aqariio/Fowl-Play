package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.ai.pathing.BirdTargeting;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class SetWaterWalkTarget<E extends BirdEntity> extends SpeedModifiableBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    protected CylindricalRadius radius = new CylindricalRadius(32, 16);
    protected BiPredicate<E, Vec3d> positionPredicate = (entity, pos) -> true;

    public SetWaterWalkTarget<E> radius(int radius) {
        return this.radius(radius, radius);
    }

    public SetWaterWalkTarget<E> radius(int xz, int y) {
        this.radius = new CylindricalRadius(xz, y);

        return this;
    }

    public SetWaterWalkTarget<E> walkTargetPredicate(BiPredicate<E, Vec3d> predicate) {
        this.positionPredicate = predicate;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected void start(E entity) {
        Vec3d targetPos = this.getTargetPos(entity);

        if(!this.positionPredicate.test(entity, targetPos)) {
            targetPos = null;
        }

        if(targetPos == null) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        }
        else {
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, this.speedModifier.apply(entity, targetPos), 0));
        }
    }

    @Nullable
    protected Vec3d getTargetPos(E entity) {
        return BirdTargeting.findWaterOrGround(entity, this.radius, this.radius);
    }
}