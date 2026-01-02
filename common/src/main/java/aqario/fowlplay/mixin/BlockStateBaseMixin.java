package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockBehaviour.BlockStateBase.class, priority = 500)
public class BlockStateBaseMixin {
    @Unique
    private static final VoxelShape LEAVES_SHAPE = Shapes.box(0, 0, 0, 1, 0.75, 1);

    @Inject(
        method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void fowlplay$changeLeavesCollisionShape(BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        //noinspection ConstantConditions
        BlockState self = (BlockState) (Object) this;
        if(self.getBlock() instanceof LeavesBlock && context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if(entity != null
                && BirdUtils.isNotFlightless(entity)
            ) {
                if(entityContext.isAbove(LEAVES_SHAPE, pos, true)
                    && (!(entity instanceof FlyingBirdEntity bird) || !bird.isFlying())
                ) {
                    cir.setReturnValue(LEAVES_SHAPE);
                }
                else {
                    cir.setReturnValue(Shapes.empty());
                }
            }
        }
    }
}
