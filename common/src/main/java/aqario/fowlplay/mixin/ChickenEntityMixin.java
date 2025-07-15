package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.common.util.ChickenAnimationStates;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.FowlPlayTrackedDataHandlerRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity implements VariantHolder<ChickenVariant>, ChickenAnimationStates {
    @Unique
    private static final TrackedData<ChickenVariant> fowlplay$VARIANT = DataTracker.registerData(
        ChickenEntity.class,
        FowlPlayTrackedDataHandlerRegistry.CHICKEN_VARIANT
    );
    @Unique
    private final AnimationState fowlplay$standingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$flappingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$floatingState = new AnimationState();

    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if(spawnReason == SpawnReason.BREEDING) {
            this.setVariant(ChickenVariant.WHITE.get());
        }
        else if(spawnReason == SpawnReason.CHUNK_GENERATION) {
            this.setVariant(ChickenVariant.RED_JUNGLEFOWL.get());
        }
        else {
            FowlPlayRegistries.CHICKEN_VARIANT.get()
                .fowlplay$getRandom(world.getRandom())
                .ifPresent(this::setVariant);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public ChickenVariant getVariant() {
        return this.dataTracker.get(fowlplay$VARIANT);
    }

    @Override
    public void setVariant(ChickenVariant variant) {
        this.dataTracker.set(fowlplay$VARIANT, variant);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(fowlplay$VARIANT, ChickenVariant.WHITE.get());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void fowlplay$readCustomVariant(NbtCompound nbt, CallbackInfo ci) {
        ChickenVariant variant = FowlPlayRegistries.CHICKEN_VARIANT.get().fowlplay$get(Identifier.tryParse(nbt.getString("variant")));
        if(variant != null) {
            this.setVariant(variant);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void fowlplay$writeCustomVariant(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("variant", FowlPlayRegistries.CHICKEN_VARIANT.get().fowlplay$getId(this.getVariant()).toString());
    }

    @Override
    public void tick() {
        if(this.getWorld().isClient()) {
            this.fowlplay$standingState.setRunning(this.isOnGround() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.fowlplay$flappingState.setRunning(!this.isOnGround() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.fowlplay$floatingState.setRunning(this.isInsideWaterOrBubbleColumn(), this.age);
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
