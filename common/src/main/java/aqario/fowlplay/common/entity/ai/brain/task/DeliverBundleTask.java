package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.player.PlayerEntity;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.function.Function;

public class DeliverBundleTask {
    public static <E extends PigeonEntity> SingleTickBehaviour<E> run(Function<E, Float> entitySpeedGetter) {
        return new SingleTickBehaviour<>(
            MemoryList.create(4)
                .present(FowlPlayMemoryModuleType.RECIPIENT.get())
                .registered(MemoryModuleType.LOOK_TARGET)
                .registered(MemoryModuleType.WALK_TARGET)
                .registered(FowlPlayMemoryModuleType.TELEPORT_TARGET.get()),
            (bird, brain) -> {
                PlayerEntity recipient = bird.getWorld().getPlayerByUuid(BrainUtils.getMemory(brain, FowlPlayMemoryModuleType.RECIPIENT.get()));
                if(recipient != null) {
                    WalkTarget walkTarget = new WalkTarget(new EntityLookTarget(recipient, false), entitySpeedGetter.apply(bird), 0);
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
