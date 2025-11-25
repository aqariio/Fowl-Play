package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.util.BrainUtils;

public class DeliverBundle {
    public static <E extends PigeonEntity> AnonymousBehaviour<E> run() {
        return new AnonymousBehaviour<>(
            MemoryList.create(4)
                .present(FowlPlayMemoryTypes.RECIPIENT.get())
                .registered(
                    MemoryModuleType.LOOK_TARGET,
                    MemoryModuleType.WALK_TARGET,
                    FowlPlayMemoryTypes.TELEPORT_TARGET.get()
                ),
            (bird, brain) -> {
                Player recipient = bird.level().getPlayerByUUID(BrainUtils.getMemory(brain, FowlPlayMemoryTypes.RECIPIENT.get()));
                if(recipient != null) {
                    WalkTarget walkTarget = new WalkTarget(new EntityTracker(recipient, false), 1.0F, 0);
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityTracker(recipient, true));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, walkTarget);
                    if(bird.getOwner() != null && bird.distanceToSqr(recipient) > 100 * 100 && bird.distanceToSqr(bird.getOwner()) > 16 * 16) {
                        BrainUtils.setMemory(brain, FowlPlayMemoryTypes.TELEPORT_TARGET.get(), new TeleportTarget(recipient));
                    }
                    return true;
                }
                return false;
            }
        );
    }
}
