package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.util.Birds;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.SequentialBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class PenguinSpecificTasks {
    @SuppressWarnings("unchecked")
    public static ExtendedBehaviour<PenguinEntity> goToWater() {
        return new SequentialBehaviour<>(
            Pair.of(
                SlideControlTask.startSliding(),
                1
            ),
            Pair.of(
                GoToWaterTask.create(32, Birds.WALK_SPEED),
                2
            )

        ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.HAS_HUNTING_COOLDOWN));
    }

    private static final int[][] SWIM_DISTANCES = new int[][]{{31, 15}};

    public static SingleTickBehaviour<PenguinEntity> swim(float speed) {
        return swim(speed, PenguinSpecificTasks::findSwimTargetPos, Entity::isInsideWaterOrBubbleColumn);
    }

    private static SingleTickBehaviour<PenguinEntity> swim(float speed, Function<PenguinEntity, Vec3d> targetGetter, Predicate<PenguinEntity> predicate) {
        return new SingleTickBehaviour<>(
            ImmutableList.of(
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)
            ),
            (bird, brain) -> {
                if(!predicate.test(bird)) {
                    return false;
                }
                Optional<Vec3d> optional = Optional.ofNullable(targetGetter.apply(bird));
                BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, optional.map(vec3d -> new WalkTarget(vec3d, speed, 0)).orElse(null));
                return true;
            }
        );
    }

    @Nullable
    private static Vec3d findSwimTargetPos(PathAwareEntity entity) {
        Vec3d vec3d = null;
        Vec3d vec3d2 = null;

        for(int[] is : SWIM_DISTANCES) {
            if(vec3d == null) {
                vec3d2 = LookTargetUtil.find(entity, is[0], is[1]);
            }
            else {
                vec3d2 = entity.getPos().add(entity.getPos().relativize(vec3d).normalize().multiply(is[0], is[1], is[0]));
            }

            if(vec3d2 == null || entity.getWorld().getFluidState(BlockPos.ofFloored(vec3d2)).isEmpty()) {
                return vec3d;
            }

            vec3d = vec3d2;
        }

        return vec3d2;
    }
}
