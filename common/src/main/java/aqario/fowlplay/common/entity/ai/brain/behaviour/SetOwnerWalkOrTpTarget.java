package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.common.entity.bird.dove.PigeonEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

import java.util.List;

public class SetOwnerWalkOrTpTarget extends SpeedModifiableBehaviour<PigeonEntity> {
    private static final MemoryList MEMORIES = MemoryList.create(3)
        .registered(
            FowlPlayMemoryTypes.TELEPORT_TARGET.get(),
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET
        );
    private LivingEntity owner;
    private int updateCountdownTicks;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, PigeonEntity pigeon) {
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
        if(pigeon.getRecipientUuid() != null) {
            return false;
        }
        this.owner = owner;
        return super.checkExtraStartConditions(world, pigeon);
    }

    @Override
    protected boolean shouldKeepRunning(PigeonEntity pigeon) {
        if(pigeon.getRecipientUuid() != null) {
            return false;
        }
        return !pigeon.isSitting();
    }

    @Override
    protected void tick(PigeonEntity pigeon) {
        pigeon.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(this.owner, true));
        if(--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 20;
            if(!pigeon.isLeashed() && !pigeon.isPassenger()) {
                if(pigeon.distanceToSqr(this.owner) >= 144.0) {
                    pigeon.setMemory(FowlPlayMemoryTypes.TELEPORT_TARGET.get(), new TeleportTarget(this.owner));
                }
                pigeon.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(this.owner, this.speedModifier.apply(pigeon, this.owner.position()), 0));
            }
        }
    }
}
