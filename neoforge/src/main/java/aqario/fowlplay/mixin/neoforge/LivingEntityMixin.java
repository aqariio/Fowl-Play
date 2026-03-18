package aqario.fowlplay.mixin.neoforge;

import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 1001)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> variant, Level world) {
        super(variant, world);
    }

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private float fowlplay$modifySlipperiness(float slipperiness) {
        LivingEntity entity = (LivingEntity) (Object) this;
        BlockState state = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
        if(entity instanceof PenguinEntity penguin && penguin.isSliding()) {
            return state.is(FowlPlayBlockTags.PENGUINS_SLIDE_ON) || this.getInBlockState().is(FowlPlayBlockTags.PENGUINS_SLIDE_ON)
                ? 1.025F
                : slipperiness;
        }
        return slipperiness;
    }

    @Inject(method = "getFlyingSpeed", at = @At("RETURN"), cancellable = true)
    private void fowlplay$increaseAirSpeed(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(entity instanceof PenguinEntity penguin && penguin.getControllingPassenger() instanceof Player) {
            cir.setReturnValue(entity.getSpeed() * 0.216f);
        }
    }

    @Inject(method = "travelRidden", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;travel(Lnet/minecraft/world/phys/Vec3;)V"))
    private void fowlplay$stepDownwards(Player player, Vec3 input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(entity instanceof PenguinEntity penguin && penguin.getControllingPassenger() instanceof Player && penguin.shouldStepDown()) {
            entity.addDeltaMovement(new Vec3(0, -0.5, 0));
        }
    }
}
