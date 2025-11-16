package aqario.fowlplay.mixin;

import aqario.fowlplay.core.platform.CustomSpawnGroup;
import aqario.fowlplay.core.platform.CustomSpawnLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class SpawnHelperMixin {
    @Unique
    private static boolean fowlplay$isClearForSpawn(LevelReader world, BlockPos pos, EntityType<?> entityType) {
        BlockState blockState = world.getBlockState(pos);
        return NaturalSpawner.isValidEmptySpawnBlock(world, pos, blockState, blockState.getFluidState(), entityType);
    }

    @Unique
    private static boolean fowlplay$spawnsOnGround(LevelReader world, BlockPos spawnPos, EntityType<?> entityType) {
        if(entityType != null && world.getWorldBorder().isWithinBounds(spawnPos)) {
            BlockPos headPos = spawnPos.above();
            return fowlplay$isClearForSpawn(world, spawnPos, entityType) && (entityType.getHeight() <= 1 || fowlplay$isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }

    @Unique
    private static boolean fowlplay$spawnsOnWater(LevelReader world, BlockPos spawnPos, EntityType<?> entityType) {
        if(entityType != null && world.getWorldBorder().isWithinBounds(spawnPos)) {
            BlockPos headPos = spawnPos.above();
            return world.getFluidState(spawnPos.below()).is(FluidTags.WATER)
                && (entityType.getHeight() <= 1 || fowlplay$isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }

    @Inject(method = "isSpawnPositionOk(Lnet/minecraft/world/entity/SpawnPlacements$Type;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/EntityType;)Z", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$addCustomSpawnLocationChecks(SpawnPlacements.Type location, LevelReader world, BlockPos pos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        if(location == null) {
            cir.setReturnValue(false);
            return;
        }
        if(location == SpawnPlacements.Type.NO_RESTRICTIONS) {
            return;
        }
        if(entityType == null || !world.getWorldBorder().isWithinBounds(pos)) {
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
        method = "spawnCategoryForChunk(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/LevelChunk;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void fowlplay$reduceSpawningFrequency(MobCategory group, ServerLevel world, LevelChunk chunk, NaturalSpawner.SpawnPredicate checker, NaturalSpawner.AfterSpawnCallback runner, CallbackInfo ci) {
        if(group == CustomSpawnGroup.birds() && world.getLevelData().getGameTime() % 20L != 0L) {
            ci.cancel();
        }
    }

    @Redirect(
        method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/BlockPos;getY()I"
        )
    )
    private static int fowlplay$midairSpawning(BlockPos pos, MobCategory group, ServerLevel world) {
        if((group == CustomSpawnGroup.birds() || group == CustomSpawnGroup.ambientBirds()) && world.getRandom().nextFloat() < 0.01F && world.getLevelData().getGameTime() % 100L != 0L) {
            return pos.getY() + world.getRandom().nextIntBetweenInclusive(32, 64);
        }
        return pos.getY();
    }
}
