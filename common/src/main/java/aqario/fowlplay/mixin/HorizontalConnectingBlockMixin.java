package aqario.fowlplay.mixin;

import aqario.fowlplay.common.util.Birds;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorizontalConnectingBlock.class)
public abstract class HorizontalConnectingBlockMixin {
    @Shadow
    protected abstract int getShapeIndex(BlockState state);

    @Shadow
    @Final
    protected VoxelShape[] collisionShapes;

    @Inject(method = "getCollisionShape", at = @At(value = "HEAD"), cancellable = true)
    private void fowlplay$lowerFenceHeight(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        HorizontalConnectingBlock block = (HorizontalConnectingBlock) (Object) this;
        if(block instanceof FenceBlock
            && context instanceof EntityShapeContext entityContext
            && entityContext.getEntity() != null
            && Birds.isNotFlightless(entityContext.getEntity())
        ) {
            VoxelShape originalShape = this.collisionShapes[this.getShapeIndex(state)];
            if(originalShape.getMax(Direction.Axis.Y) > 1) {
                cir.setReturnValue(VoxelShapes.cuboid(
                    originalShape.getMin(Direction.Axis.X),
                    originalShape.getMin(Direction.Axis.Y),
                    originalShape.getMin(Direction.Axis.Z),
                    originalShape.getMax(Direction.Axis.X),
                    1,
                    originalShape.getMax(Direction.Axis.Z)
                ));
            }
        }
    }
}
