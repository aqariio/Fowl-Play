package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Function;
import java.util.function.Predicate;

public class DeliverBundleTask {
    public static <E extends PigeonEntity> Task<E> run(Predicate<E> startPredicate, Function<E, Float> entitySpeedGetter) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryValue(FowlPlayMemoryModuleType.RECIPIENT),
                    instance.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                    instance.queryMemoryOptional(MemoryModuleType.WALK_TARGET),
                    instance.queryMemoryOptional(FowlPlayMemoryModuleType.TELEPORT_TARGET)
                )
                .apply(
                    instance,
                    (recipientUuid, lookTarget, walkTarget, teleportTarget) -> (world, pigeon, l) -> {
                        PlayerEntity recipient = world.getPlayerByUuid(instance.getValue(recipientUuid));
                        if (recipient != null && startPredicate.test(pigeon)) {
                            WalkTarget newWalkTarget = new WalkTarget(new EntityLookTarget(recipient, false), entitySpeedGetter.apply(pigeon), 0);
                            lookTarget.remember(new EntityLookTarget(recipient, true));
                            walkTarget.remember(newWalkTarget);
                            if (pigeon.getOwner() != null && pigeon.squaredDistanceTo(recipient) > 100 * 100 && pigeon.squaredDistanceTo(pigeon.getOwner()) > 16 * 16) {
                                teleportTarget.remember(new TeleportTarget(recipient));
                            }
                            return true;
                        }
                        return false;
                    }
                )
        );
    }
}
