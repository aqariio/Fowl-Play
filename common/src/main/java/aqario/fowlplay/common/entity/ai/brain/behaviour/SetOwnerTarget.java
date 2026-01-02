package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.common.entity.bird.pigeon.PigeonEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetOwnerTarget extends SpeedModifiableBehaviour<PigeonEntity> {
    private static final MemoryList MEMORIES = MemoryList.create(3)
        .registered(
            FowlPlayMemoryTypes.TELEPORT_TARGET.get(),
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET
        );
    private LivingEntity owner;
    private int updateCountdownTicks;
    protected UniformInt range = UniformInt.of(5, 10);

    public SetOwnerTarget setRange(int min, int max) {
        this.range = UniformInt.of(min, max);

        return this;
    }

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
        if(pigeon.distanceToSqr(owner) < this.range.getMinValue() * this.range.getMinValue()) {
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
        if(pigeon.getNavigation().isDone()) {
            return false;
        }
        if(pigeon.isSitting()) {
            return false;
        }

        return pigeon.distanceToSqr(this.owner) > this.range.getMaxValue() * this.range.getMaxValue();
    }

    @Override
    protected void tick(PigeonEntity pigeon) {
        Brain<?> brain = pigeon.getBrain();
        BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityTracker(this.owner, true));
        if(--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 20;
            if(!pigeon.isLeashed() && !pigeon.isPassenger()) {
                if(pigeon.distanceToSqr(this.owner) >= 144.0) {
                    BrainUtils.setMemory(brain, FowlPlayMemoryTypes.TELEPORT_TARGET.get(), new TeleportTarget(this.owner));
                }
                else {
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(this.owner, this.speedModifier.apply(pigeon, this.owner.position()), 0));
                }
            }
        }
    }
}
