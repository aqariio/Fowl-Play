package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class DelivererFollowOwnerTask extends Task<PigeonEntity> {
    private LivingEntity owner;
    private final float speed;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;

    public DelivererFollowOwnerTask(float speed, float minDistance, float maxDistance) {
        super(ImmutableMap.of());
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PigeonEntity pigeon) {
        LivingEntity owner = pigeon.getOwner();
        if (owner == null) {
            return false;
        }
        if (owner.isSpectator()) {
            return false;
        }
        if (pigeon.isSitting()) {
            return false;
        }
        if (pigeon.squaredDistanceTo(owner) < this.minDistance * this.minDistance) {
            return false;
        }
        if (pigeon.getRecipientUuid() != null) {
            return false;
        }
        this.owner = owner;
        return super.shouldRun(world, pigeon);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, PigeonEntity pigeon, long time) {
        if (pigeon.getRecipientUuid() != null) {
            return false;
        }
        if (pigeon.getNavigation().isIdle()) {
            return false;
        }
        if (pigeon.isSitting()) {
            return false;
        }

        return pigeon.squaredDistanceTo(this.owner) > this.maxDistance * this.maxDistance;
    }

    @Override
    protected void keepRunning(ServerWorld world, PigeonEntity pigeon, long time) {
        pigeon.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(this.owner, true));
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 20;
            if (!pigeon.isLeashed() && !pigeon.hasVehicle()) {
                if (pigeon.squaredDistanceTo(this.owner) >= 144.0) {
                    pigeon.getBrain().remember(FowlPlayMemoryModuleType.TELEPORT_TARGET, new TeleportTarget(this.owner));
                }
                else {
                    pigeon.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(this.owner, this.speed, 0));
                }
            }
        }
    }
}
