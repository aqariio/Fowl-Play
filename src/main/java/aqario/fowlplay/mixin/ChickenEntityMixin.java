package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.common.entity.data.FowlPlayTrackedDataHandlerRegistry;
import aqario.fowlplay.common.registry.FowlPlayRegistries;
import aqario.fowlplay.common.registry.FowlPlayRegistryKeys;
import aqario.fowlplay.common.util.ChickenAnimationStates;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
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

import java.util.Optional;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity implements VariantHolder<RegistryEntry<ChickenVariant>>, ChickenAnimationStates {
    @Unique
    private static final TrackedData<RegistryEntry<ChickenVariant>> VARIANT = DataTracker.registerData(
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (spawnReason == SpawnReason.BREEDING) {
            FowlPlayRegistries.CHICKEN_VARIANT.getEntry(ChickenVariant.WHITE).ifPresent(this::setVariant);
        }
        else if (spawnReason == SpawnReason.NATURAL) {
            FowlPlayRegistries.CHICKEN_VARIANT.getEntry(ChickenVariant.RED_JUNGLEFOWL).ifPresent(this::setVariant);
        }
        else {
            FowlPlayRegistries.CHICKEN_VARIANT.getRandom(world.getRandom()).ifPresent(this::setVariant);
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public RegistryEntry<ChickenVariant> getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public void setVariant(RegistryEntry<ChickenVariant> variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, FowlPlayRegistries.CHICKEN_VARIANT.entryOf(ChickenVariant.WHITE));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void fowlplay$readCustomVariant(NbtCompound nbt, CallbackInfo ci) {
        Optional.ofNullable(Identifier.tryParse(nbt.getString("variant")))
            .map(variant -> RegistryKey.of(FowlPlayRegistryKeys.CHICKEN_VARIANT, variant))
            .flatMap(FowlPlayRegistries.CHICKEN_VARIANT::getEntry)
            .ifPresent(this::setVariant);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void fowlplay$writeCustomVariant(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("variant", this.getVariant().getKey().orElse(ChickenVariant.WHITE).getValue().toString());
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
