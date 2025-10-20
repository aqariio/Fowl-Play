package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.entity.ai.FuzzyPositions;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.object.SquareRadius;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToDoubleFunction;

public class FlightTargeting {
    @Nullable
    public static Vec3d findPerchOrGround(FlyingBirdEntity entity, SquareRadius perchRange, SquareRadius groundRange) {
        Vec3d perch = findPerch(entity, (int) perchRange.xzRadius(), (int) perchRange.yRadius());
        return perch != null ? perch : findGround(entity, (int) groundRange.xzRadius(), (int) groundRange.yRadius());
    }

    @Nullable
    public static Vec3d findGround(FlyingBirdEntity entity, int horizontalRange, int verticalRange) {
        boolean bl = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        Vec3d target = FuzzyPositions.guessBest(
            () -> NoPenaltySolidTargeting.tryMake(entity, horizontalRange, verticalRange, -2, 0, 0, Math.PI, bl),
            pos -> 0
        );
        return target == null || target.y > entity.getY() ? null : target;
    }

    @Nullable
    public static Vec3d findPerch(FlyingBirdEntity entity, int horizontalRange, int verticalRange) {
        return findPerch(entity, horizontalRange, verticalRange, pos -> 0);
    }

    @Nullable
    public static Vec3d findPerch(FlyingBirdEntity entity, int horizontalRange, int verticalRange, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        Vec3d direction = entity.getRotationVec(1);
        return FuzzyPositions.guessBest(() -> {
            BlockPos blockPos = FuzzyPositions.localFuzz(entity.getRandom(), horizontalRange, verticalRange, 0, direction.x, direction.z, Math.PI * 2);
            if(blockPos == null) {
                return null;
            }
            BlockPos blockPos2 = towardTarget(entity, horizontalRange, posTargetInRange, blockPos);
            if(blockPos2 == null) {
                return null;
            }
            return validatePerch(entity, blockPos2);
        }, scorer);
    }

    @Nullable
    public static Vec3d find(FlyingBirdEntity entity, int horizontalRange, int verticalRange) {
        return find(entity, horizontalRange, verticalRange, entity::getPathfindingFavor);
    }

    @Nullable
    public static Vec3d find(FlyingBirdEntity entity, int horizontalRange, int verticalRange, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        // the entity's path should be in the same direction as its look vector
        Vec3d direction = entity.getRotationVec(1);
        // the angle within which the target position should be in regard to the entity's look vector
        final double angle = 15.0;
        return FuzzyPositions.guessBest(() -> {
            BlockPos blockPos = FuzzyPositions.localFuzz(entity.getRandom(), horizontalRange, verticalRange, 0, direction.x, direction.z, angle * (Math.PI / 180));
            if(blockPos == null) {
                return null;
            }
            BlockPos blockPos2 = towardTarget(entity, horizontalRange, posTargetInRange, blockPos);
            if(blockPos2 == null) {
                return null;
            }
            return Birds.isPosWithinViewAngle(entity, blockPos2, angle * (Math.PI / 180)) ? validate(entity, blockPos2) : null;
        }, scorer);
    }

    @Nullable
    public static Vec3d findTo(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3d end) {
        Vec3d vec3d = end.subtract(entity.getX(), entity.getY(), entity.getZ());
        boolean bl = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        return findValid(entity, horizontalRange, verticalRange, vec3d, bl);
    }

    @Nullable
    public static Vec3d findFrom(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3d start) {
        Vec3d vec3d = entity.getPos().subtract(start);
        boolean bl = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        return findValid(entity, horizontalRange, verticalRange, vec3d, bl);
    }

    @Nullable
    private static Vec3d findValid(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3d direction, boolean posTargetInRange) {
        return FuzzyPositions.guessBestPathTarget(entity, () -> {
            BlockPos blockPos = FuzzyPositions.localFuzz(entity.getRandom(), horizontalRange, verticalRange, 0, direction.x, direction.z, (float) (Math.PI / 2));
            if(blockPos == null) {
                return null;
            }
            BlockPos blockPos2 = towardTarget(entity, horizontalRange, posTargetInRange, blockPos);
            return blockPos2 == null ? null : validate(entity, blockPos2);
        });
    }

    @Nullable
    public static BlockPos validate(FlyingBirdEntity entity, BlockPos pos) {
        pos = FuzzyPositions.upWhile(pos, entity.getWorld().getTopY(), currentPos -> NavigationConditions.isSolidAt(entity, currentPos));
        return !NavigationConditions.isWaterAt(entity, pos) && !NavigationConditions.hasPathfindingPenalty(entity, pos) ? pos : null;
    }

    @Nullable
    public static BlockPos validatePerch(FlyingBirdEntity entity, BlockPos pos) {
        pos = FuzzyPositions.upWhile(pos, entity.getWorld().getTopY(), currentPos ->
            NavigationConditions.isSolidAt(entity, currentPos) && !entity.getWorld().getBlockState(currentPos).isIn(FowlPlayBlockTags.PERCHES));
        return !NavigationConditions.isWaterAt(entity, pos)
            && !NavigationConditions.hasPathfindingPenalty(entity, pos)
            && entity.getWorld().getBlockState(pos).isIn(FowlPlayBlockTags.PERCHES)
            ? pos
            : null;
    }

    @Nullable
    public static BlockPos towardTarget(FlyingBirdEntity entity, int horizontalRange, boolean posTargetInRange, BlockPos relativeInRangePos) {
        BlockPos blockPos = FuzzyPositions.towardTarget(entity, horizontalRange, entity.getRandom(), relativeInRangePos);
        return !NavigationConditions.isHeightInvalid(blockPos, entity)
            && !NavigationConditions.isPositionTargetOutOfWalkRange(posTargetInRange, entity, blockPos)
            && !NavigationConditions.isInvalidPosition(entity.getNavigation(), blockPos)
            ? blockPos
            : null;
    }
}
