package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class FlockTask extends MultiTickTask<FlyingBirdEntity> {
    public final float coherence;
    public final float alignment;
    public final float separation;
    public final float separationRange;
    private List<? extends PassiveEntity> nearbyBirds;

    public FlockTask(float coherence, float alignment, float separation, float separationRange) {
        super(ImmutableMap.of());
        this.coherence = coherence;
        this.alignment = alignment;
        this.separation = separation;
        this.separationRange = separationRange;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FlyingBirdEntity bird) {
        if (!bird.isFlying()) {
            return false;
        }
        if (!bird.getBrain().hasMemoryModule(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS)) {
            return false;
        }

        this.nearbyBirds = bird.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS).get();

        return this.nearbyBirds.size() > 5;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, FlyingBirdEntity bird, long time) {
        return this.shouldRun(world, bird);
    }

    @Override
    protected void keepRunning(ServerWorld world, FlyingBirdEntity bird, long time) {
        Vec3d heading = this.getHeading(bird).add(bird.getPos());
        bird.getMoveControl().moveTo(heading.x, heading.y, heading.z, (bird.getRandom().nextFloat() - bird.getRandom().nextFloat()) * 1.5 + 2);
    }

    private Vec3d getHeading(FlyingBirdEntity bird) {
        Vec3d separation = Vec3d.ZERO;
        Vec3d alignment = Vec3d.ZERO;
        Vec3d cohesion = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            if (entity.getPos().subtract(bird.getPos()).length() < this.separationRange) {
                separation = separation.subtract(entity.getPos().subtract(bird.getPos()));
            }
            alignment = alignment.add(entity.getVelocity());
            cohesion = cohesion.add(entity.getPos());
        }

        alignment = alignment.multiply(1f / this.nearbyBirds.size());
        cohesion = cohesion.multiply(1f / this.nearbyBirds.size());
        cohesion = cohesion.subtract(bird.getPos());

        cohesion = cohesion.multiply(this.coherence);
        alignment = alignment.multiply(this.alignment);
        separation = separation.multiply(this.separation);

        return cohesion.add(separation).add(alignment);
    }
}
