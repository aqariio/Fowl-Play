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
    public static BlockPos tryFindAir(PathfinderMob entity, CylindricalRadius range, BlockPos pos) {
        BlockPos adjustedPos = RandomPos.generateRandomPosTowardDirection(
            entity, range.horizontal(), entity.getRandom(), pos
        );
        adjustedPos = RandomPos.moveUpOutOfSolid(adjustedPos, entity.level().getMaxBuildHeight(), currentPos ->
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
    public static BlockPos tryFindWater(PathfinderMob entity, CylindricalRadius range, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, range, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 0, currentPos ->
            GoalUtils.isSolid(entity, currentPos)
                || GoalUtils.isWater(entity, currentPos)
        );
        if(!GoalUtils.isWater(entity, adjustedPos)) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos tryFindNonAir(PathfinderMob entity, CylindricalRadius range, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, range, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 1, currentPos ->
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
    public static BlockPos tryFindGround(PathfinderMob entity, CylindricalRadius range, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, range, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 1, currentPos ->
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
    public static BlockPos tryFindPerch(PathfinderMob entity, CylindricalRadius range, BlockPos pos) {
        // TODO: this logic still needs fixing
        BlockPos adjustedPos = findSurfacePosition(entity, pos, range, Heightmap.Types.MOTION_BLOCKING, 1, currentPos ->
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

    public static BlockPos findSurfacePosition(
        final PathfinderMob entity,
        final BlockPos initialPos,
        CylindricalRadius range,
        final Heightmap.Types heightmap,
        final int blocksAbove,
        final Predicate<BlockPos> predicate
    ) {
        BlockPos adjustedPos = RandomPos.generateRandomPosTowardDirection(
            entity, range.horizontal(), entity.getRandom(), initialPos
        );
        // if position is above the surface, set to surface level
        if(adjustedPos.getY() > entity.level().getHeight(heightmap, adjustedPos.getX(), adjustedPos.getZ())) {
            adjustedPos = new BlockPos(
                adjustedPos.getX(),
                entity.level().getHeight(heightmap, adjustedPos.getX(), adjustedPos.getZ()) + blocksAbove,
                adjustedPos.getZ()
            );
        }
        // else, move up until we reach solid ground or water
        else {
            adjustedPos = RandomPos.moveUpOutOfSolid(adjustedPos, entity.level().getMaxBuildHeight(), predicate)
                .above(blocksAbove - 1);
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validateBlockPos(PathfinderMob entity, @Nullable BlockPos pos, CylindricalRadius range) {
        if(pos == null) {
            return null;
        }
        if(GoalUtils.isOutsideLimits(pos, entity)
            || GoalUtils.isRestricted(GoalUtils.mobRestricted(entity, range.horizontal()), entity, pos)
        ) {
            return null;
        }
        return pos;
    }

    @Nullable
    public static Vec3 validatePos(PathfinderMob entity, BlockPos pos, CylindricalRadius range) {
        BlockPos validPos = validateBlockPos(entity, pos, range);
        return validPos != null ? validPos.getBottomCenter() : null;
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
