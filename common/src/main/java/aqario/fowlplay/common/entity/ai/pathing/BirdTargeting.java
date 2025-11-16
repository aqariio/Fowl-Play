package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.common.util.TargetingUtil;
import net.minecraft.entity.ai.FuzzyPositions;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToDoubleFunction;

/**
 * Similar to {@link FuzzyTargeting} but specialized for birds.
 */
public class BirdTargeting {
    @Nullable
    public static Vec3d findWaterOrGround(BirdEntity entity, CylindricalRadius waterRange, CylindricalRadius groundRange) {
        Vec3d pos = findWater(entity, waterRange);
        return pos != null ? pos : findGround(entity, groundRange);
    }

    @Nullable
    public static Vec3d findWater(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtil.validateWater(entity, ExtendedRandomPos.withinRangePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return pos != null ? Vec3d.ofBottomCenter(pos) : null;
    }

    @Nullable
    public static Vec3d findNonAir(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtil.validateNonAir(entity, ExtendedRandomPos.withinRangePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return pos != null ? Vec3d.ofBottomCenter(pos) : null;
    }

    @Nullable
    public static Vec3d findPerchOrGround(BirdEntity entity, CylindricalRadius perchRange, CylindricalRadius groundRange) {
        Vec3d pos = findPerch(entity, perchRange);
        return pos != null ? pos : findGround(entity, groundRange);
    }

    @Nullable
    public static Vec3d findGround(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtil.validateGround(entity, ExtendedRandomPos.withinRangePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return pos != null ? Vec3d.ofBottomCenter(pos) : null;
    }

    @Nullable
    public static Vec3d findPerch(BirdEntity entity, CylindricalRadius range) {
        return findPerch(entity, range, pos -> 0);
    }

    @Nullable
    public static Vec3d findPerch(BirdEntity entity, CylindricalRadius range, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = NavigationConditions.isPositionTargetInRange(entity, range.horizontal());
        Vec3d direction = entity.getRotationVec(1);
        return FuzzyPositions.guessBest(() -> {
            BlockPos pos = ExtendedRandomPos.withinAngleSlicePreferNear(
                entity.getRandom(),
                range.horizontal(),
                range.vertical(),
                0,
                direction,
                Math.PI * 3 / 2
            );
            BlockPos shiftedPos = TargetingUtil.towardTarget(entity, range.horizontal(), posTargetInRange, pos);
            return shiftedPos != null ? TargetingUtil.validatePerch(entity, shiftedPos) : null;
        }, scorer);
    }

    @Nullable
    public static Vec3d findAir(FlyingBirdEntity entity, CylindricalRadius range) {
        return findAir(entity, range, entity::getFlyingPathfindingFavor);
    }

    @Nullable
    public static Vec3d findAir(FlyingBirdEntity entity, CylindricalRadius range, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = NavigationConditions.isPositionTargetInRange(entity, range.horizontal());
        // the entity's path should be in the same direction as its look vector
        Vec3d direction = entity.getRotationVec(1);
        // the angle within which the target position should be in regard to the entity's look vector
        final double angle = 15.0;
        return FuzzyPositions.guessBest(() -> {
            BlockPos pos = ExtendedRandomPos.withinAngleSlicePreferFar(
                entity.getRandom(),
                range.horizontal(),
                range.vertical(),
                0,
                direction,
                angle * (Math.PI / 180)
            );
            BlockPos shiftedPos = TargetingUtil.towardTarget(entity, range.horizontal(), posTargetInRange, pos);
            return shiftedPos != null ? TargetingUtil.validateAny(entity, shiftedPos) : null;
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
            BlockPos blockPos = ExtendedRandomPos.withinAngleSlicePreferNear(
                entity.getRandom(),
                horizontalRange,
                verticalRange,
                0,
                direction,
                Math.PI / 2
            );
            BlockPos blockPos2 = TargetingUtil.towardTarget(entity, horizontalRange, posTargetInRange, blockPos);
            return blockPos2 == null ? null : TargetingUtil.validateAny(entity, blockPos2);
        });
    }
}
