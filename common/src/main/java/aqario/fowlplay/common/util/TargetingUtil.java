package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
    public static BlockPos tryFindAir(FlyingBirdEntity entity, CylindricalRadius range, BlockPos pos) {
        BlockPos adjustedPos = RandomPos.generateRandomPosTowardDirection(
            entity, range.horizontal(), entity.getRandom(), pos
        );
        adjustedPos = shiftPosTowardsFlyHeightRange(entity, adjustedPos);
        int surfaceY = entity.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, adjustedPos.getX(), adjustedPos.getZ());
        if(adjustedPos.getY() < surfaceY && entity.getY() >= surfaceY) {
            adjustedPos = adjustedPos.atY(
                surfaceY + 12
            );
        }
        else if(entity.getY() < surfaceY) {
            adjustedPos = RandomPos.moveUpOutOfSolid(adjustedPos, entity.level().getMaxBuildHeight(), currentPos ->
                GoalUtils.isSolid(entity, currentPos) || GoalUtils.isWater(entity, currentPos)
            );
        }
        if(GoalUtils.isSolid(entity, adjustedPos)
            || GoalUtils.isWater(entity, adjustedPos)
            || GoalUtils.hasMalus(entity, adjustedPos)
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
        BlockPos adjustedPos = findSurfacePosition(entity, pos, range, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 0, currentPos ->
            GoalUtils.isSolid(entity, currentPos)
                || GoalUtils.isWater(entity, currentPos)
        );
        if(GoalUtils.hasMalus(entity, adjustedPos)
            || !TargetingUtil.isPositionNonAir(entity, adjustedPos)
        ) {
            return null;
        }
        return entity.level().isWaterAt(adjustedPos) ? adjustedPos : adjustedPos.above();
    }

    @Nullable
    public static BlockPos tryFindGround(PathfinderMob entity, CylindricalRadius range, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, range, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 1, currentPos ->
            GoalUtils.isSolid(entity, currentPos)
        );
        if(GoalUtils.isWater(entity, adjustedPos)
            || GoalUtils.hasMalus(entity, adjustedPos)
            || !TargetingUtil.isPositionGrounded(entity, adjustedPos.below())
        ) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos tryFindPerch(PathfinderMob entity, CylindricalRadius range, BlockPos pos) {
        BlockPos adjustedPos = findSurfacePosition(entity, pos, range, Heightmap.Types.MOTION_BLOCKING, 0, currentPos -> {
            boolean isLeaves = entity.level().getBlockState(currentPos.below()).getBlock() instanceof LeavesBlock;
            boolean isValidLeavesPerch = isLeaves
                && !entity.level().getBlockState(currentPos.below(2)).isAir();
            boolean isValidPerch = isPerch(entity, currentPos.below())
                && entity.level().getBlockState(currentPos).isAir();
            if(isLeaves && !isValidLeavesPerch) {
                return true;
            }
            if(isValidLeavesPerch) {
                return false;
            }
            return !isValidPerch;
        });
        if(!TargetingUtil.isPerch(entity, adjustedPos)
            || GoalUtils.isWater(entity, adjustedPos)
            || GoalUtils.hasMalus(entity, adjustedPos)
        ) {
            return null;
        }
        return entity.level().getBlockState(adjustedPos).getBlock() instanceof LeavesBlock
            ? adjustedPos
            : adjustedPos.above();
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
        int surfaceY = entity.level().getHeight(heightmap, adjustedPos.getX(), adjustedPos.getZ());
        // if position is above the surface, set to surface level, and vertically offset final position by blocksAbove
        if(adjustedPos.getY() >= surfaceY) {
            adjustedPos = adjustedPos.atY(
                surfaceY + blocksAbove - 1
            );
        }
        // else, move up based on provided predicate, and vertically offset final position by blocksAbove
        else {
            adjustedPos = RandomPos.moveUpOutOfSolid(adjustedPos, surfaceY, predicate)
                .relative(Direction.Axis.Y, blocksAbove - 1);
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
    public static Vec3 validatePos(PathfinderMob entity, @Nullable BlockPos pos, CylindricalRadius range) {
        BlockPos validPos = validateBlockPos(entity, pos, range);
        return validPos != null ? validPos.getBottomCenter() : null;
    }

    public static BlockPos shiftPosTowardsFlyHeightRange(FlyingBirdEntity bird, BlockPos pos) {
        int posY = pos.getY();
        RandomSource random = bird.getRandom();
        Pair<Integer, Integer> flyHeightRange = bird.getFlyHeightRange();
        int lower = flyHeightRange.getFirst();
        int upper = flyHeightRange.getSecond();
        if(posY < lower) {
            return pos.above(Math.min(random.nextIntBetweenInclusive(5, 10), lower - posY));
        }
        if(posY > upper) {
            return pos.below(Math.min(random.nextIntBetweenInclusive(5, 10), posY - upper));
        }
        return pos;
    }

    public static boolean isPerch(PathfinderMob entity, BlockPos pos) {
        return entity.level().getBlockState(pos).is(FowlPlayBlockTags.PERCHES);
    }

    public static boolean isPositionNonAir(PathfinderMob entity, BlockPos pos) {
        return isFullBlockAt(entity, pos) || GoalUtils.isWater(entity, pos);
    }

    public static boolean isPositionGrounded(PathfinderMob entity, BlockPos pos) {
        return isFullBlockAt(entity, pos);
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
