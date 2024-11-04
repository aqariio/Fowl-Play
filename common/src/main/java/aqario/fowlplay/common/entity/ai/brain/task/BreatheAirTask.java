package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;

public class BreatheAirTask extends Task<BirdEntity> {
    private final float speed;

    public BreatheAirTask(float speed) {
        super(ImmutableMap.of());
        this.speed = speed;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, BirdEntity bird) {
        return bird.getAir() < 400;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, BirdEntity bird, long l) {
        return this.shouldRun(world, bird);
    }

    @Override
    protected void keepRunning(ServerWorld world, BirdEntity bird, long l) {
        bird.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(this.findAir(bird), this.speed, 0));
    }

    private Vec3d findAir(BirdEntity bird) {
        Iterable<BlockPos> iterable = BlockPos.iterate(
            MathHelper.floor(bird.getX() - 1.0),
            bird.getBlockY(),
            MathHelper.floor(bird.getZ() - 1.0),
            MathHelper.floor(bird.getX() + 1.0),
            MathHelper.floor(bird.getY() + 8.0),
            MathHelper.floor(bird.getZ() + 1.0)
        );
        BlockPos blockPos = null;

        for (BlockPos blockPos2 : iterable) {
            if (this.isAirPos(bird.getWorld(), blockPos2)) {
                blockPos = blockPos2;
                break;
            }
        }

        if (blockPos == null) {
            blockPos = BlockPos.create(bird.getX(), bird.getY() + 8.0, bird.getZ());
        }

        return new Vec3d(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
    }

    private boolean isAirPos(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return (world.getFluidState(pos).isEmpty() || blockState.isOf(Blocks.BUBBLE_COLUMN)) && blockState.canPathfindThrough(NavigationType.LAND);
    }
}