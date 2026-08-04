package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SetBreatheTarget<E extends BirdEntity> extends SpeedModifiableBehaviour<E> {
    private static final int TARGET_REFRESH_INTERVAL = 10;
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .registered(MemoryModuleType.WALK_TARGET);
    private BlockPos breathePos;
    private int nextTargetRefreshTick;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, E bird) {
        return this.shouldKeepRunning(bird);
    }

    @Override
    protected boolean shouldKeepRunning(E bird) {
        return bird.getAirSupply() < 400;
    }

    @Override
    protected void tick(E bird) {
        if(this.breathePos == null
            || bird.tickCount >= this.nextTargetRefreshTick && !this.isAirPos(bird.level(), this.breathePos)
        ) {
            this.breathePos = this.findAir(bird);
        }
        if(bird.tickCount < this.nextTargetRefreshTick) {
            return;
        }
        this.nextTargetRefreshTick = bird.tickCount + TARGET_REFRESH_INTERVAL;
        Vec3 targetPos = Vec3.atBottomCenterOf(this.breathePos.above());
        if(bird.isMemoryPresent(MemoryModuleType.WALK_TARGET)
            && bird.getPresentMemory(MemoryModuleType.WALK_TARGET).getTarget().currentPosition().distanceToSqr(targetPos) < 0.25
        ) {
            return;
        }
        bird.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, this.speedModifier.apply(bird, targetPos), 0));
    }

    @Override
    protected void stop(E bird) {
        this.breathePos = null;
        this.nextTargetRefreshTick = 0;
    }

    private BlockPos findAir(E bird) {
        Iterable<BlockPos> iterable = BlockPos.betweenClosed(
            Mth.floor(bird.getX() - 1.0),
            bird.getBlockY(),
            Mth.floor(bird.getZ() - 1.0),
            Mth.floor(bird.getX() + 1.0),
            Mth.floor(bird.getY() + 8.0),
            Mth.floor(bird.getZ() + 1.0)
        );
        BlockPos blockPos = null;

        for(BlockPos blockPos2 : iterable) {
            if(this.isAirPos(bird.level(), blockPos2)) {
                blockPos = blockPos2;
                break;
            }
        }

        if(blockPos == null) {
            blockPos = BlockPos.containing(bird.getX(), bird.getY() + 8.0, bird.getZ());
        }

        return blockPos;
    }

    private boolean isAirPos(LevelReader world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return (world.getFluidState(pos).isEmpty() || blockState.is(Blocks.BUBBLE_COLUMN)) && blockState.isPathfindable(PathComputationType.LAND);
    }
}
