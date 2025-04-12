package aqario.fowlplay.common.entity.ai.pathing;

import net.minecraft.entity.ai.FuzzyPositions;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToDoubleFunction;

public class FlightTargeting {
    /**
     * Paths to a random reachable position with positive path-finding favorability.
     *
     * @param entity          the entity doing the pathing
     * @param horizontalRange the horizontal pathing range (how far the point can be from the entity's starting position on the X or Z range)
     * @param verticalRange   the vertical pathing range (how far the point can be from the entity's starting position on the Y range)
     * @return chosen position or null if none could be found
     */
    @Nullable
    public static Vec3d find(PathAwareEntity entity, int horizontalRange, int verticalRange) {
        return find(entity, horizontalRange, verticalRange, entity::getPathfindingFavor);
    }

    /**
     * Paths to a random reachable position with positive path-finding favorability computed by a given function.
     *
     * @param scorer          function to compute the path-finding favorability of a candidate position
     * @param verticalRange   the vertical pathing range (how far the point can be from the entity's starting position on the Y range)
     * @param horizontalRange the horizontal pathing range (how far the point can be from the entity's starting position on the X or Z range)
     * @param entity          the entity doing the pathing
     * @return the chosen position or null if none could be found
     */
    @Nullable
    public static Vec3d find(PathAwareEntity entity, int horizontalRange, int verticalRange, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        // the entity's path should be in the same direction as its look vector
        Vec3d direction = entity.getRotationVec(1);
        return FuzzyPositions.guessBest(() -> {
            BlockPos blockPos = FuzzyPositions.localFuzz(entity.getRandom(), horizontalRange, verticalRange, 0, direction.x, direction.z, 5.0F * (Math.PI / 180));
            if (blockPos == null) {
                return null;
            }
            BlockPos blockPos2 = towardTarget(entity, horizontalRange, posTargetInRange, blockPos);
            return blockPos2 == null ? null : validate(entity, blockPos2);
        }, scorer);
    }

    /**
     * Paths to a random reachable position leading towards a given end-point.
     *
     * @param end             the position to path towards
     * @param verticalRange   the vertical pathing range (how far the point can be from the entity's starting position on the Y range)
     * @param horizontalRange the horizontal pathing range (how far the point can be from the entity's starting position on the X or Z range)
     * @return the chosen position or null if none could be found
     */
    @Nullable
    public static Vec3d findTo(PathAwareEntity entity, int horizontalRange, int verticalRange, Vec3d end) {
        Vec3d vec3d = end.subtract(entity.getX(), entity.getY(), entity.getZ());
        boolean bl = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        return findValid(entity, horizontalRange, verticalRange, vec3d, bl);
    }

    /**
     * Paths to a random reachable position leading away from a given starting point.
     *
     * @param entity          the entity doing the pathing
     * @param verticalRange   the vertical pathing range (how far the point can be from the entity's starting position on the Y range)
     * @param horizontalRange the horizontal pathing range (how far the point can be from the entity's starting position on the X or Z range)
     * @param start           the position to path away from
     * @return the chosen position or null if none could be found
     */
    @Nullable
    public static Vec3d findFrom(PathAwareEntity entity, int horizontalRange, int verticalRange, Vec3d start) {
        Vec3d vec3d = entity.getPos().subtract(start);
        boolean bl = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        return findValid(entity, horizontalRange, verticalRange, vec3d, bl);
    }

    @Nullable
    private static Vec3d findValid(PathAwareEntity entity, int horizontalRange, int verticalRange, Vec3d direction, boolean posTargetInRange) {
        return FuzzyPositions.guessBestPathTarget(entity, () -> {
            BlockPos blockPos = FuzzyPositions.localFuzz(entity.getRandom(), horizontalRange, verticalRange, 0, direction.x, direction.z, (float) (Math.PI / 2));
            if (blockPos == null) {
                return null;
            }
            BlockPos blockPos2 = towardTarget(entity, horizontalRange, posTargetInRange, blockPos);
            return blockPos2 == null ? null : validate(entity, blockPos2);
        });
    }

    /**
     * Checks whether a given position is a valid pathable target.
     *
     * @param pos    the candidate position
     * @param entity the entity doing the pathing
     * @return the input position, or null if validation failed
     */
    @Nullable
    public static BlockPos validate(PathAwareEntity entity, BlockPos pos) {
        pos = FuzzyPositions.upWhile(pos, entity.getWorld().getTopY(), currentPos -> NavigationConditions.isSolidAt(entity, currentPos));
        return !NavigationConditions.isWaterAt(entity, pos) && !NavigationConditions.hasPathfindingPenalty(entity, pos) ? pos : null;
    }

    /**
     * Paths to a random reachable position approaching an entity's chosen {@link net.minecraft.entity.mob.MobEntity#getPositionTarget() position target}.
     *
     * @param entity          the entity doing the pathing
     * @param horizontalRange the horizontal pathing range (how far the point can be from the entity's starting position on the X or Z range)
     * @return the chosen position or null if none could be found
     */
    @Nullable
    public static BlockPos towardTarget(PathAwareEntity entity, int horizontalRange, boolean posTargetInRange, BlockPos relativeInRangePos) {
        BlockPos blockPos = FuzzyPositions.towardTarget(entity, horizontalRange, entity.getRandom(), relativeInRangePos);
        return !NavigationConditions.isHeightInvalid(blockPos, entity)
            && !NavigationConditions.isPositionTargetOutOfWalkRange(posTargetInRange, entity, blockPos)
            && !NavigationConditions.isInvalidPosition(entity.getNavigation(), blockPos)
            ? blockPos
            : null;
    }
}
