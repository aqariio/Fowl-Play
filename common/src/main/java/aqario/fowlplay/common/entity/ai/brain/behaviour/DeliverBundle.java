package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.player.PlayerEntity;
import net.tslat.smartbrainlib.util.BrainUtils;

public class DeliverBundle {
    public static <E extends PigeonEntity> AnonymousBehaviour<E> run() {
        return new AnonymousBehaviour<>(
            MemoryList.create(4)
                .present(FowlPlayMemoryModuleType.RECIPIENT.get())
                .registered(
                    MemoryModuleType.LOOK_TARGET,
                    MemoryModuleType.WALK_TARGET,
                    FowlPlayMemoryModuleType.TELEPORT_TARGET.get()
                ),
            (bird, brain) -> {
                PlayerEntity recipient = bird.getWorld().getPlayerByUuid(BrainUtils.getMemory(brain, FowlPlayMemoryModuleType.RECIPIENT.get()));
                if(recipient != null) {
                    WalkTarget walkTarget = new WalkTarget(new EntityLookTarget(recipient, false), 1.0F, 0);
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(recipient, true));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, walkTarget);
                    if(bird.getOwner() != null && bird.squaredDistanceTo(recipient) > 100 * 100 && bird.squaredDistanceTo(bird.getOwner()) > 16 * 16) {
                        BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.TELEPORT_TARGET.get(), new TeleportTarget(recipient));
                    }
                    return true;
                }
                return false;
            }
        );
    }
}
