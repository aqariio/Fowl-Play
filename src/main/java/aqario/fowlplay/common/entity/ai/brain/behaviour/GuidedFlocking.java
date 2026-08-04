package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.Flocking;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FPMemoryTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

public class GuidedFlocking<E extends FlyingBirdEntity & Flocking> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(3)
        .present(
            FPMemoryTypes.NEAREST_VISIBLE_ADULTS.get()
        )
        .absent(
            FPMemoryTypes.IS_AVOIDING.get(),
            FPMemoryTypes.NEAREST_FOOD_ITEM.get()
        );
    private static final int VIEW_RADIUS = 8;
    private static final int MAX_NEIGHBOURS = 8;
    private static final int STEERING_INTERVAL = 4;
    public float coherence;
    public float alignment;
    public final float separation;
    public final float separationRange;
    private FlyingBirdEntity leader;
    private List<? extends AgeableMob> nearbyBirds;
    private int nextSteeringTick;

    public GuidedFlocking(float separation, float separationRange) {
        this.separation = separation;
        this.separationRange = separationRange;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, E bird) {
        if(!bird.isFlying()) {
            return false;
        }
        if(!bird.isMemoryPresent(FPMemoryTypes.NEAREST_VISIBLE_ADULTS.get())) {
            return false;
        }
        this.nearbyBirds = bird.getPresentMemory(FPMemoryTypes.NEAREST_VISIBLE_ADULTS.get());
        if(this.leader != null
            && this.leader.isAlive()
            && this.leader.distanceToSqr(bird) <= VIEW_RADIUS * VIEW_RADIUS
        ) {
            return true;
        }
        this.leader = null;
        for(AgeableMob entity : this.nearbyBirds) {
            if(entity.distanceToSqr(bird) <= VIEW_RADIUS * VIEW_RADIUS
                && entity instanceof Flocking flockingBird
                && flockingBird.isLeader()
            ) {
                this.leader = (FlyingBirdEntity) flockingBird;
                break;
            }
        }

        if(this.leader == null) {
            bird.setLeader();
            return false;
        }

        return true;
    }

    @Override
    protected boolean canStillUse(ServerLevel world, E bird, long time) {
        return this.checkExtraStartConditions(world, bird);
    }

    @Override
    protected void tick(E bird) {
        if(bird.tickCount < this.nextSteeringTick) {
            return;
        }
        this.nextSteeringTick = bird.tickCount + STEERING_INTERVAL;
        Vec3 heading = this.getHeading(bird).add(bird.position());
        bird.getMoveControl().setWantedPosition(heading.x, heading.y, heading.z, (bird.getRandom().nextFloat() - bird.getRandom().nextFloat()) * 1.5 + 2);
    }

    private Vec3 getHeading(E bird) {
        Vec3 birdPos = bird.position();
        double separationX = 0;
        double separationY = 0;
        double separationZ = 0;
        double alignmentX = 0;
        double alignmentY = 0;
        double alignmentZ = 0;
        double cohesionX = 0;
        double cohesionY = 0;
        double cohesionZ = 0;
        int count = 0;
        double separationRangeSqr = this.separationRange * this.separationRange;
        for(AgeableMob entity : this.nearbyBirds) {
            double distanceSqr = entity.distanceToSqr(bird);
            if(distanceSqr > VIEW_RADIUS * VIEW_RADIUS) {
                continue;
            }
            Vec3 entityPos = entity.position();
            double dx = entityPos.x - birdPos.x;
            double dy = entityPos.y - birdPos.y;
            double dz = entityPos.z - birdPos.z;
            if(distanceSqr < separationRangeSqr) {
                separationX -= dx;
                separationY -= dy;
                separationZ -= dz;
            }
            Vec3 movement = entity.getDeltaMovement();
            alignmentX += movement.x;
            alignmentY += movement.y;
            alignmentZ += movement.z;
            cohesionX += entityPos.x;
            cohesionY += entityPos.y;
            cohesionZ += entityPos.z;
            if(++count >= MAX_NEIGHBOURS) {
                break;
            }
        }
        if(count == 0) {
            return Vec3.ZERO;
        }
        double inverseCount = 1.0 / count;
        Vec3 alignment = new Vec3(alignmentX, alignmentY, alignmentZ).scale(inverseCount * this.alignment);
        Vec3 cohesion = new Vec3(
            cohesionX * inverseCount - birdPos.x,
            cohesionY * inverseCount - birdPos.y,
            cohesionZ * inverseCount - birdPos.z
        ).scale(this.coherence);
        Vec3 separation = new Vec3(separationX, separationY, separationZ).scale(this.separation);
        Vec3 randomness = new Vec3(
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat(),
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat(),
            bird.getRandom().nextFloat() - bird.getRandom().nextFloat())
            .scale(0.5);

        return cohesion.add(separation).add(alignment).add(randomness);
    }
}
