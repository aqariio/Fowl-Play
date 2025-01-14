package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;

import java.util.Optional;
import java.util.function.Function;

public class GoToAttackTargetTask {
    private static final int WEAPON_REACH_REDUCTION = 1;

    public static <E extends BirdEntity> Task<E> create(float speed) {
        return create(entity -> speed);
    }

    public static <E extends BirdEntity> Task<E> create(Function<E, Float> speed) {
        return TaskTriggerer.task(
            context -> context.group(
                    context.queryMemoryOptional(MemoryModuleType.WALK_TARGET),
                    context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                    context.queryMemoryValue(MemoryModuleType.ATTACK_TARGET),
                    context.queryMemoryOptional(MemoryModuleType.VISIBLE_MOBS)
                )
                .apply(
                    context,
                    (walkTarget, lookTarget, attackTarget, visibleMobs) -> (world, entity, time) -> {
                        LivingEntity target = context.getValue(attackTarget);
                        Optional<LivingTargetCache> nearbyMobs = context.getOptionalValue(visibleMobs);
                        if (nearbyMobs.isPresent()
                            && nearbyMobs.get().contains(target)
                            && LookTargetUtil.isTargetWithinAttackRange(entity, target, WEAPON_REACH_REDUCTION)) {
                            walkTarget.forget();
                        }
                        else {
                            lookTarget.remember(new EntityLookTarget(target, true));
                            walkTarget.remember(new WalkTarget(new EntityLookTarget(target, false), speed.apply(entity), 0));
                        }

                        return true;
                    }
                )
        );
    }
}
