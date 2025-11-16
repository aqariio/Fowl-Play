package aqario.fowlplay.common.entity.ai.brain.behaviour;

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
    private List<? extends AgeableMob> nearbyBirds;

    public LeaderlessFlocking(int minFlockSize, float coherence, float alignment, float separation, float separationRange) {
        this.minFlockSize = minFlockSize;
        this.coherence = coherence;
        this.alignment = alignment;
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
        // noinspection ConstantConditions
        this.nearbyBirds.removeIf(entity -> !entity.closerThan(bird, VIEW_RADIUS));

        return this.nearbyBirds.size() > this.minFlockSize;
    }

    @Override
    protected boolean shouldKeepRunning(FlyingBirdEntity bird) {
        return this.checkExtraStartConditions((ServerLevel) bird.level(), bird);
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
