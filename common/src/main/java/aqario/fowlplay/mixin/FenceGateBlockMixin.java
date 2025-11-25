package aqario.fowlplay.mixin;

import aqario.fowlplay.common.util.Birds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FenceGateBlock.class)
public abstract class FenceGateBlockMixin extends HorizontalDirectionalBlock {
    @Shadow
    @Final
    protected static VoxelShape X_COLLISION_SHAPE;
    @Shadow
    @Final
    protected static VoxelShape Z_COLLISION_SHAPE;

    protected FenceGateBlockMixin(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Inject(method = "getCollisionShape", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void fowlplay$lowerFenceGateHeight(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if(context instanceof EntityCollisionContext entityContext
            && entityContext.getEntity() != null
            && Birds.isNotFlightless(entityContext.getEntity())
        ) {
            switch(state.getValue(FACING).getAxis()) {
                case X -> cir.setReturnValue(Shapes.box(
                    X_COLLISION_SHAPE.min(Direction.Axis.X),
                    X_COLLISION_SHAPE.min(Direction.Axis.Y),
                    X_COLLISION_SHAPE.min(Direction.Axis.Z),
                    X_COLLISION_SHAPE.max(Direction.Axis.X),
                    1,
                    X_COLLISION_SHAPE.max(Direction.Axis.Z)
                ));
                case Z -> cir.setReturnValue(Shapes.box(
                    Z_COLLISION_SHAPE.min(Direction.Axis.X),
                    Z_COLLISION_SHAPE.min(Direction.Axis.Y),
                    Z_COLLISION_SHAPE.min(Direction.Axis.Z),
                    Z_COLLISION_SHAPE.max(Direction.Axis.X),
                    1,
                    Z_COLLISION_SHAPE.max(Direction.Axis.Z)
                ));
            }
        }
    }
}
