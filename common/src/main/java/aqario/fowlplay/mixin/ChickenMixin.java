package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.bird.ChickenVariant;
import aqario.fowlplay.common.util.ChickenAnimationHolder;
import aqario.fowlplay.core.FowlPlayBuiltInRegistries;
import aqario.fowlplay.core.platform.DataAttachmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Chicken.class, priority = 999)
public abstract class ChickenMixin extends Animal implements VariantHolder<Holder<ChickenVariant>>, ChickenAnimationHolder {
    @Unique
    private final AnimationState fowlplay$standingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$flappingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$swimmingState = new AnimationState();

    protected ChickenMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        FowlPlayBuiltInRegistries.CHICKEN_VARIANT
            .getHolder(ChickenVariant.RED_JUNGLEFOWL)
            .ifPresent(this::setVariant);

        return super.finalizeSpawn(level, difficulty, spawnReason, entityData);
    }

//    @Inject(
//        method = "registerGoals",
//        at = @At("HEAD"),
//        cancellable = true
//    )
//    private void fowlplay$removeGoals(CallbackInfo ci) {
//        this.goalSelector.removeAllGoals(goal -> true);
//        this.targetSelector.removeAllGoals(goal -> true);
//        ci.cancel();
//    }

    @Inject(
        method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/Chicken;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void fowlplay$createChild(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<Chicken> cir) {
        Chicken child = EntityType.CHICKEN.create(level);
        if(child != null) {
            FowlPlayBuiltInRegistries.CHICKEN_VARIANT.getHolder(ChickenVariant.WHITE).ifPresent(
                variant -> DataAttachmentHelper.setChickenVariant(child, variant)
            );
        }
        cir.setReturnValue(child);
    }

    @Override
    public Holder<ChickenVariant> getVariant() {
        return DataAttachmentHelper.getChickenVariant((Chicken) (Object) this);
    }

    @Override
    public void setVariant(Holder<ChickenVariant> variant) {
        DataAttachmentHelper.setChickenVariant((Chicken) (Object) this, variant);
    }

    @Override
    public void tick() {
        if(this.level().isClientSide()) {
            this.fowlplay$standingState.animateWhen(this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
            this.fowlplay$flappingState.animateWhen(!this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
            this.fowlplay$swimmingState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        }
        super.tick();
        if(!this.level().isClientSide()) {
            DataAttachmentHelper.sendChickenVariantUpdate((Chicken) (Object) this);
        }
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
    public AnimationState fowlplay$getFloatingState() {
        return this.fowlplay$swimmingState;
    }
}
