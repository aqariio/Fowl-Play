package aqario.fowlplay.mixin;

import aqario.fowlplay.core.platform.CustomSpawnGroup;
import aqario.fowlplay.core.platform.CustomSpawnLocation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
    @Unique
    private static boolean fowlplay$isClearForSpawn(WorldView world, BlockPos pos, EntityType<?> entityType) {
        BlockState blockState = world.getBlockState(pos);
        return SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), entityType);
    }

    @Unique
    private static boolean fowlplay$spawnsOnGround(WorldView world, BlockPos spawnPos, EntityType<?> entityType) {
        if(entityType != null && world.getWorldBorder().contains(spawnPos)) {
            BlockPos headPos = spawnPos.up();
            return fowlplay$isClearForSpawn(world, spawnPos, entityType) && (entityType.getHeight() <= 1 || fowlplay$isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }

    @Unique
    private static boolean fowlplay$spawnsOnWater(WorldView world, BlockPos spawnPos, EntityType<?> entityType) {
        if(entityType != null && world.getWorldBorder().contains(spawnPos)) {
            BlockPos headPos = spawnPos.up();
            return world.getFluidState(spawnPos.down()).isIn(FluidTags.WATER)
                && (entityType.getHeight() <= 1 || fowlplay$isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }

    @Inject(method = "canSpawn(Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityType;)Z", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$addCustomSpawnLocationChecks(SpawnRestriction.Location location, WorldView world, BlockPos pos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        if(location == null) {
            cir.setReturnValue(false);
            return;
        }
        if(location == SpawnRestriction.Location.NO_RESTRICTIONS) {
            return;
        }
        if(entityType == null || !world.getWorldBorder().contains(pos)) {
            return;
        }
        if(location == CustomSpawnLocation.ground()) {
            cir.setReturnValue(fowlplay$spawnsOnGround(world, pos, entityType));
        }
        else if(location == CustomSpawnLocation.semiaquatic()) {
            cir.setReturnValue(fowlplay$spawnsOnWater(world, pos, entityType) || fowlplay$spawnsOnGround(world, pos, entityType));
        }
        else if(location == CustomSpawnLocation.aquatic()) {
            cir.setReturnValue(fowlplay$spawnsOnWater(world, pos, entityType));
        }
    }

    @Inject(
        method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/WorldChunk;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void fowlplay$reduceSpawningFrequency(SpawnGroup group, ServerWorld world, WorldChunk chunk, SpawnHelper.Checker checker, SpawnHelper.Runner runner, CallbackInfo ci) {
        if(group == CustomSpawnGroup.birds() && world.getLevelProperties().getTime() % 20L != 0L) {
            ci.cancel();
        }
    }

    @Redirect(
        method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/BlockPos;getY()I"
        )
    )
    private static int fowlplay$midairSpawning(BlockPos pos, SpawnGroup group, ServerWorld world) {
        if((group == CustomSpawnGroup.birds() || group == CustomSpawnGroup.ambientBirds()) && world.getRandom().nextFloat() < 0.01F) {
            return pos.getY() + world.getRandom().nextBetween(32, 64);
        }
        return pos.getY();
    }
}
