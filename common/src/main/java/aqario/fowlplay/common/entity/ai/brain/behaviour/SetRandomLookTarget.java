package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.FreePositionTracker;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetRandomLookTarget<E extends MobEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET
        );

    protected FloatProvider runChance = ConstantFloatProvider.create(1f);
    private long timeUntilNextLook = 0L;

    public SetRandomLookTarget() {
        this.runtimeProvider = entity -> entity.getRandom().nextBetween(20, 60);
    }

    public SetRandomLookTarget<E> lookChance(float chance) {
        return this.lookChance(ConstantFloatProvider.create(chance));
    }

    public SetRandomLookTarget<E> lookChance(FloatProvider chance) {
        this.runChance = chance;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean shouldRun(ServerWorld level, E entity) {
        return entity.getRandom().nextFloat() < this.runChance.get(entity.getRandom());
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void tick(E entity) {
        if(this.timeUntilNextLook <= entity.getWorld().getTime()) {
            this.lookAround(entity);
        }
    }

    private void lookAround(E entity) {
        double angle = MathHelper.TAU * entity.getRandom().nextDouble();

        int lookTime = entity.getRandom().nextBetween(15, 60);
        this.timeUntilNextLook = entity.getWorld().getTime() + lookTime;
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.LOOK_TARGET, new FreePositionTracker(entity.getEyePos().add(Math.cos(angle), 0, Math.sin(angle))), lookTime);
    }
}
