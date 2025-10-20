package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetAirTargetTask<E extends BirdEntity> extends SpeedModifiableBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .registered(MemoryModuleType.WALK_TARGET);

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, E bird) {
        return this.shouldKeepRunning(bird);
    }

    @Override
    protected boolean shouldKeepRunning(E bird) {
        return bird.getAir() < 400;
    }

    @Override
    protected void tick(E bird) {
        Vec3d targetPos = this.findAir(bird);
        BrainUtils.setMemory(bird, MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, this.speedModifier.apply(bird, targetPos), 0));
    }

    private Vec3d findAir(E bird) {
        Iterable<BlockPos> iterable = BlockPos.iterate(
            MathHelper.floor(bird.getX() - 1.0),
            bird.getBlockY(),
            MathHelper.floor(bird.getZ() - 1.0),
            MathHelper.floor(bird.getX() + 1.0),
            MathHelper.floor(bird.getY() + 8.0),
            MathHelper.floor(bird.getZ() + 1.0)
        );
        BlockPos blockPos = null;

        for(BlockPos blockPos2 : iterable) {
            if(this.isAirPos(bird.getWorld(), blockPos2)) {
                blockPos = blockPos2;
                break;
            }
        }

        if(blockPos == null) {
            blockPos = BlockPos.ofFloored(bird.getX(), bird.getY() + 8.0, bird.getZ());
        }

        return new Vec3d(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
    }

    private boolean isAirPos(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return (world.getFluidState(pos).isEmpty() || blockState.isOf(Blocks.BUBBLE_COLUMN)) && blockState.canPathfindThrough(world, pos, NavigationType.LAND);
    }
}