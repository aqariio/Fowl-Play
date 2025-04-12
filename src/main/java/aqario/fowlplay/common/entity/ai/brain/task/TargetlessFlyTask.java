package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.pathing.FlightTargeting;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class TargetlessFlyTask {
    public static Task<FlyingBirdEntity> create(float speed, int horizontalRange, int verticalRange) {
        return create(speed, (entity) -> FlightTargeting.find(entity, horizontalRange, verticalRange), (entity) -> true);
    }

    public static Task<FlyingBirdEntity> perch(float speed) {
        return create(speed, TargetlessFlyTask::findPerchPos, (entity) -> true);
    }

    private static SingleTickTask<FlyingBirdEntity> create(float speed, Function<FlyingBirdEntity, Vec3d> targetGetter, Predicate<FlyingBirdEntity> predicate) {
        return TaskTriggerer.task((instance) -> instance.group(instance.queryMemoryAbsent(MemoryModuleType.WALK_TARGET)).apply(instance, (walkTarget) -> (world, entity, time) -> {
            if (!predicate.test(entity)) {
                return false;
            }
            Optional<Vec3d> target = Optional.ofNullable(targetGetter.apply(entity));
            walkTarget.remember(target.map((vec3d) -> new WalkTarget(vec3d, speed, 0)));
            return true;
        }));
    }

    @Nullable
    private static Vec3d findPerchPos(FlyingBirdEntity entity) {
        BlockPos entityPos = entity.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();

        for (BlockPos targetPos : BlockPos.iterate(
            MathHelper.floor(entity.getX() - 12.0),
            MathHelper.floor(entity.getY() + 4.0),
            MathHelper.floor(entity.getZ() - 12.0),
            MathHelper.floor(entity.getX() + 12.0),
            MathHelper.floor(entity.getY() + 20.0),
            MathHelper.floor(entity.getZ() + 12.0)
        )) {
            if (!entityPos.equals(targetPos)) {
                BlockState state = entity.getWorld().getBlockState(mutable2.set(targetPos, Direction.DOWN));
                boolean validBlock = state.isIn(FowlPlayBlockTags.PERCHES);
                if (validBlock && entity.getWorld().isAir(targetPos)
                    && (entity.getBoundingBox().getYLength() <= 1
                    || entity.getWorld().isAir(mutable.set(targetPos, Direction.UP)))
                ) {
                    return Vec3d.ofBottomCenter(targetPos);
                }
            }
        }

        return FlightTargeting.find(entity, 32, 16);
    }
}
