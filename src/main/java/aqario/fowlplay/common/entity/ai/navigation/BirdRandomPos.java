package aqario.fowlplay.common.entity.ai.navigation;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.common.util.TargetingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link LandRandomPos} but specialized for birds.
 */
public class BirdRandomPos {
    @Nullable
    public static Vec3 getWater(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtils.tryFindWater(entity, range, ExtendedRandomPos.generatePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return TargetingUtils.validatePos(entity, pos, range);
    }

    @Nullable
    public static Vec3 getNonAir(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtils.tryFindNonAir(entity, range, ExtendedRandomPos.generatePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return TargetingUtils.validatePos(entity, pos, range);
    }

    @Nullable
    public static Vec3 getGround(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtils.tryFindGround(entity, range, ExtendedRandomPos.generatePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return TargetingUtils.validatePos(entity, pos, range);
    }

    @Nullable
    public static Vec3 getPerch(BirdEntity entity, CylindricalRadius range) {
        BlockPos pos = TargetingUtils.tryFindPerch(entity, range, ExtendedRandomPos.generatePreferNear(
            entity.getRandom(),
            range.horizontal(),
            range.vertical()
        ));
        return TargetingUtils.validatePos(entity, pos, range);
    }

    @Nullable
    public static Vec3 getAir(FlyingBirdEntity entity, CylindricalRadius range) {
        // the entity's path should be in the same direction as its look vector
        Vec3 direction = entity.getViewVector(1);
        // the angle within which the target position should be in regard to the entity's look vector
        final double angle = 15.0;
        BlockPos pos = TargetingUtils.tryFindAir(entity, range, ExtendedRandomPos.generateWithinAnglePreferFar(
            entity.getRandom(),
            range.horizontal(),
            range.vertical(),
            0,
            direction,
            Math.toRadians(angle)
        ));
        return TargetingUtils.validatePos(entity, pos, range);
    }
}
