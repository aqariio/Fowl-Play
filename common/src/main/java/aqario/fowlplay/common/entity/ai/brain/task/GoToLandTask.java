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

public class GoToLandTask {
    public static SingleTickBehaviour<BirdEntity> create(int range, float speed) {
        return new SingleTickBehaviour<>(
            MemoryList.create(4)
                .present(
                    MemoryModuleType.HAS_HUNTING_COOLDOWN
                )
                .absent(
                    MemoryModuleType.ATTACK_TARGET,
                    MemoryModuleType.WALK_TARGET
                )
                .registered(
                    MemoryModuleType.LOOK_TARGET
                ),
            (bird, brain) -> {
                if(!bird.getWorld().getFluidState(bird.getBlockPos()).isIn(FluidTags.WATER)) {
                    return false;
                }
                BlockPos targetPos = null;
                BlockPos entityBlockPos = bird.getBlockPos();

                for(BlockPos pos : BlockPos.iterateOutwards(entityBlockPos, range, range, range)) {
                    if(pos.getX() != entityBlockPos.getX() || pos.getZ() != entityBlockPos.getZ()) {
                        BlockState aboveCurrentBlock = bird.getWorld().getBlockState(pos.up());
                        BlockState currentBlock = bird.getWorld().getBlockState(pos);
                        if(!currentBlock.isOf(Blocks.WATER) && !currentBlock.isAir() && aboveCurrentBlock.isAir()) {
                            targetPos = pos.up(2).toImmutable();
                            break;
                        }
                    }
                }

                if(targetPos == null) {
                    return false;
                }

                BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(targetPos));
                BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosLookTarget(targetPos), speed, 0));
                return true;
            }
        );
    }
}
