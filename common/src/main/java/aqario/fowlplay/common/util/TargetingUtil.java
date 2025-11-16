package aqario.fowlplay.common.util;

import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.FuzzyPositions;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class TargetingUtil {
    @Nullable
    public static BlockPos validateAny(PathAwareEntity entity, BlockPos pos) {
        BlockPos adjustedPos = FuzzyPositions.upWhile(pos, entity.getWorld().getTopY(), currentPos ->
            NavigationConditions.isSolidAt(entity, currentPos)
        );
        if(NavigationConditions.isWaterAt(entity, adjustedPos)
            || NavigationConditions.hasPathfindingPenalty(entity, adjustedPos)
            || NavigationConditions.isInvalidPosition(entity.getNavigation(), adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validateWater(PathAwareEntity entity, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, 0, currentPos ->
            NavigationConditions.isSolidAt(entity, currentPos)
                || NavigationConditions.isWaterAt(entity, currentPos)
        );
        if(!NavigationConditions.isWaterAt(entity, adjustedPos)) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validateNonAir(PathAwareEntity entity, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, 1, currentPos ->
            NavigationConditions.isSolidAt(entity, currentPos)
                || NavigationConditions.isWaterAt(entity, currentPos)
        );
        if(NavigationConditions.hasPathfindingPenalty(entity, adjustedPos)
            || !TargetingUtil.isPositionNonAir(entity, adjustedPos)
        ) {
            return null;
        }
        return entity.getWorld().isWater(adjustedPos.down()) ? adjustedPos.down() : adjustedPos;
    }

    @Nullable
    public static BlockPos validateGround(PathAwareEntity entity, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, 1, currentPos ->
            NavigationConditions.isSolidAt(entity, currentPos)
        );
        if(NavigationConditions.isWaterAt(entity, adjustedPos)
            || NavigationConditions.hasPathfindingPenalty(entity, adjustedPos)
            || !TargetingUtil.isPositionGrounded(entity, adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validatePerch(PathAwareEntity entity, BlockPos pos) {
        // TODO: this logic still needs fixing
        BlockPos adjustedPos = findSurfacePosition(entity, pos, Heightmap.Type.MOTION_BLOCKING, 1, currentPos ->
            NavigationConditions.isSolidAt(entity, currentPos)
                && !TargetingUtil.isPerch(entity, currentPos)
        );
        if(NavigationConditions.isWaterAt(entity, adjustedPos.down())
            || NavigationConditions.hasPathfindingPenalty(entity, adjustedPos)
            || !TargetingUtil.isPerch(entity, adjustedPos)
        ) {
            return null;
        }
        return entity.getWorld().getBlockState(adjustedPos).getBlock() instanceof LeavesBlock
            ? adjustedPos.down()
            : adjustedPos;
    }

    @Nullable
    public static BlockPos towardTarget(PathAwareEntity entity, int horizontalRange, boolean posTargetInRange, BlockPos relativeInRangePos) {
        BlockPos adjustedPos = FuzzyPositions.towardTarget(entity, horizontalRange, entity.getRandom(), relativeInRangePos);
        if(NavigationConditions.isHeightInvalid(adjustedPos, entity)
            || NavigationConditions.isPositionTargetOutOfWalkRange(posTargetInRange, entity, adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    public static BlockPos findSurfacePosition(
        final PathAwareEntity entity,
        final BlockPos initialPos,
        final Heightmap.Type heightmap,
        final int blocksAbove,
        final Predicate<BlockPos> predicate
    ) {
        BlockPos adjustedPos;
        // if position is above the surface, set to surface level
        if(initialPos.getY() > entity.getWorld().getTopY(heightmap, initialPos.getX(), initialPos.getZ())) {
            adjustedPos = new BlockPos(
                initialPos.getX(),
                entity.getWorld().getTopY(heightmap, initialPos.getX(), initialPos.getZ()) + blocksAbove,
                initialPos.getZ()
            );
        }
        // else, move up until we reach solid ground or water
        else {
            adjustedPos = FuzzyPositions.upWhile(initialPos, entity.getWorld().getTopY(), predicate)
                .up(blocksAbove - 1);
        }
        return adjustedPos;
    }

    public static boolean isPerch(PathAwareEntity entity, BlockPos pos) {
        return entity.getWorld().getBlockState(pos).isIn(FowlPlayBlockTags.PERCHES);
    }

    public static boolean isPositionNonAir(PathAwareEntity entity, BlockPos pos) {
        BlockPos belowPos = pos.down();
        return isFullBlockAt(entity, belowPos) || NavigationConditions.isWaterAt(entity, belowPos);
    }

    public static boolean isPositionGrounded(PathAwareEntity entity, BlockPos pos) {
        BlockPos belowPos = pos.down();
        return isFullBlockAt(entity, belowPos);
    }

    public static boolean isFullBlockAt(PathAwareEntity entity, BlockPos pos) {
        return entity.getWorld().getBlockState(pos).isOpaqueFullCube(entity.getWorld(), pos);
    }

    // angle is in radians
    public static boolean isWithinAngle(Vec3d normalVec, Vec3d targetVec, double angle) {
        normalVec = normalVec.normalize();
        targetVec = targetVec.normalize();

        // cosine of angle between the two vectors
        float cosVectorAngle = (float) normalVec.dotProduct(targetVec);

        // if cosine of the vectors' angle >= cosine of max angle the target vector is within the angle
        float cosMaxAngle = MathHelper.cos((float) angle);
        return cosVectorAngle >= cosMaxAngle;
    }

    // angle is in radians
    public static boolean isPosWithinViewAngle(PathAwareEntity entity, BlockPos pos, double angle) {
        Vec3d lookVec = entity.getRotationVec(1.0F);

        Vec3d target = Vec3d.ofCenter(pos);
        Vec3d targetVec = target.subtract(entity.getPos());

        return isWithinAngle(lookVec, targetVec, angle);
    }
}
