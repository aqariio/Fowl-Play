package pengugang.fowlplay.entity.ai;

import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;
import pengugang.fowlplay.entity.PenguinEntity;

import javax.annotation.Nullable;

public class SlideAroundGoal extends Goal {
    public static final int DEFAULT_CHANCE = 120;
    protected final PenguinEntity penguinEntity;
    protected final double speed;
    private final boolean canDespawn;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected int chance;
    protected boolean ignoringChance;

    public SlideAroundGoal(PenguinEntity mob, double speed, PenguinEntity penguinEntity) {
        this(mob, speed, 120);
    }

    public SlideAroundGoal(PenguinEntity mob, double speed, int chance) {
        this(mob, speed, chance, true);
    }

    public SlideAroundGoal(PenguinEntity entity, double speed, int chance, boolean canDespawn) {
        this.penguinEntity = entity;
        this.speed = speed;
        this.chance = chance;
        this.canDespawn = canDespawn;
    }

    public boolean canStart() {
        if (this.penguinEntity.hasPassengers()) {
            return false;
        } else {
            if (!this.ignoringChance) {
                if (this.canDespawn && this.penguinEntity.getDespawnCounter() >= 100) {
                    return false;
                }

                if (this.penguinEntity.getRandom().nextInt(toGoalTicks(this.penguinEntity.fleeTime > 0 ? 1 : chance)) != 0) {
                    return false;
                }
            }

            Vec3d vec3d = this.getWanderTarget();
            if (vec3d == null) {
                return false;
            } else {
                this.targetX = vec3d.x;
                this.targetY = vec3d.y;
                this.targetZ = vec3d.z;
                this.ignoringChance = false;
                return penguinEntity.isOnGround();
            }
        }
    }

    @Nullable
    protected Vec3d getWanderTarget() {
        return NoPenaltyTargeting.find(this.penguinEntity, 5, 2);
    }

    public boolean shouldContinue() {
        return !this.penguinEntity.getNavigation().isIdle() && !this.penguinEntity.hasPassengers();
    }

    public void start() {
        Vec3d targetPos = new Vec3d(targetX, targetY, targetZ);
        Vec3d dirVec = targetPos.subtract(penguinEntity.getPos()).normalize();
        Vec3d velVec = new Vec3d(dirVec.getX() * 0.6f, 0.6f, dirVec.getZ() * 0.6f);
        this.penguinEntity.setVelocity(velVec);

        penguinEntity.setBodyYaw((float) -(Math.atan2(dirVec.getX(), dirVec.getZ()) * 180f / Math.PI));
        penguinEntity.setYaw((float) -(Math.atan2(dirVec.getX(), dirVec.getZ()) * 180f / Math.PI));
    }

    public void stop() {
        super.stop();
    }

    public void ignoreChanceOnce() {
        this.ignoringChance = true;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}