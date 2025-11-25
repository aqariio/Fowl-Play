package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.common.util.ChickenAnimationHolder;
import aqario.fowlplay.core.FowlPlayBuiltInRegistries;
import aqario.fowlplay.core.platform.DataAttachmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        switch(spawnReason) {
            case BREEDING ->
                FowlPlayBuiltInRegistries.CHICKEN_VARIANT.getHolder(ChickenVariant.WHITE).ifPresent(this::setVariant);
            case CHUNK_GENERATION ->
                FowlPlayBuiltInRegistries.CHICKEN_VARIANT.getHolder(ChickenVariant.RED_JUNGLEFOWL).ifPresent(this::setVariant);
            default -> FowlPlayBuiltInRegistries.CHICKEN_VARIANT.getRandom(world.getRandom()).ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

//    @Inject(
//        method = "createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/ChickenEntity;",
//        at = @At("HEAD"),
//        cancellable = true
//    )
//    private void fowlplay$createChild(ServerWorld serverWorld, PassiveEntity otherParent, CallbackInfoReturnable<ChickenEntity> cir) {
//        ChickenEntity chicken = EntityType.CHICKEN.create(serverWorld);
//        if(chicken != null) {
//            FowlPlayRegistries.CHICKEN_VARIANT.getEntry(ChickenVariant.WHITE).ifPresent(
//                variant ->
//                    DataAttachmentHelper.setChickenVariant(chicken, variant)
//            );
//        }
//        cir.setReturnValue(chicken);
//    }

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
