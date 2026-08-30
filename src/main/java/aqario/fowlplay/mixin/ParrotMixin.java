//package aqario.fowlplay.mixin;
//
//import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
//import aqario.fowlplay.common.entity.ai.brain.ExtendedBrainProvider;
//import aqario.fowlplay.common.entity.bird.VariantHolder;
//import aqario.fowlplay.common.util.ParrotAnimationHolder;
//import aqario.fowlplay.common.util.TargetingUtils;
//import it.unimi.dsi.fastutil.objects.ObjectArrayList;
//import net.minecraft.world.entity.AnimationState;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.ai.Brain;
//import net.minecraft.world.entity.animal.FlyingAnimal;
//import net.minecraft.world.entity.animal.Parrot;
//import net.minecraft.world.entity.animal.ShoulderRidingEntity;
//import net.minecraft.world.level.Level;
//import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
//import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import java.util.List;
//
//@Mixin(value = Parrot.class, priority = 999)
//public abstract class ParrotMixin extends ShoulderRidingEntity implements VariantHolder<Parrot.Variant>, FlyingAnimal, ParrotAnimationHolder, BirdBrain<ParrotMixin> {
//    @Unique
//    private final AnimationState fowlplay$standingState = new AnimationState();
//    @Unique
//    private final AnimationState fowlplay$perchingState = new AnimationState();
//    @Unique
//    private final AnimationState fowlplay$glidingState = new AnimationState();
//    @Unique
//    private final AnimationState fowlplay$swimmingState = new AnimationState();
//
//    protected ParrotMixin(EntityType<? extends ShoulderRidingEntity> entityType, Level level) {
//        super(entityType, level);
//    }
//
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
//
//    @Override
//    protected Brain.Provider<ParrotMixin> brainProvider() {
//        return new ExtendedBrainProvider<>(this);
//    }
//
//    @Override
//    public List<? extends ExtendedSensor<? extends ParrotMixin>> getSensors() {
//        return ObjectArrayList.of(
////            new NearbyLivingEntitySensor<ParrotMixin>()
////                .setRadius(24),
////            new NearbyPlayersSensor<ParrotMixin>()
////                .setRadius(24),
////            new NearbyFoodSensor<>(),
////            new NearbyAdultsSensor<>(),
////            new InWaterSensor<>(),
////            new AttackedSensor<>(),
////            new AvoidTargetSensor<>()
//        );
//    }
//
//    @Override
//    public BrainActivityGroup<? extends ParrotMixin> coreActivity() {
//        return BirdBrain.super.coreActivity();
//    }
//
//    @Override
//    public BrainActivityGroup<? extends ParrotMixin> avoidActivity() {
//        return BirdBrain.super.avoidActivity();
//    }
//
//    @Override
//    public BrainActivityGroup<? extends ParrotMixin> idleActivity() {
//        return BirdBrain.super.idleActivity();
//    }
//
//    @Override
//    public BrainActivityGroup<? extends ParrotMixin> restActivity() {
//        return BirdBrain.super.restActivity();
//    }
//
//    @Override
//    protected void customServerAiStep() {
//        this.tickBrain(this);
//        super.customServerAiStep();
//    }
//
//    @Unique
//    private boolean isPerched() {
//        return this.onGround() && TargetingUtils.isPerch(this, this.getBlockPosBelowThatAffectsMyMovement());
//    }
//
//    @Override
//    public void tick() {
//        if(this.level().isClientSide()) {
//            this.fowlplay$standingState.animateWhen(this.onGround() && !this.isPerched() && !this.isInWaterOrBubble(), this.tickCount);
//            this.fowlplay$perchingState.animateWhen(this.isPerched() && !this.isInWaterOrBubble(), this.tickCount);
//            this.fowlplay$glidingState.animateWhen(this.isFlying() && !this.isInWaterOrBubble(), this.tickCount);
//            this.fowlplay$swimmingState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
//        }
//        super.tick();
//    }
//
//    @Override
//    public AnimationState fowlplay$getStandingState() {
//        return this.fowlplay$standingState;
//    }
//
//    @Override
//    public AnimationState fowlplay$getPerchingState() {
//        return this.fowlplay$perchingState;
//    }
//
//    @Override
//    public AnimationState fowlplay$getGlidingState() {
//        return this.fowlplay$glidingState;
//    }
//
//    @Override
//    public AnimationState fowlplay$getSwimmingState() {
//        return this.fowlplay$swimmingState;
//    }
//}
