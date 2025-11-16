package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class LeaderlessFlocking extends ExtendedBehaviour<FlyingBirdEntity> {
    private static final MemoryList MEMORIES = MemoryList.create(3)
        .present(
            FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get()
        )
        .absent(
            FowlPlayMemoryModuleType.IS_AVOIDING.get(),
            FowlPlayMemoryModuleType.SEES_FOOD.get()
        );
    private static final int VIEW_RADIUS = 24;
    public final int minFlockSize;
    public final float coherence;
    public final float alignment;
    public final float separation;
    public final float separationRange;
    private List<? extends PassiveEntity> nearbyBirds;

    public LeaderlessFlocking(int minFlockSize, float coherence, float alignment, float separation, float separationRange) {
        this.minFlockSize = minFlockSize;
        this.coherence = coherence;
        this.alignment = alignment;
        this.separation = separation;
        this.separationRange = separationRange;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FlyingBirdEntity bird) {
        if(!bird.isFlying()) {
            return false;
        }
        Brain<?> brain = bird.getBrain();
        if(!BrainUtils.hasMemory(brain, FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get())) {
            return false;
        }
        this.nearbyBirds = BrainUtils.getMemory(brain, FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get());
        // noinspection ConstantConditions
        this.nearbyBirds.removeIf(entity -> !entity.isInRange(bird, VIEW_RADIUS));

        return this.nearbyBirds.size() > this.minFlockSize;
    }

    @Override
    protected boolean shouldKeepRunning(FlyingBirdEntity bird) {
        return this.shouldRun((ServerWorld) bird.getWorld(), bird);
    }

    @Override
    protected void tick(FlyingBirdEntity bird) {
        Vec3d heading = this.getHeading(bird).add(bird.getPos());
        bird.getMoveControl().moveTo(heading.x, heading.y, heading.z, (bird.getRandom().nextFloat() - bird.getRandom().nextFloat()) * 1.5 + 2);
    }

    private Vec3d getHeading(FlyingBirdEntity bird) {
        Vec3d separation = Vec3d.ZERO;
        Vec3d alignment = Vec3d.ZERO;
        Vec3d cohesion = Vec3d.ZERO;

        for(PassiveEntity entity : this.nearbyBirds) {
            if(entity.getPos().subtract(bird.getPos()).length() < this.separationRange) {
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
        Vec3d randomness = new Vec3d(
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat(),
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat(),
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat())
            .multiply(0.5);

        return cohesion.add(separation).add(alignment).add(randomness);
    }
}
