package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.server.world.ServerWorld;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class FollowOwnerTask extends ExtendedBehaviour<PigeonEntity> {
    private static final List<Pair<MemoryModuleType<?>, MemoryModuleState>> MEMORIES = ImmutableList.of(
        Pair.of(FowlPlayMemoryModuleType.TELEPORT_TARGET.get(), MemoryModuleState.VALUE_PRESENT),
        Pair.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_PRESENT),
        Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)
    );
    private LivingEntity owner;
    private final float speed;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;

    public FollowOwnerTask(float speed, float minDistance, float maxDistance) {
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PigeonEntity pigeon) {
        LivingEntity owner = pigeon.getOwner();
        if(owner == null) {
            return false;
        }
        if(owner.isSpectator()) {
            return false;
        }
        if(pigeon.isSitting()) {
            return false;
        }
        if(pigeon.squaredDistanceTo(owner) < this.minDistance * this.minDistance) {
            return false;
        }
        if(pigeon.getRecipientUuid() != null) {
            return false;
        }
        this.owner = owner;
        return super.shouldRun(world, pigeon);
    }

    @Override
    protected boolean shouldKeepRunning(PigeonEntity pigeon) {
        if(pigeon.getRecipientUuid() != null) {
            return false;
        }
        if(pigeon.getNavigation().isIdle()) {
            return false;
        }
        if(pigeon.isSitting()) {
            return false;
        }

        return pigeon.squaredDistanceTo(this.owner) > this.maxDistance * this.maxDistance;
    }

    @Override
    protected void tick(PigeonEntity pigeon) {
        Brain<?> brain = pigeon.getBrain();
        BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(this.owner, true));
        if(--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 20;
            if(!pigeon.isLeashed() && !pigeon.hasVehicle()) {
                if(pigeon.squaredDistanceTo(this.owner) >= 144.0) {
                    BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.TELEPORT_TARGET.get(), new TeleportTarget(this.owner));
                }
                else {
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(this.owner, this.speed, 0));
                }
            }
        }
    }
}
