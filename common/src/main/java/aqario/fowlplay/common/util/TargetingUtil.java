package aqario.fowlplay.common.util;

import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class TargetingUtil {
    @Nullable
    public static BlockPos validateAny(PathfinderMob entity, BlockPos pos) {
        BlockPos adjustedPos = RandomPos.moveUpOutOfSolid(pos, entity.level().getMaxBuildHeight(), currentPos ->
            GoalUtils.isSolid(entity, currentPos)
        );
        if(GoalUtils.isWater(entity, adjustedPos)
            || GoalUtils.hasMalus(entity, adjustedPos)
            || GoalUtils.isNotStable(entity.getNavigation(), adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validateWater(PathfinderMob entity, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 0, currentPos ->
            GoalUtils.isSolid(entity, currentPos)
                || GoalUtils.isWater(entity, currentPos)
        );
        if(!GoalUtils.isWater(entity, adjustedPos)) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validateNonAir(PathfinderMob entity, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 1, currentPos ->
            GoalUtils.isSolid(entity, currentPos)
                || GoalUtils.isWater(entity, currentPos)
        );
        if(GoalUtils.hasMalus(entity, adjustedPos)
            || !TargetingUtil.isPositionNonAir(entity, adjustedPos)
        ) {
            return null;
        }
        return entity.level().isWaterAt(adjustedPos.below()) ? adjustedPos.below() : adjustedPos;
    }

    @Nullable
    public static BlockPos validateGround(PathfinderMob entity, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 1, currentPos ->
            GoalUtils.isSolid(entity, currentPos)
        );
        if(GoalUtils.isWater(entity, adjustedPos)
            || GoalUtils.hasMalus(entity, adjustedPos)
            || !TargetingUtil.isPositionGrounded(entity, adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validatePerch(PathfinderMob entity, BlockPos pos) {
        // TODO: this logic still needs fixing
        BlockPos adjustedPos = findSurfacePosition(entity, pos, Heightmap.Types.MOTION_BLOCKING, 1, currentPos ->
            GoalUtils.isSolid(entity, currentPos)
                && !TargetingUtil.isPerch(entity, currentPos)
        );
        if(GoalUtils.isWater(entity, adjustedPos.below())
            || GoalUtils.hasMalus(entity, adjustedPos)
            || !TargetingUtil.isPerch(entity, adjustedPos)
        ) {
            return null;
        }
        return entity.level().getBlockState(adjustedPos).getBlock() instanceof LeavesBlock
            ? adjustedPos.below()
            : adjustedPos;
    }

    @Nullable
    public static BlockPos towardTarget(PathfinderMob entity, int horizontalRange, boolean posTargetInRange, BlockPos relativeInRangePos) {
        BlockPos adjustedPos = RandomPos.generateRandomPosTowardDirection(entity, horizontalRange, entity.getRandom(), relativeInRangePos);
        if(GoalUtils.isOutsideLimits(adjustedPos, entity)
            || GoalUtils.isRestricted(posTargetInRange, entity, adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    public static BlockPos findSurfacePosition(
        final PathfinderMob entity,
        final BlockPos initialPos,
        final Heightmap.Types heightmap,
        final int blocksAbove,
        final Predicate<BlockPos> predicate
    ) {
        BlockPos adjustedPos;
        // if position is above the surface, set to surface level
        if(initialPos.getY() > entity.level().getHeight(heightmap, initialPos.getX(), initialPos.getZ())) {
            adjustedPos = new BlockPos(
                initialPos.getX(),
                entity.level().getHeight(heightmap, initialPos.getX(), initialPos.getZ()) + blocksAbove,
                initialPos.getZ()
            );
        }
        // else, move up until we reach solid ground or water
        else {
            adjustedPos = RandomPos.moveUpOutOfSolid(initialPos, entity.level().getMaxBuildHeight(), predicate)
                .above(blocksAbove - 1);
        }
        return adjustedPos;
    }

    public static boolean isPerch(PathfinderMob entity, BlockPos pos) {
        return entity.level().getBlockState(pos).is(FowlPlayBlockTags.PERCHES);
    }

    public static boolean isPositionNonAir(PathfinderMob entity, BlockPos pos) {
        BlockPos belowPos = pos.below();
        return isFullBlockAt(entity, belowPos) || GoalUtils.isWater(entity, belowPos);
    }

    public static boolean isPositionGrounded(PathfinderMob entity, BlockPos pos) {
        BlockPos belowPos = pos.below();
        return isFullBlockAt(entity, belowPos);
    }

    public static boolean isFullBlockAt(PathfinderMob entity, BlockPos pos) {
        return entity.level().getBlockState(pos).isSolidRender(entity.level(), pos);
    }

    // angle is in radians
    public static boolean isWithinAngle(Vec3 normalVec, Vec3 targetVec, double angle) {
        normalVec = normalVec.normalize();
        targetVec = targetVec.normalize();

        // cosine of angle between the two vectors
        float cosVectorAngle = (float) normalVec.dot(targetVec);

        // if cosine of the vectors' angle >= cosine of max angle the target vector is within the angle
        float cosMaxAngle = Mth.cos((float) angle);
        return cosVectorAngle >= cosMaxAngle;
    }

    // angle is in radians
    public static boolean isPosWithinViewAngle(PathfinderMob entity, BlockPos pos, double angle) {
        Vec3 lookVec = entity.getViewVector(1.0F);

        Vec3 target = Vec3.atCenterOf(pos);
        Vec3 targetVec = target.subtract(entity.position());

        return isWithinAngle(lookVec, targetVec, angle);
    }
}
