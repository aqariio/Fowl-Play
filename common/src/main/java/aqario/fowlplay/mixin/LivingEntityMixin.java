package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> variant, World world) {
        super(variant, world);
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    private float fowlplay$modifySlipperiness(Block block) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PenguinEntity penguin && penguin.isSliding()) {
            return block.getDefaultState().isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON) || this.getBlockStateAtPos().isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON) ? 1.025F : block.getSlipperiness();
        }
        return block.getSlipperiness();
    }

    @Inject(method = "getAirSpeed", at = @At("RETURN"), cancellable = true)
    private void fowlplay$increaseAirSpeed(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PenguinEntity penguin && penguin.getPrimaryPassenger() instanceof PlayerEntity) {
            cir.setReturnValue(entity.getMovementSpeed() * 0.216f);
        }
    }

    @Inject(method = "travelControlled", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d;)V"))
    private void fowlplay$stepDownwards(PlayerEntity player, Vec3d input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PenguinEntity penguin && penguin.getPrimaryPassenger() instanceof PlayerEntity && penguin.shouldStepDown()) {
            entity.addVelocityInternal(new Vec3d(0, -0.5, 0));
        }
    }
}
