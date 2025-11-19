package aqario.fowlplay.common.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import java.util.function.DoubleUnaryOperator;

public class ExtendedRandomPos {
    public static BlockPos generatePreferFar(
        final RandomSource random,
        final int horizontalRange,
        final int verticalRange
    ) {
        return generate(random, Math::sqrt, horizontalRange, verticalRange);
    }

    public static BlockPos generate(
        final RandomSource random,
        final DoubleUnaryOperator distanceFunction,
        final int horizontalRange,
        final int verticalRange
    ) {
        double angle = random.nextDouble() * Mth.TWO_PI;
        double dist = distanceFunction.applyAsDouble(random.nextDouble()) * horizontalRange;
        double x = -dist * Math.sin(angle);
        double z = dist * Math.cos(angle);
        int y = random.nextInt(2 * verticalRange + 1) - verticalRange;
        return BlockPos.containing(x, y, z);
    }

    public static BlockPos generateWithinAnglePreferFar(
        final RandomSource random,
        final int horizontalRange,
        final int verticalRange,
        final int flyingHeight,
        final Vec3 direction,
        final double sliceAngle
    ) {
        return generateWithinAngle(
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

    public static BlockPos generateWithinAnglePreferNear(
        final RandomSource random,
        final int horizontalRange,
        final int verticalRange,
        final int flyingHeight,
        final Vec3 direction,
        final double sliceAngle
    ) {
        return generateWithinAngle(
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
     * @param angle the slice angle in radians
     */
    public static BlockPos generateWithinAngle(
        final RandomSource random,
        final DoubleUnaryOperator distanceFunction,
        final int minHorizontalRange,
        final int maxHorizontalRange,
        final int verticalRange,
        final int flyingHeight,
        final Vec3 direction,
        final double angle
    ) {
        double directionAngle = Mth.atan2(direction.z, direction.x) - (float) (Math.PI / 2);
        double randomAngle = directionAngle + (2.0F * random.nextFloat() - 1.0F) * angle;
        double randomDist = Mth.lerp(
            distanceFunction.applyAsDouble(random.nextDouble()),
            minHorizontalRange,
            maxHorizontalRange
        );
        double x = -randomDist * Math.sin(randomAngle);
        double z = randomDist * Math.cos(randomAngle);
        int y = random.nextInt(2 * verticalRange + 1) - verticalRange + flyingHeight;
        return BlockPos.containing(x, y, z);
    }

    public static BlockPos generateWithinCone(
        final RandomSource random,
        final int minRange,
        final int maxRange,
        final int startHeight,
        final Vec3 direction,
        final double coneAngle
    ) {
        double baseHorizontalAngle = Math.atan2(direction.z, direction.x);
        double baseVerticalAngle = Math.asin(direction.y);

        double randomAngleOffset = (2.0 * random.nextDouble() - 1.0) * (coneAngle / 2.0);
        double rotationAngle = random.nextDouble() * 2.0 * Math.PI;

        double yaw = baseHorizontalAngle + randomAngleOffset * Math.cos(rotationAngle);
        double pitch = baseVerticalAngle + randomAngleOffset * Math.sin(rotationAngle);

        double randomDist = Mth.lerp(Math.sqrt(random.nextDouble()), minRange, maxRange);
        double randomHorizontalDist = randomDist * Math.cos(pitch);

        double x = randomHorizontalDist * Math.cos(yaw);
        double z = randomHorizontalDist * Math.sin(yaw);
        double y = randomDist * Math.sin(pitch) + startHeight;

        return BlockPos.containing(x, y, z);
    }
}
