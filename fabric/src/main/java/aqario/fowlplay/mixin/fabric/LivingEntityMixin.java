package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 1001)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> variant, World world) {
        super(variant, world);
    }

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private float fowlplay$modifySlipperiness(float slipperiness) {
        LivingEntity entity = (LivingEntity) (Object) this;
        BlockState state = this.getWorld().getBlockState(this.getVelocityAffectingPos());
        if(entity instanceof PenguinEntity penguin && penguin.isSliding()) {
            return state.isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON) || this.getBlockStateAtPos().isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON)
                ? 1.025F
                : slipperiness;
        }
        return slipperiness;
    }

    @Inject(method = "getOffGroundSpeed", at = @At("RETURN"), cancellable = true)
    private void fowlplay$increaseAirSpeed(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(entity instanceof PenguinEntity penguin && penguin.getControllingPassenger() instanceof PlayerEntity) {
            cir.setReturnValue(entity.getMovementSpeed() * 0.216f);
        }
    }

    @Inject(method = "travelControlled", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d;)V"))
    private void fowlplay$stepDownwards(PlayerEntity player, Vec3d input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(entity instanceof PenguinEntity penguin && penguin.getControllingPassenger() instanceof PlayerEntity && penguin.shouldStepDown()) {
            entity.addVelocity(new Vec3d(0, -0.5, 0));
        }
    }
}
