package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.FreePositionTracker;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetRandomLookTarget<E extends Mob> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET
        );

    protected FloatProvider runChance = ConstantFloat.of(1f);
    private long timeUntilNextLook = 0L;

    public SetRandomLookTarget() {
        this.runtimeProvider = entity -> entity.getRandom().nextIntBetweenInclusive(20, 60);
    }

    public SetRandomLookTarget<E> lookChance(float chance) {
        return this.lookChance(ConstantFloat.of(chance));
    }

    public SetRandomLookTarget<E> lookChance(FloatProvider chance) {
        this.runChance = chance;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return entity.getRandom().nextFloat() < this.runChance.sample(entity.getRandom());
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void tick(E entity) {
        if(this.timeUntilNextLook <= entity.level().getGameTime()) {
            this.lookAround(entity);
        }
    }

    private void lookAround(E entity) {
        double angle = Mth.TWO_PI * entity.getRandom().nextDouble();

        int lookTime = entity.getRandom().nextIntBetweenInclusive(15, 60);
        this.timeUntilNextLook = entity.level().getGameTime() + lookTime;
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.LOOK_TARGET, new FreePositionTracker(entity.getEyePosition().add(Math.cos(angle), 0, Math.sin(angle))), lookTime);
    }
}
