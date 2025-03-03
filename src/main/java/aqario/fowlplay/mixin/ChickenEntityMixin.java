package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.common.entity.data.FowlPlayTrackedDataHandlerRegistry;
import aqario.fowlplay.common.registry.FowlPlayRegistries;
import aqario.fowlplay.common.util.ChickenAnimationStates;
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
    private static final TrackedData<ChickenVariant> VARIANT = DataTracker.registerData(
        ChickenEntityMixin.class,
        FowlPlayTrackedDataHandlerRegistry.CHICKEN_VARIANT
    );
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (spawnReason == SpawnReason.BREEDING) {
            this.setVariant(ChickenVariant.WHITE);
        }
        else if (spawnReason == SpawnReason.CHUNK_GENERATION) {
            this.setVariant(ChickenVariant.RED_JUNGLEFOWL);
        }
        else {
            FowlPlayRegistries.CHICKEN_VARIANT
                .getRandom(world.getRandom())
                .ifPresent(variant -> this.setVariant(variant.value()));
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public ChickenVariant getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public void setVariant(ChickenVariant variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, ChickenVariant.WHITE);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void fowlplay$readCustomVariant(NbtCompound nbt, CallbackInfo ci) {
        ChickenVariant variant = FowlPlayRegistries.CHICKEN_VARIANT.get(Identifier.tryParse(nbt.getString("variant")));
        if (variant != null) {
            this.setVariant(variant);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void fowlplay$writeCustomVariant(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("variant", FowlPlayRegistries.CHICKEN_VARIANT.getId(this.getVariant()).toString());
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
