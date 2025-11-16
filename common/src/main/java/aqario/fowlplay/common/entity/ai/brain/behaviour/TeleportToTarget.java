package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class TeleportToTarget extends ExtendedBehaviour<BirdEntity> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .present(FowlPlayMemoryModuleType.TELEPORT_TARGET.get());

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean shouldKeepRunning(BirdEntity entity) {
        return BrainUtils.hasMemory(entity, FowlPlayMemoryModuleType.TELEPORT_TARGET.get());
    }

    @Override
    protected void tick(BirdEntity entity) {
        Brain<?> brain = entity.getBrain();
        if(this.tryTeleport(entity, brain)) {
            BrainUtils.clearMemory(brain, FowlPlayMemoryModuleType.TELEPORT_TARGET.get());
        }
    }

    private boolean tryTeleport(BirdEntity entity, Brain<?> brain) {
        if(!BrainUtils.hasMemory(brain, FowlPlayMemoryModuleType.TELEPORT_TARGET.get())) {
            return false;
        }
        // noinspection ConstantConditions
        Entity target = BrainUtils.getMemory(brain, FowlPlayMemoryModuleType.TELEPORT_TARGET.get()).entity();
        BlockPos pos = target.getBlockPos();

        for(int i = 0; i < 10; i++) {
            int j = entity.getRandom().nextBetween(-3, 3);
            int k = entity.getRandom().nextBetween(-3, 3);
            if(Math.abs(j) >= 2 || Math.abs(k) >= 2) {
                int l = entity.getRandom().nextBetween(-1, 1);
                if(this.tryTeleportTo(entity, pos.getX() + j, pos.getY() + l, pos.getZ() + k)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean tryTeleportTo(BirdEntity entity, int x, int y, int z) {
        if(!this.canTeleportTo(entity, new BlockPos(x, y, z))) {
            return false;
        }

        entity.refreshPositionAndAngles(x + 0.5, y, z + 0.5, entity.getYaw(), entity.getPitch());
        entity.getNavigation().stop();
        return true;
    }

    private boolean canTeleportTo(BirdEntity entity, BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(entity.getWorld(), pos.mutableCopy());
        if(pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        BlockPos distance = pos.subtract(entity.getBlockPos());
        return entity.getWorld().isSpaceEmpty(entity, entity.getBoundingBox().offset(distance));
    }
}
