package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.bird.VariantHolder;
import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.common.registry.CommonRegistry;
import aqario.fowlplay.common.util.ChickenAnimationHolder;
import aqario.fowlplay.core.FPBuiltInRegistries;
import aqario.fowlplay.core.FPEntityDataSerializers;
import aqario.fowlplay.core.FPRegistries;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Chicken.class, priority = 999)
public abstract class ChickenMixin extends Animal implements VariantHolder<ChickenVariant>, ChickenAnimationHolder {
    @Unique
    private static final EntityDataAccessor<ChickenVariant> fowlplay$VARIANT = SynchedEntityData.defineId(
        Chicken.class,
        FPEntityDataSerializers.CHICKEN_VARIANT
    );
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        this.setVariant(ChickenVariant.RED_JUNGLEFOWL.get());

        return super.finalizeSpawn(level, difficulty, spawnReason, entityData, entityNbt);
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
            ((ChickenMixin) (Object) child).setVariant(ChickenVariant.WHITE.get());
        }
        cir.setReturnValue(child);
    }

    @Override
    public CommonRegistry<ChickenVariant> variantRegistry() {
        return FPBuiltInRegistries.CHICKEN_VARIANT.get();
    }

    @Override
    public ResourceKey<Registry<ChickenVariant>> variantRegistryKey() {
        return FPRegistries.CHICKEN_VARIANT;
    }

    @Override
    public ChickenVariant defaultVariant() {
        return ChickenVariant.WHITE.get();
    }

    @Override
    public ChickenVariant getVariant() {
        return this.entityData.get(fowlplay$VARIANT);
    }

    @Override
    public void setVariant(ChickenVariant variant) {
        this.entityData.set(fowlplay$VARIANT, variant);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(fowlplay$VARIANT, ChickenVariant.WHITE.get());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void fowlplay$readCustomVariant(CompoundTag nbt, CallbackInfo ci) {
        this.readVariant(nbt);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void fowlplay$writeCustomVariant(CompoundTag nbt, CallbackInfo ci) {
        this.writeVariant(nbt);
    }

    @Override
    public void tick() {
        if(this.level().isClientSide()) {
            this.fowlplay$standingState.animateWhen(this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
            this.fowlplay$flappingState.animateWhen(!this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
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
    public AnimationState fowlplay$getFloatingState() {
        return this.fowlplay$swimmingState;
    }
}
