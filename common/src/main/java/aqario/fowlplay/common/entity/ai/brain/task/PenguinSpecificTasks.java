package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.AllApplicableBehaviours;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A collection of preconfigured or composite tasks for penguins.
 */
public class PenguinSpecificTasks {
    @SuppressWarnings("unchecked")
    public static ExtendedBehaviour<PenguinEntity> goToWater() {
        return new AllApplicableBehaviours<>(
            Pair.of(
                SlideTasks.startSliding(),
                1
            ),
            Pair.of(
                SetWaterWalkTargetTask.create(32),
                2
            )
        ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.HAS_HUNTING_COOLDOWN));
    }

    private static final int[][] SWIM_DISTANCES = new int[][]{{31, 15}};

    public static SingleTickBehaviour<PenguinEntity> swim() {
        return swim(PenguinSpecificTasks::findSwimTargetPos, Entity::isInsideWaterOrBubbleColumn);
    }

    private static SingleTickBehaviour<PenguinEntity> swim(Function<PenguinEntity, Vec3d> targetGetter, Predicate<PenguinEntity> predicate) {
        return new SingleTickBehaviour<>(
            MemoryList.create(1)
                .absent(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                if(!predicate.test(bird)) {
                    return false;
                }
                Optional<Vec3d> optional = Optional.ofNullable(targetGetter.apply(bird));
                BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, optional.map(vec3d -> new WalkTarget(vec3d, 1.0f, 0)).orElse(null));
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
