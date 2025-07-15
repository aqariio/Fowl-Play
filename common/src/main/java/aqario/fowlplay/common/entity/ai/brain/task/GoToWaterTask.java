package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.tslat.smartbrainlib.util.BrainUtils;

public class GoToWaterTask {
    public static SingleTickBehaviour<BirdEntity> create(int range, float speed) {
        return new SingleTickBehaviour<>(
            MemoryList.create(3)
                .absent(
                    MemoryModuleType.ATTACK_TARGET,
                    MemoryModuleType.WALK_TARGET
                )
                .registered(
                    MemoryModuleType.LOOK_TARGET
                ),
            (bird, brain) -> {
                if(bird.getWorld().getFluidState(bird.getBlockPos()).isIn(FluidTags.WATER)) {
                    return false;
                }
                BlockPos blockPos = null;
                BlockPos blockPos2 = null;
                BlockPos blockPos3 = bird.getBlockPos();

                for(BlockPos blockPos4 : BlockPos.iterateOutwards(blockPos3, range, range, range)) {
                    if(blockPos4.getX() != blockPos3.getX() || blockPos4.getZ() != blockPos3.getZ()) {
                        BlockState blockState = bird.getWorld().getBlockState(blockPos4.up());
                        BlockState blockState2 = bird.getWorld().getBlockState(blockPos4);
                        if(blockState2.isOf(Blocks.WATER)) {
                            if(blockState.isAir()) {
                                blockPos = blockPos4.toImmutable();
                                break;
                            }

                            if(blockPos2 == null && !blockPos4.isWithinDistance(bird.getPos(), 1.5)) {
                                blockPos2 = blockPos4.toImmutable();
                            }
                        }
                    }
                }

                if(blockPos == null) {
                    blockPos = blockPos2;
                }

                if(blockPos != null) {
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(blockPos));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosLookTarget(blockPos), speed, 0));
                }
                return true;
            }
        );
    }
}
