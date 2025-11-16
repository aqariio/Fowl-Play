package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.common.util.TargetingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToDoubleFunction;

/**
 * Similar to {@link LandRandomPos} but specialized for birds.
 */
public class BirdTargeting {
    @Nullable
    public static Vec3 findWaterOrGround(BirdEntity entity, CylindricalRadius waterRange, CylindricalRadius groundRange) {
        Vec3 pos = findWater(entity, waterRange);
        return pos != null ? pos : findGround(entity, groundRange);
    }

    @Nullable
    public static Vec3 findWater(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtil.validateWater(entity, ExtendedRandomPos.withinRangePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return pos != null ? Vec3.atBottomCenterOf(pos) : null;
    }

    @Nullable
    public static Vec3 findNonAir(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtil.validateNonAir(entity, ExtendedRandomPos.withinRangePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return pos != null ? Vec3.atBottomCenterOf(pos) : null;
    }

    @Nullable
    public static Vec3 findPerchOrGround(BirdEntity entity, CylindricalRadius perchRange, CylindricalRadius groundRange) {
        Vec3 pos = findPerch(entity, perchRange);
        return pos != null ? pos : findGround(entity, groundRange);
    }

    @Nullable
    public static Vec3 findGround(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtil.validateGround(entity, ExtendedRandomPos.withinRangePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return pos != null ? Vec3.atBottomCenterOf(pos) : null;
    }

    @Nullable
    public static Vec3 findPerch(BirdEntity entity, CylindricalRadius range) {
        return findPerch(entity, range, pos -> 0);
    }

    @Nullable
    public static Vec3 findPerch(BirdEntity entity, CylindricalRadius range, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = GoalUtils.mobRestricted(entity, range.horizontal());
        Vec3 direction = entity.getViewVector(1);
        return RandomPos.generateRandomPos(() -> {
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
    public static Vec3 findAir(FlyingBirdEntity entity, CylindricalRadius range) {
        return findAir(entity, range, entity::getFlyingPathfindingFavor);
    }

    @Nullable
    public static Vec3 findAir(FlyingBirdEntity entity, CylindricalRadius range, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = GoalUtils.mobRestricted(entity, range.horizontal());
        // the entity's path should be in the same direction as its look vector
        Vec3 direction = entity.getViewVector(1);
        // the angle within which the target position should be in regard to the entity's look vector
        final double angle = 15.0;
        return RandomPos.generateRandomPos(() -> {
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
    public static Vec3 findTo(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3 end) {
        Vec3 vec3d = end.subtract(entity.getX(), entity.getY(), entity.getZ());
        boolean bl = GoalUtils.mobRestricted(entity, horizontalRange);
        return findValid(entity, horizontalRange, verticalRange, vec3d, bl);
    }

    @Nullable
    public static Vec3 findFrom(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3 start) {
        Vec3 vec3d = entity.position().subtract(start);
        boolean bl = GoalUtils.mobRestricted(entity, horizontalRange);
        return findValid(entity, horizontalRange, verticalRange, vec3d, bl);
    }

    @Nullable
    private static Vec3 findValid(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3 direction, boolean posTargetInRange) {
        return RandomPos.generateRandomPos(entity, () -> {
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
