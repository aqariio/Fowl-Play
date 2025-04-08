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
    private static boolean isClearForSpawn(WorldView world, BlockPos pos, EntityType<?> entityType) {
        BlockState blockState = world.getBlockState(pos);
        return SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), entityType);
    }

    @Unique
    private static boolean spawnsOnGround(WorldView world, BlockPos spawnPos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().contains(spawnPos)) {
            BlockPos headPos = spawnPos.up();
            return isClearForSpawn(world, spawnPos, entityType) && (entityType.getHeight() <= 1 || isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }

    @Unique
    private static boolean spawnsOnWater(WorldView world, BlockPos spawnPos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().contains(spawnPos)) {
            BlockPos headPos = spawnPos.up();
            return world.getFluidState(spawnPos.down()).isIn(FluidTags.WATER)
                && (entityType.getHeight() <= 1 || isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }

    @Inject(method = "canSpawn(Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityType;)Z", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$addCustomSpawnLocationChecks(SpawnRestriction.Location location, WorldView world, BlockPos pos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        if (location == SpawnRestriction.Location.NO_RESTRICTIONS) {
            cir.setReturnValue(true);
            return;
        }
        if (entityType == null || !world.getWorldBorder().contains(pos)) {
            cir.setReturnValue(false);
            return;
        }
        if (location == FowlPlaySpawnLocation.GROUND.location) {
            cir.setReturnValue(spawnsOnGround(world, pos, entityType));
            return;
        }
        if (location == FowlPlaySpawnLocation.SEMIAQUATIC.location) {
            cir.setReturnValue(spawnsOnWater(world, pos, entityType) || spawnsOnGround(world, pos, entityType));
            return;
        }
        if (location == FowlPlaySpawnLocation.AQUATIC.location) {
            cir.setReturnValue(spawnsOnWater(world, pos, entityType));
            return;
        }
        // reimplement vanilla checks so the switch statement doesn't have a stroke
        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = world.getFluidState(pos);
        BlockPos blockPos = pos.up();
        BlockPos blockPos2 = pos.down();
        if (location == SpawnRestriction.Location.IN_WATER) {
            cir.setReturnValue(fluidState.isIn(FluidTags.WATER) && !world.getBlockState(blockPos).isSolidBlock(world, blockPos));
        }
        else if (location == SpawnRestriction.Location.IN_LAVA) {
            cir.setReturnValue(fluidState.isIn(FluidTags.LAVA));
        }
        else {
            BlockState blockState2 = world.getBlockState(blockPos2);
            cir.setReturnValue(blockState2.allowsSpawning(world, blockPos2, entityType) && SpawnHelper.isClearForSpawn(world, pos, blockState, fluidState, entityType)
                && SpawnHelper.isClearForSpawn(world, blockPos, world.getBlockState(blockPos), world.getFluidState(blockPos), entityType));
        }
    }

    @Inject(method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/WorldChunk;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, WorldChunk chunk, SpawnHelper.Checker checker, SpawnHelper.Runner runner, CallbackInfo ci) {
        if (group == FowlPlaySpawnGroup.BIRD.spawnGroup && world.getLevelProperties().getTime() % 20L != 0L) {
            ci.cancel();
        }
    }
}
