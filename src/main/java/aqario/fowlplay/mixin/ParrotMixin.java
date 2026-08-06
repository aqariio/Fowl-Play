package aqario.fowlplay.mixin;

import aqario.fowlplay.common.config.FPConfig;
import aqario.fowlplay.common.entity.bird.VariantHolder;
import aqario.fowlplay.common.util.ParrotAnimationHolder;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Parrot.class, priority = 999)
public abstract class ParrotMixin extends ShoulderRidingEntity implements VariantHolder<Parrot.Variant>, FlyingAnimal, ParrotAnimationHolder {
    @Unique
    private final AnimationState fowlplay$standingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$flappingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$swimmingState = new AnimationState();

    protected ParrotMixin(EntityType<? extends ShoulderRidingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "registerGoals",
        at = @At("HEAD"),
        cancellable = true
    )
    private void fowlplay$removeGoals(CallbackInfo ci) {
        if(FPConfig.getInstance().customParrotBehavior) {
            this.goalSelector.removeAllGoals(goal -> true);
            this.targetSelector.removeAllGoals(goal -> true);
            ci.cancel();
        }
    }

    @Override
    public void tick() {
        if(this.level().isClientSide()) {
            this.fowlplay$standingState.animateWhen(this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
            this.fowlplay$flappingState.animateWhen(this.isFlapping() && !this.isInWaterOrBubble(), this.tickCount);
            this.fowlplay$swimmingState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        }
        super.tick();
    }

    @Override
    public AnimationState fowlplay$getStandingState() {
        return this.fowlplay$standingState;
    }

    @Override
    public AnimationState fowlplay$getFlappingState() {
        return this.fowlplay$flappingState;
    }

    @Override
    public AnimationState fowlplay$getSwimmingState() {
        return this.fowlplay$swimmingState;
    }
}
