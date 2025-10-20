package aqario.fowlplay.mixin;

import aqario.fowlplay.common.util.Birds;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.WallBlock;
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

import java.util.Map;

@Mixin(WallBlock.class)
public class WallBlockMixin {
    @Shadow
    @Final
    private Map<BlockState, VoxelShape> collisionShapeMap;

    @Shadow
    @Final
    private Map<BlockState, VoxelShape> shapeMap;

    @Inject(method = "getCollisionShape", at = @At(value = "RETURN"), cancellable = true)
    private void fowlplay$lowerWallHeight(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if(context instanceof EntityShapeContext entityContext
            && entityContext.getEntity() != null
            && Birds.isNotFlightless(entityContext.getEntity())
        ) {
            VoxelShape originalShape = this.collisionShapeMap.get(state);
            if(originalShape.getMax(Direction.Axis.Y) > 1) {
                cir.setReturnValue(VoxelShapes.cuboid(
                    originalShape.getMin(Direction.Axis.X),
                    originalShape.getMin(Direction.Axis.Y),
                    originalShape.getMin(Direction.Axis.Z),
                    originalShape.getMax(Direction.Axis.X),
                    this.shapeMap.get(state).getMax(Direction.Axis.Y),
                    originalShape.getMax(Direction.Axis.Z)
                ));
            }
        }
    }
}
