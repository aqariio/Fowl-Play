package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.tags.FowlPlayEntityTypeTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ReportingTaskControl;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FlyTask {
    public static TaskControl<PathAwareEntity> create(float speed, int horizontalRange, int verticalRange) {
        return create(speed, (entity) -> findFlyTargetPos(entity, horizontalRange, verticalRange), (entity) -> true);
    }

    @Nullable
    private static Vec3d findFlyTargetPos(PathAwareEntity entity, int horizontalRange, int verticalRange) {
        Vec3d vec3d = entity.getRotationVec(0.0F);
        if (entity.getType().isIn(FowlPlayEntityTypeTags.PASSERINES) && entity.getRandom().nextFloat() < 0.8F) {
            return getTreePos(entity);
        }
        return NoPenaltySolidTargeting.find(entity, horizontalRange, verticalRange, -2, vec3d.x, vec3d.z, 1.5707963705062866);
    }

    private static ReportingTaskControl<PathAwareEntity> create(float speed, Function<PathAwareEntity, Vec3d> targetGetter, Predicate<PathAwareEntity> predicate) {
        return TaskBuilder.task((instance) -> instance.group(instance.absentMemory(MemoryModuleType.WALK_TARGET)).apply(instance, (memoryAccessor) -> (world, entity, time) -> {
            if (!predicate.test(entity)) {
                return false;
            }
            else {
                Optional<Vec3d> optional = Optional.ofNullable(targetGetter.apply(entity));
                memoryAccessor.remember(optional.map((vec3d) -> new WalkTarget(vec3d, speed, 0)));
                return true;
            }
        }));
    }

    @Nullable
    private static Vec3d getTreePos(PathAwareEntity entity) {
        BlockPos entityPos = entity.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();

        for (BlockPos targetPos : BlockPos.iterate(
            MathHelper.floor(entity.getX() - 12.0),
            MathHelper.floor(entity.getY() - 18.0),
            MathHelper.floor(entity.getZ() - 12.0),
            MathHelper.floor(entity.getX() + 12.0),
            MathHelper.floor(entity.getY() + 18.0),
            MathHelper.floor(entity.getZ() + 12.0)
        )) {
            if (!entityPos.equals(targetPos)) {
                BlockState blockState = entity.getWorld().getBlockState(mutable2.set(targetPos, Direction.DOWN));
                boolean bl = blockState.isIn(BlockTags.LEAVES) || blockState.isIn(BlockTags.LOGS);
                if (bl && entity.getWorld().isAir(targetPos) && entity.getWorld().isAir(mutable.set(targetPos, Direction.UP))) {
                    return Vec3d.ofBottomCenter(targetPos);
                }
            }
        }

        return null;
    }
}
