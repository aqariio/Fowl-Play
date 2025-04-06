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

@Mixin(FenceGateBlock.class)
public abstract class FenceGateBlockMixin extends HorizontalFacingBlock {
    @Shadow
    @Final
    protected static VoxelShape X_AXIS_COLLISION_SHAPE;
    @Shadow
    @Final
    protected static VoxelShape Z_AXIS_COLLISION_SHAPE;

    protected FenceGateBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getCollisionShape", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void fowlplay$lowerFenceGateHeight(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context instanceof EntityShapeContext entityContext
            && entityContext.getEntity() != null
            && Birds.notFlightless(entityContext.getEntity())
        ) {
            switch (state.get(FACING).getAxis()) {
                case X -> cir.setReturnValue(VoxelShapes.cuboid(
                    X_AXIS_COLLISION_SHAPE.getMin(Direction.Axis.X),
                    X_AXIS_COLLISION_SHAPE.getMin(Direction.Axis.Y),
                    X_AXIS_COLLISION_SHAPE.getMin(Direction.Axis.Z),
                    X_AXIS_COLLISION_SHAPE.getMax(Direction.Axis.X),
                    1,
                    X_AXIS_COLLISION_SHAPE.getMax(Direction.Axis.Z)
                ));
                case Z -> cir.setReturnValue(VoxelShapes.cuboid(
                    Z_AXIS_COLLISION_SHAPE.getMin(Direction.Axis.X),
                    Z_AXIS_COLLISION_SHAPE.getMin(Direction.Axis.Y),
                    Z_AXIS_COLLISION_SHAPE.getMin(Direction.Axis.Z),
                    Z_AXIS_COLLISION_SHAPE.getMax(Direction.Axis.X),
                    1,
                    Z_AXIS_COLLISION_SHAPE.getMax(Direction.Axis.Z)
                ));
            }
        }
    }
}
