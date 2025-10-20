package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetOwnerTargetTask extends SpeedModifiableBehaviour<PigeonEntity> {
    private static final MemoryList MEMORIES = MemoryList.create(3)
        .registered(
            FowlPlayMemoryModuleType.TELEPORT_TARGET.get(),
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET
        );
    private LivingEntity owner;
    private int updateCountdownTicks;
    protected UniformIntProvider range = UniformIntProvider.create(5, 10);

    public SetOwnerTargetTask range(int min, int max) {
        this.range = UniformIntProvider.create(min, max);

        return this;
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
        if(pigeon.squaredDistanceTo(owner) < this.range.getMin() * this.range.getMin()) {
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

        return pigeon.squaredDistanceTo(this.owner) > this.range.getMax() * this.range.getMax();
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
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(this.owner, this.speedModifier.apply(pigeon, this.owner.getPos()), 0));
                }
            }
        }
    }
}
