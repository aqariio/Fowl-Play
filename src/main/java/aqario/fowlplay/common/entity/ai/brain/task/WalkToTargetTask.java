package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WalkToTargetTask extends MultiTickTask<MobEntity> {
    private static final int MAX_UPDATE_COUNTDOWN = 80;
    private int pathUpdateCountdownTicks;
    @Nullable
    private Path path;
    @Nullable
    private BlockPos lookTargetPos;
    private float speed;

    public WalkToTargetTask() {
        this(150, 250);
    }

    public WalkToTargetTask(int minRunTime, int maxRunTime) {
        super(
            ImmutableMap.of(
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleState.REGISTERED,
                MemoryModuleType.PATH,
                MemoryModuleState.VALUE_ABSENT,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleState.VALUE_PRESENT,
                FowlPlayMemoryModuleType.TELEPORT_TARGET,
                MemoryModuleState.VALUE_ABSENT
            ),
            minRunTime,
            maxRunTime
        );
    }

    protected boolean shouldRun(ServerWorld world, MobEntity entity) {
        if (this.pathUpdateCountdownTicks > 0) {
            this.pathUpdateCountdownTicks--;
            return false;
        }
        Brain<?> brain = entity.getBrain();
        WalkTarget walkTarget = brain.getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET).get();
        boolean reachedTarget = hasReached(entity, walkTarget);
        if (!reachedTarget && this.hasFinishedPath(entity, walkTarget, world.getTime())) {
            this.lookTargetPos = walkTarget.getLookTarget().getBlockPos();
            return true;
        }
        brain.forget(MemoryModuleType.WALK_TARGET);
        if (reachedTarget) {
            brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        }

        return false;
    }

    protected boolean shouldKeepRunning(ServerWorld world, MobEntity entity, long l) {
        if (this.path == null || this.lookTargetPos == null) {
            return false;
        }
        Optional<WalkTarget> walkTarget = entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET);
        boolean isTargetSpectator = walkTarget.map(WalkToTargetTask::isTargetSpectator).orElse(false);
        EntityNavigation navigation = entity.getNavigation();
        return !navigation.isIdle() && walkTarget.isPresent() && !hasReached(entity, walkTarget.get()) && !isTargetSpectator;
    }

    protected void finishRunning(ServerWorld world, MobEntity entity, long l) {
        if (entity.getBrain().hasMemoryModule(MemoryModuleType.WALK_TARGET)
            && !hasReached(entity, entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET).get())
            && entity.getNavigation().isNearPathStartPos()) {
            this.pathUpdateCountdownTicks = world.getRandom().nextInt(MAX_UPDATE_COUNTDOWN);
        }

        entity.getNavigation().stop();
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        entity.getBrain().forget(MemoryModuleType.PATH);
        this.path = null;
    }

    protected void run(ServerWorld world, MobEntity entity, long l) {
        entity.getBrain().remember(MemoryModuleType.PATH, this.path);
        entity.getNavigation().startMovingAlong(this.path, this.speed);
    }

    protected void keepRunning(ServerWorld world, MobEntity entity, long l) {
        Path path = entity.getNavigation().getCurrentPath();
        Brain<?> brain = entity.getBrain();
        if (this.path != path) {
            this.path = path;
            brain.remember(MemoryModuleType.PATH, path);
        }

        if (path != null && this.lookTargetPos != null) {
            WalkTarget walkTarget = brain.getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET).get();
            if (walkTarget.getLookTarget().getBlockPos().getSquaredDistance(this.lookTargetPos) > 4.0 && this.hasFinishedPath(entity, walkTarget, world.getTime())) {
                this.lookTargetPos = walkTarget.getLookTarget().getBlockPos();
                this.run(world, entity, l);
            }
        }
    }

    private boolean hasFinishedPath(MobEntity entity, WalkTarget walkTarget, long time) {
        BlockPos targetPos = walkTarget.getLookTarget().getBlockPos();
        this.path = entity.getNavigation().findPathTo(targetPos, 0);
        this.speed = walkTarget.getSpeed();
        Brain<?> brain = entity.getBrain();
        if (hasReached(entity, walkTarget)) {
            brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        }
        else {
            boolean bl = this.path != null && this.path.reachesTarget();
            if (bl) {
                brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            }
            else if (!brain.hasMemoryModule(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
                brain.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
            }

            if (this.path != null) {
                return true;
            }

            Vec3d target = NoPenaltyTargeting.findTo(
                (PathAwareEntity) entity,
                24,
                16,
                Vec3d.ofBottomCenter(targetPos),
                (float) (Math.PI / 4)
            );

            if (target != null) {
                this.path = entity.getNavigation().findPathTo(target.x, target.y, target.z, 0);
                return this.path != null;
            }
        }

        return false;
    }

    private static boolean hasReached(MobEntity entity, WalkTarget walkTarget) {
        return walkTarget.getLookTarget().getBlockPos().getManhattanDistance(entity.getBlockPos()) <= walkTarget.getCompletionRange();
    }

    private static boolean isTargetSpectator(WalkTarget target) {
        return target.getLookTarget() instanceof EntityLookTarget entityLookTarget && entityLookTarget.getEntity().isSpectator();
    }
}
