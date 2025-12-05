package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.ai.navigation.BirdRandomPos;
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
import java.util.function.Predicate;

public class SetNonAirWalkTarget<E extends BirdEntity> extends SpeedModifiableBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    protected Predicate<E> avoidWaterPredicate = entity -> true;
    protected CylindricalRadius radius = new CylindricalRadius(32, 16);
    protected BiPredicate<E, Vec3> positionPredicate = (entity, pos) -> true;

    public SetNonAirWalkTarget<E> radius(int radius) {
        return this.radius(radius, radius);
    }

    public SetNonAirWalkTarget<E> radius(int xz, int y) {
        this.radius = new CylindricalRadius(xz, y);

        return this;
    }

    public SetNonAirWalkTarget<E> walkTargetPredicate(BiPredicate<E, Vec3> predicate) {
        this.positionPredicate = predicate;

        return this;
    }

    public SetNonAirWalkTarget<E> dontAvoidWater() {
        return this.avoidWaterWhen(entity -> false);
    }

    public SetNonAirWalkTarget<E> avoidWaterWhen(Predicate<E> predicate) {
        this.avoidWaterPredicate = predicate;

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
        if(this.avoidWaterPredicate.test(entity)) {
            return BirdRandomPos.getGround(entity, this.radius);
        }
        return BirdRandomPos.getNonAir(entity, this.radius);
    }
}