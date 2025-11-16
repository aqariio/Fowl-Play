package aqario.fowlplay.mixin;

import aqario.fowlplay.common.util.Birds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
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

@Mixin(CrossCollisionBlock.class)
public abstract class HorizontalConnectingBlockMixin {
    @Shadow
    protected abstract int getAABBIndex(BlockState state);

    @Shadow
    @Final
    protected VoxelShape[] collisionShapeByIndex;

    @Inject(method = "getCollisionShape", at = @At(value = "HEAD"), cancellable = true)
    private void fowlplay$lowerFenceHeight(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        CrossCollisionBlock block = (CrossCollisionBlock) (Object) this;
        if(block instanceof FenceBlock
            && context instanceof EntityCollisionContext entityContext
            && entityContext.getEntity() != null
            && Birds.isNotFlightless(entityContext.getEntity())
        ) {
            VoxelShape originalShape = this.collisionShapeByIndex[this.getAABBIndex(state)];
            if(originalShape.max(Direction.Axis.Y) > 1) {
                cir.setReturnValue(Shapes.box(
                    originalShape.min(Direction.Axis.X),
                    originalShape.min(Direction.Axis.Y),
                    originalShape.min(Direction.Axis.Z),
                    originalShape.max(Direction.Axis.X),
                    1,
                    originalShape.max(Direction.Axis.Z)
                ));
            }
        }
    }
}
