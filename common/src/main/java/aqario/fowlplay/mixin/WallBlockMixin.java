package aqario.fowlplay.mixin;

import aqario.fowlplay.common.util.BirdUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
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

import java.util.Map;

@Mixin(WallBlock.class)
public class WallBlockMixin {
    @Shadow
    @Final
    private Map<BlockState, VoxelShape> collisionShapeByIndex;

    @Shadow
    @Final
    private Map<BlockState, VoxelShape> shapeByIndex;

    @Inject(method = "getCollisionShape", at = @At(value = "RETURN"), cancellable = true)
    private void fowlplay$lowerWallHeight(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if(context instanceof EntityCollisionContext entityContext
            && entityContext.getEntity() != null
            && BirdUtils.isNotFlightless(entityContext.getEntity())
        ) {
            VoxelShape originalShape = this.collisionShapeByIndex.get(state);
            if(originalShape.max(Direction.Axis.Y) > 1) {
                cir.setReturnValue(Shapes.box(
                    originalShape.min(Direction.Axis.X),
                    originalShape.min(Direction.Axis.Y),
                    originalShape.min(Direction.Axis.Z),
                    originalShape.max(Direction.Axis.X),
                    this.shapeByIndex.get(state).max(Direction.Axis.Y),
                    originalShape.max(Direction.Axis.Z)
                ));
            }
        }
    }
}
