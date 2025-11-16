package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.Flocking;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class GuidedFlocking extends ExtendedBehaviour<FlyingBirdEntity> {
    private static final MemoryList MEMORIES = MemoryList.create(3)
        .present(
            FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get()
        )
        .absent(
            FowlPlayMemoryModuleType.IS_AVOIDING.get(),
            FowlPlayMemoryModuleType.SEES_FOOD.get()
        );
    private static final int VIEW_RADIUS = 8;
    public float coherence;
    public float alignment;
    public final float separation;
    public final float separationRange;
    private FlyingBirdEntity leader;
    private List<? extends AgeableMob> nearbyBirds;

    public GuidedFlocking(float separation, float separationRange) {
        this.separation = separation;
        this.separationRange = separationRange;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, FlyingBirdEntity bird) {
        if(!bird.isFlying()) {
            return false;
        }
        Brain<?> brain = bird.getBrain();
        if(!BrainUtils.hasMemory(brain, FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get())) {
            return false;
        }
        this.nearbyBirds = BrainUtils.getMemory(brain, FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get());
        assert this.nearbyBirds != null;
        this.nearbyBirds.removeIf(entity -> entity.distanceToSqr(bird) > VIEW_RADIUS * VIEW_RADIUS);
        if(this.nearbyBirds.isEmpty()) {
            return false;
        }

        this.leader = null;
        this.nearbyBirds.forEach(entity -> {
            if(entity instanceof Flocking flockingBird && flockingBird.isLeader()) {
                this.leader = (FlyingBirdEntity) flockingBird;
            }
        });

        if(this.leader == null) {
            ((Flocking) bird).setLeader();
            return false;
        }

        return true;
    }

    @Override
    protected boolean canStillUse(ServerLevel world, FlyingBirdEntity bird, long time) {
        return this.checkExtraStartConditions(world, bird);
    }

    @Override
    protected void tick(FlyingBirdEntity bird) {
        Vec3 heading = this.getHeading(bird).add(bird.position());
        bird.getMoveControl().setWantedPosition(heading.x, heading.y, heading.z, (bird.getRandom().nextFloat() - bird.getRandom().nextFloat()) * 1.5 + 2);
    }

    private Vec3 getHeading(FlyingBirdEntity bird) {
        Vec3 separation = Vec3.ZERO;
        Vec3 alignment = Vec3.ZERO;
        Vec3 cohesion = Vec3.ZERO;

        for(AgeableMob entity : this.nearbyBirds) {
            if(entity.position().subtract(bird.position()).length() < this.separationRange) {
                separation = separation.subtract(entity.position().subtract(bird.position()));
            }
            alignment = alignment.add(entity.getDeltaMovement());
            cohesion = cohesion.add(entity.position());
        }

        alignment = alignment.scale(1f / this.nearbyBirds.size());
        cohesion = cohesion.scale(1f / this.nearbyBirds.size());
        cohesion = cohesion.subtract(bird.position());

        cohesion = cohesion.scale(this.coherence);
        alignment = alignment.scale(this.alignment);
        separation = separation.scale(this.separation);
        Vec3 randomness = new Vec3(
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat(),
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat(),
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat())
            .scale(0.5);

        return cohesion.add(separation).add(alignment).add(randomness);
    }
}
