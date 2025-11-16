package aqario.fowlplay.common.entity.ai.pathing;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.function.DoubleUnaryOperator;

public class ExtendedRandomPos {
    public static BlockPos withinRangePreferFar(
        final Random random,
        final int horizontalRange,
        final int verticalRange
    ) {
        return withinRange(random, Math::sqrt, horizontalRange, verticalRange);
    }

    public static BlockPos withinRange(
        final Random random,
        final DoubleUnaryOperator distanceFunction,
        final int horizontalRange,
        final int verticalRange
    ) {
        double angle = random.nextDouble() * MathHelper.TAU;
        double dist = distanceFunction.applyAsDouble(random.nextDouble()) * horizontalRange;
        double x = -dist * Math.sin(angle);
        double z = dist * Math.cos(angle);
        int y = random.nextInt(2 * verticalRange + 1) - verticalRange;
        return BlockPos.ofFloored(x, y, z);
    }

    public static BlockPos withinAngleSlicePreferFar(
        final Random random,
        final int horizontalRange,
        final int verticalRange,
        final int flyingHeight,
        final Vec3d direction,
        final double sliceAngle
    ) {
        return withinAngleSlice(
            random,
            Math::sqrt,
            0,
            horizontalRange,
            verticalRange,
            flyingHeight,
            direction,
            sliceAngle
        );
    }

    public static BlockPos withinAngleSlicePreferNear(
        final Random random,
        final int horizontalRange,
        final int verticalRange,
        final int flyingHeight,
        final Vec3d direction,
        final double sliceAngle
    ) {
        return withinAngleSlice(
            random,
            d -> Math.pow(d, 2),
            0,
            horizontalRange,
            verticalRange,
            flyingHeight,
            direction,
            sliceAngle
        );
    }

    /**
     * @param sliceAngle the angle in radians
     */
    public static BlockPos withinAngleSlice(
        final Random random,
        final DoubleUnaryOperator distanceFunction,
        final int minHorizontalRange,
        final int maxHorizontalRange,
        final int verticalRange,
        final int flyingHeight,
        final Vec3d direction,
        final double sliceAngle
    ) {
        double directionAngle = MathHelper.atan2(direction.z, direction.x) - (float) (Math.PI / 2);
        double randomAngle = directionAngle + (2.0F * random.nextFloat() - 1.0F) * sliceAngle;
        double randomDist = MathHelper.lerp(
            distanceFunction.applyAsDouble(random.nextDouble()),
            minHorizontalRange,
            maxHorizontalRange
        );
        double x = -randomDist * Math.sin(randomAngle);
        double z = randomDist * Math.cos(randomAngle);
        int y = random.nextInt(2 * verticalRange + 1) - verticalRange + flyingHeight;
        return BlockPos.ofFloored(x, y, z);
    }

    public static BlockPos withinAngleCone(
        final Random random,
        final int minRange,
        final int maxRange,
        final int startHeight,
        final Vec3d direction,
        final double coneAngle
    ) {
        double baseHorizontalAngle = Math.atan2(direction.z, direction.x);
        double baseVerticalAngle = Math.asin(direction.y);

        double randomAngleOffset = (2.0 * random.nextDouble() - 1.0) * (coneAngle / 2.0);
        double rotationAngle = random.nextDouble() * 2.0 * Math.PI;

        double yaw = baseHorizontalAngle + randomAngleOffset * Math.cos(rotationAngle);
        double pitch = baseVerticalAngle + randomAngleOffset * Math.sin(rotationAngle);

        double randomDist = MathHelper.lerp(Math.sqrt(random.nextDouble()), minRange, maxRange);
        double randomHorizontalDist = randomDist * Math.cos(pitch);

        double x = randomHorizontalDist * Math.cos(yaw);
        double z = randomHorizontalDist * Math.sin(yaw);
        double y = randomDist * Math.sin(pitch) + startHeight;

        return BlockPos.ofFloored(x, y, z);
    }
}
