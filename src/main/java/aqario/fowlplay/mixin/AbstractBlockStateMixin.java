package aqario.fowlplay.mixin;

import aqario.fowlplay.common.util.Birds;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractBlock.AbstractBlockState.class, priority = 500)
public class AbstractBlockStateMixin {
    @Unique
    private static final VoxelShape LEAVES_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 0.75, 1);

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    private void fowlplay$changeLeavesCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        //noinspection ConstantConditions
        BlockState self = (BlockState) (Object) this;
        if (self.getBlock() instanceof LeavesBlock) {
            if (context instanceof EntityShapeContext entityContext
                && entityContext.isAbove(LEAVES_SHAPE, pos, true)
            ) {
                Entity entity = entityContext.getEntity();
                if (entity != null && Birds.notFlightless(entity)) {
                    cir.setReturnValue(LEAVES_SHAPE);
                }
            }
        }
    }
}
