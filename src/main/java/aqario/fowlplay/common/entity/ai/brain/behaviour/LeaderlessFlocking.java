package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FPMemoryTypes;
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
            FPMemoryTypes.NEAREST_VISIBLE_ADULTS.get()
        )
        .absent(
            FPMemoryTypes.IS_AVOIDING.get(),
            FPMemoryTypes.SEES_FOOD.get()
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
        if(!BrainUtils.hasMemory(brain, FPMemoryTypes.NEAREST_VISIBLE_ADULTS.get())) {
            return false;
        }
        this.nearbyBirds = BrainUtils.getMemory(brain, FPMemoryTypes.NEAREST_VISIBLE_ADULTS.get());
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

        for(AgeableMob entity : this.nearbyBirds) {
            Vec3 entityPos = entity.position();
            double offsetX = entityPos.x - birdPos.x;
            double offsetY = entityPos.y - birdPos.y;
            double offsetZ = entityPos.z - birdPos.z;
            if(Math.sqrt(offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ) < this.separationRange) {
                separationX -= offsetX;
                separationY -= offsetY;
                separationZ -= offsetZ;
            }
            Vec3 velocity = entity.getDeltaMovement();
            alignmentX += velocity.x;
            alignmentY += velocity.y;
            alignmentZ += velocity.z;
            cohesionX += entityPos.x;
            cohesionY += entityPos.y;
            cohesionZ += entityPos.z;
        }

        float scale = 1f / this.nearbyBirds.size();
        alignmentX *= scale;
        alignmentY *= scale;
        alignmentZ *= scale;
        cohesionX = cohesionX * scale - birdPos.x;
        cohesionY = cohesionY * scale - birdPos.y;
        cohesionZ = cohesionZ * scale - birdPos.z;

        cohesionX *= this.coherence;
        cohesionY *= this.coherence;
        cohesionZ *= this.coherence;
        alignmentX *= this.alignment;
        alignmentY *= this.alignment;
        alignmentZ *= this.alignment;
        separationX *= this.separation;
        separationY *= this.separation;
        separationZ *= this.separation;
        double randomX = (bird.getRandom().nextFloat() - bird.getRandom().nextFloat()) * 0.5;
        double randomY = (bird.getRandom().nextFloat() - bird.getRandom().nextFloat()) * 0.5;
        double randomZ = (bird.getRandom().nextFloat() - bird.getRandom().nextFloat()) * 0.5;

        return new Vec3(
            cohesionX + separationX + alignmentX + randomX,
            cohesionY + separationY + alignmentY + randomY,
            cohesionZ + separationZ + alignmentZ + randomZ
        );
    }
}
