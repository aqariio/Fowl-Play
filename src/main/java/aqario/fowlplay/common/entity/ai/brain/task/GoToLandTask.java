package aqario.fowlplay.common.entity.ai.brain.task;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;

public class GoToLandTask {
    public static Task<PathAwareEntity> create(int range, float speed) {
        return TaskTriggerer.task(
            instance -> instance.group(
                    instance.queryMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN),
                    instance.queryMemoryAbsent(MemoryModuleType.ATTACK_TARGET),
                    instance.queryMemoryAbsent(MemoryModuleType.WALK_TARGET),
                    instance.queryMemoryOptional(MemoryModuleType.LOOK_TARGET)
                )
                .apply(instance, (huntingCooldown, attackTarget, walkTarget, lookTarget) -> (world, entity, time) -> {
                    if (!world.getFluidState(entity.getBlockPos()).isIn(FluidTags.WATER)) {
                        return false;
                    }
                    BlockPos targetPos = null;
                    BlockPos entityBlockPos = entity.getBlockPos();

                    for (BlockPos pos : BlockPos.iterateOutwards(entityBlockPos, range, range, range)) {
                        if (pos.getX() != entityBlockPos.getX() || pos.getZ() != entityBlockPos.getZ()) {
                            BlockState aboveCurrentBlock = entity.getWorld().getBlockState(pos.up());
                            BlockState currentBlock = entity.getWorld().getBlockState(pos);
                            if (!currentBlock.isOf(Blocks.WATER) && !currentBlock.isAir() && aboveCurrentBlock.isAir()) {
                                targetPos = pos.up(2).toImmutable();
                                break;
                            }
                        }
                    }

                    if (targetPos == null) {
                        return false;
                    }

                    lookTarget.remember(new BlockPosLookTarget(targetPos));
                    walkTarget.remember(new WalkTarget(new BlockPosLookTarget(targetPos), speed, 0));
                    return true;
                })
        );
    }
}
