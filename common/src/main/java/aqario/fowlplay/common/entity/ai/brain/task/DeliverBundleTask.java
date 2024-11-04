package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class DeliverBundleTask extends Task<PigeonEntity> {
    private final float speed;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private PlayerEntity recipient;

    public DeliverBundleTask(float speed, float minDistance, float maxDistance) {
        super(ImmutableMap.of());
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PigeonEntity pigeon) {
        if (!pigeon.isTamed()) {
            return false;
        }
        if (pigeon.getRecipientUuid() == null) {
            return false;
        }
        this.recipient = pigeon.getWorld().getPlayerByUuid(pigeon.getRecipientUuid());
        if (this.recipient == null) {
            return false;
        }
        return pigeon.squaredDistanceTo(this.recipient) > this.minDistance * this.minDistance;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, PigeonEntity pigeon, long time) {
        if (pigeon.getNavigation().isIdle()) {
            return false;
        }
        return pigeon.squaredDistanceTo(this.recipient) < this.maxDistance * this.maxDistance;
    }

    @Override
    protected void keepRunning(ServerWorld world, PigeonEntity pigeon, long time) {
        pigeon.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(this.recipient, true));
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            if (pigeon.squaredDistanceTo(this.recipient) > 10000 && pigeon.squaredDistanceTo(pigeon.getOwner()) > 1024) {
                pigeon.getBrain().remember(FowlPlayMemoryModuleType.TELEPORT_TARGET, new TeleportTarget(this.recipient));
            }
            else {
                pigeon.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(this.recipient, this.speed, 0));
            }
        }
    }
}
