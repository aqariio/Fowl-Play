package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class TeleportToTargetTask extends MultiTickTask<BirdEntity> {
    public TeleportToTargetTask() {
        super(ImmutableMap.of(FowlPlayMemoryModuleType.TELEPORT_TARGET, MemoryModuleState.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, BirdEntity entity, long time) {
        return entity.getBrain().isMemoryInState(FowlPlayMemoryModuleType.TELEPORT_TARGET, MemoryModuleState.VALUE_PRESENT);
    }

    @Override
    protected void keepRunning(ServerWorld world, BirdEntity entity, long time) {
        if (this.tryTeleport(entity)) {
            entity.getBrain().forget(FowlPlayMemoryModuleType.TELEPORT_TARGET);
        }
    }

    private boolean tryTeleport(BirdEntity entity) {
        if (!entity.getBrain().hasMemoryModule(FowlPlayMemoryModuleType.TELEPORT_TARGET)) {
            return false;
        }
        Entity target = entity.getBrain().getOptionalMemory(FowlPlayMemoryModuleType.TELEPORT_TARGET).get().entity();
        BlockPos pos = target.getBlockPos();

        for (int i = 0; i < 10; i++) {
            int j = entity.getRandom().nextBetween(-3, 3);
            int k = entity.getRandom().nextBetween(-3, 3);
            if (Math.abs(j) >= 2 || Math.abs(k) >= 2) {
                int l = entity.getRandom().nextBetween(-1, 1);
                if (this.tryTeleportTo(entity, pos.getX() + j, pos.getY() + l, pos.getZ() + k)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean tryTeleportTo(BirdEntity entity, int x, int y, int z) {
        if (!this.canTeleportTo(entity, new BlockPos(x, y, z))) {
            return false;
        }

        entity.refreshPositionAndAngles(x + 0.5, y, z + 0.5, entity.getYaw(), entity.getPitch());
        entity.getNavigation().stop();
        return true;
    }

    private boolean canTeleportTo(BirdEntity entity, BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(entity.getWorld(), pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        BlockPos distance = pos.subtract(entity.getBlockPos());
        return entity.getWorld().isSpaceEmpty(entity, entity.getBoundingBox().offset(distance));
    }
}
