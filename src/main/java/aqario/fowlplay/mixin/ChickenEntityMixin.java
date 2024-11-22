package aqario.fowlplay.mixin;

import aqario.fowlplay.common.util.ChickenAnimationStates;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity implements ChickenAnimationStates {
    @Unique
    private final AnimationState idleState = new AnimationState();
    @Unique
    private final AnimationState flapState = new AnimationState();
    @Unique
    private final AnimationState floatState = new AnimationState();

    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            this.idleState.setRunning(this.isOnGround() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.flapState.setRunning(!this.isOnGround() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.floatState.setRunning(this.isInsideWaterOrBubbleColumn(), this.age);
        }
        super.tick();
    }

    @Override
    public AnimationState fowlplay$getIdleState() {
        return this.idleState;
    }

    @Override
    public AnimationState fowlplay$getFlapState() {
        return this.flapState;
    }

    @Override
    public AnimationState fowlplay$getFloatState() {
        return this.floatState;
    }
}
