package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.FowlPlaySpawnGroup;
import aqario.fowlplay.common.world.gen.FowlPlaySpawnLocation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
    @Unique
    private static boolean spawnsOnGround(WorldView world, BlockPos pos, EntityType<?> entityType) {
        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = world.getFluidState(pos);
        BlockPos headPos = pos.up();
        return SpawnHelper.isClearForSpawn(world, pos, blockState, fluidState, entityType)
            && SpawnHelper.isClearForSpawn(world, headPos, world.getBlockState(headPos), world.getFluidState(headPos), entityType);

    }

    @Unique
    private static boolean spawnsInWater(WorldView world, BlockPos pos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().contains(pos)) {
            BlockPos headPos = pos.up();
            return world.getFluidState(pos).isIn(FluidTags.WATER) && !world.getBlockState(headPos).isSolidBlock(world, headPos);
        }
        return false;
    }

    @Inject(method = "canSpawn(Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityType;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"), cancellable = true)
    private static void addCustomSpawnLocationChecks(SpawnRestriction.Location location, WorldView world, BlockPos pos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        if (location == FowlPlaySpawnLocation.GROUND.location) {
            cir.setReturnValue(spawnsOnGround(world, pos, entityType));
            return;
        }
        if (location == FowlPlaySpawnLocation.SEMIAQUATIC.location) {
            cir.setReturnValue(spawnsInWater(world, pos, entityType) || spawnsOnGround(world, pos, entityType));
        }
    }

    @Inject(method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/WorldChunk;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V", at = @At("HEAD"), cancellable = true)
    private static void spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, WorldChunk chunk, SpawnHelper.Checker checker, SpawnHelper.Runner runner, CallbackInfo ci) {
        if (group == FowlPlaySpawnGroup.BIRD.spawnGroup && world.getLevelProperties().getTime() % 20L != 0L) {
            ci.cancel();
        }
    }
}
