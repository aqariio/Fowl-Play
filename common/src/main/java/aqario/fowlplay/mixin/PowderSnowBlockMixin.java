package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void fowlplay$changePowderSnowShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        // this way the penguin can actually step up powder snow blocks instead of phasing into it when sliding
        if(context instanceof EntityCollisionContext ctx && ctx.getEntity() instanceof PenguinEntity penguin && penguin.isSliding()) {
            cir.setReturnValue(Shapes.block());
        }
    }
}
