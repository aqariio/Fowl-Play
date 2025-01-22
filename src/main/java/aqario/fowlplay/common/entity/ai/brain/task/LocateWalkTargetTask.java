package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class LocateWalkTargetTask {
    private static final int DEFAULT_HORIZONTAL_RADIUS = 64;
    private static final int DEFAULT_VERTICAL_RADIUS = 16;
    private static final int[][] RADII = new int[][]{{1, 1}, {3, 3}, {5, 5}, {6, 5}, {7, 7}, {64, 16}};

    public static SingleTickTask<BirdEntity> create() {
        return create(true);
    }

    public static SingleTickTask<BirdEntity> create(boolean strollInsideWater) {
        return create(bird -> FuzzyTargeting.find(bird, DEFAULT_HORIZONTAL_RADIUS, DEFAULT_VERTICAL_RADIUS), strollInsideWater ? bird -> true : bird -> !bird.isInsideWaterOrBubbleColumn());
    }

    public static Task<BirdEntity> create(int horizontalRadius, int verticalRadius) {
        return create(bird -> FuzzyTargeting.find(bird, horizontalRadius, verticalRadius), bird -> true);
    }

    public static Task<BirdEntity> createSolidTargeting() {
        return create(bird -> findTargetPos(bird, DEFAULT_HORIZONTAL_RADIUS, DEFAULT_VERTICAL_RADIUS), bird -> true);
    }

    public static Task<BirdEntity> createDynamicRadius() {
        return create(LocateWalkTargetTask::findTargetPos, Entity::isInsideWaterOrBubbleColumn);
    }

    private static SingleTickTask<BirdEntity> create(Function<BirdEntity, Vec3d> targetGetter, Predicate<BirdEntity> shouldRun) {
        return TaskTriggerer.task(
            context -> context.group(context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET)).apply(context, walkTarget -> (world, bird, time) -> {
                if (!shouldRun.test(bird)) {
                    return false;
                }
                float speed;
                if (bird instanceof FlyingBirdEntity flyingBird && flyingBird.isFlying()) {
                    speed = flyingBird.getFlySpeedMultiplier();
                }
                else if (bird.isInsideWaterOrBubbleColumn()) {
                    speed = bird.getSwimSpeedMultiplier();
                }
                else {
                    speed = bird.getWalkSpeedMultiplier();
                }
                Optional<Vec3d> optional = Optional.ofNullable(targetGetter.apply(bird));
                walkTarget.remember(optional.map(pos -> new WalkTarget(pos, speed, 0)));
                return true;
            })
        );
    }

    @Nullable
    private static Vec3d findTargetPos(BirdEntity bird) {
        Vec3d vec3d = null;
        Vec3d vec3d2 = null;

        for (int[] is : RADII) {
            if (vec3d == null) {
                vec3d2 = LookTargetUtil.find(bird, is[0], is[1]);
            }
            else {
                vec3d2 = bird.getPos().add(bird.getPos().relativize(vec3d).normalize().multiply(is[0], is[1], is[0]));
            }

            if (vec3d2 == null || bird.getWorld().getFluidState(BlockPos.ofFloored(vec3d2)).isEmpty()) {
                return vec3d;
            }

            vec3d = vec3d2;
        }

        return vec3d2;
    }

    @Nullable
    private static Vec3d findTargetPos(BirdEntity bird, int horizontalRadius, int verticalRadius) {
        Vec3d vec3d = bird.getRotationVec(0.0F);
        return NoPenaltySolidTargeting.find(bird, horizontalRadius, verticalRadius, -2, vec3d.x, vec3d.z, (float) (Math.PI / 2));
    }
}
