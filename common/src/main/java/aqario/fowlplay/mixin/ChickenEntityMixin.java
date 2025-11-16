package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.common.util.ChickenAnimationStates;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.FowlPlayTrackedDataHandlerRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
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

@Mixin(value = Chicken.class, priority = 999)
public abstract class ChickenEntityMixin extends Animal implements VariantHolder<ChickenVariant>, ChickenAnimationStates {
    @Unique
    private static final EntityDataAccessor<ChickenVariant> fowlplay$VARIANT = SynchedEntityData.defineId(
        Chicken.class,
        FowlPlayTrackedDataHandlerRegistry.CHICKEN_VARIANT
    );
    @Unique
    private final AnimationState fowlplay$standingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$flappingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$floatingState = new AnimationState();

    protected ChickenEntityMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        switch(spawnReason) {
            case BREEDING -> this.setVariant(ChickenVariant.WHITE.get());
            case CHUNK_GENERATION -> this.setVariant(ChickenVariant.RED_JUNGLEFOWL.get());
            default -> FowlPlayRegistries.CHICKEN_VARIANT.get()
                .fowlplay$getRandom(world.getRandom())
                .ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
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
        ChickenVariant variant = FowlPlayRegistries.CHICKEN_VARIANT.get().fowlplay$get(ResourceLocation.tryParse(nbt.getString("variant")));
        if(variant != null) {
            this.setVariant(variant);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void fowlplay$writeCustomVariant(CompoundTag nbt, CallbackInfo ci) {
        nbt.putString("variant", FowlPlayRegistries.CHICKEN_VARIANT.get().fowlplay$getId(this.getVariant()).toString());
    }

    @Override
    public void tick() {
        if(this.level().isClientSide()) {
            this.fowlplay$standingState.animateWhen(this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
            this.fowlplay$flappingState.animateWhen(!this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
            this.fowlplay$floatingState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
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
        return this.fowlplay$floatingState;
    }
}
