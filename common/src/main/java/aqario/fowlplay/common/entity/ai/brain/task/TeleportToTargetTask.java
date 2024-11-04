package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class TeleportToTargetTask extends Task<PathAwareEntity> {

    public TeleportToTargetTask() {
        super(ImmutableMap.of(FowlPlayMemoryModuleType.TELEPORT_TARGET, MemoryModuleState.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, PathAwareEntity entity, long time) {
        return entity.getBrain().isMemoryInState(FowlPlayMemoryModuleType.TELEPORT_TARGET, MemoryModuleState.VALUE_PRESENT);
    }

    @Override
    protected void keepRunning(ServerWorld world, PathAwareEntity entity, long time) {
        if (this.tryTeleport(entity)) {
            entity.getBrain().forget(FowlPlayMemoryModuleType.TELEPORT_TARGET);
        }
    }

    private boolean tryTeleport(PathAwareEntity entity) {
        if (!entity.getBrain().hasMemoryModule(FowlPlayMemoryModuleType.TELEPORT_TARGET)) {
            return false;
        }
        Entity owner = entity.getBrain().getMemoryValue(FowlPlayMemoryModuleType.TELEPORT_TARGET).get().entity();
        BlockPos pos = owner.getBlockPos();

        for (int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(entity, -3, 3);
            int k = this.getRandomInt(entity, -1, 1);
            int l = this.getRandomInt(entity, -3, 3);
            boolean bl = this.tryTeleportTo(entity, owner, pos.getX() + j, pos.getY() + k, pos.getZ() + l);
            if (bl) {
                return true;
            }
        }

        return false;
    }

    private boolean tryTeleportTo(PathAwareEntity entity, Entity owner, int x, int y, int z) {
        if (Math.abs(x - owner.getX()) < 2.0 && Math.abs(z - owner.getZ()) < 2.0) {
            return false;
        }
        if (!this.canTeleportTo(entity, new BlockPos(x, y, z))) {
            return false;
        }
        entity.refreshPositionAndAngles(x + 0.5, y, z + 0.5, entity.getYaw(), entity.getPitch());
        entity.getNavigation().stop();
        return true;
    }

    private boolean canTeleportTo(PathAwareEntity entity, BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(entity, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        BlockPos blockPos = pos.subtract(entity.getBlockPos());
        return entity.getWorld().isSpaceEmpty(entity, entity.getBounds().offset(blockPos));
    }

    private int getRandomInt(PathAwareEntity entity, int min, int max) {
        return entity.getRandom().nextInt(max - min + 1) + min;
    }
}
