package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.pathing.FlightTargeting;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class TargetlessFlyTask {
    private static final int HORIZONTAL_RANGE = 24;
    private static final int VERTICAL_RANGE = 16;

    public static SingleTickBehaviour<FlyingBirdEntity> create() {
        return create((entity) -> FlightTargeting.find(entity, HORIZONTAL_RANGE, VERTICAL_RANGE));
    }

    public static SingleTickBehaviour<FlyingBirdEntity> perch() {
        return create(TargetlessFlyTask::findPerchPos);
    }

    private static SingleTickBehaviour<FlyingBirdEntity> create(Function<FlyingBirdEntity, Vec3d> targetGetter) {
        return new SingleTickBehaviour<>(
            MemoryList.create(1)
                .absent(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                Optional<Vec3d> target = Optional.ofNullable(targetGetter.apply(bird));
                BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, target.map((vec3d) -> new WalkTarget(vec3d, 1.0f, 0)).orElse(null));
                return true;
            });
    }

    @Nullable
    private static Vec3d findPerchPos(FlyingBirdEntity entity) {
        BlockPos entityPos = entity.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();

        for(BlockPos targetPos : BlockPos.iterate(
            MathHelper.floor(entity.getX() - 12.0),
            MathHelper.floor(entity.getY() + 4.0),
            MathHelper.floor(entity.getZ() - 12.0),
            MathHelper.floor(entity.getX() + 12.0),
            MathHelper.floor(entity.getY() + 20.0),
            MathHelper.floor(entity.getZ() + 12.0)
        )) {
            if(!entityPos.equals(targetPos)) {
                BlockState state = entity.getWorld().getBlockState(mutable2.set(targetPos, Direction.DOWN));
                boolean validBlock = state.isIn(FowlPlayBlockTags.PERCHES);
                if(validBlock && entity.getWorld().isAir(targetPos)
                    && (entity.getBoundingBox().getYLength() <= 1
                    || entity.getWorld().isAir(mutable.set(targetPos, Direction.UP)))
                ) {
                    return Vec3d.ofBottomCenter(targetPos);
                }
            }
        }

        return FlightTargeting.find(entity, HORIZONTAL_RANGE, VERTICAL_RANGE);
    }
}
