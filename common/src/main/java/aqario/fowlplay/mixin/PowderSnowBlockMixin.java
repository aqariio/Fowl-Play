package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void fowlplay$changePowderSnowShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        // this way the penguin can actually step up powder snow blocks instead of phasing into it when sliding
        if(context instanceof EntityShapeContext ctx && ctx.getEntity() instanceof PenguinEntity penguin && penguin.isSliding()) {
            cir.setReturnValue(VoxelShapes.fullCube());
        }
    }
}
