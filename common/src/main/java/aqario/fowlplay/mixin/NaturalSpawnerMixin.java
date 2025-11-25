package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.CustomMobCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
    @Inject(
        method = "spawnCategoryForChunk(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/LevelChunk;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void fowlplay$reduceSpawningFrequency(MobCategory group, ServerLevel world, LevelChunk chunk, NaturalSpawner.SpawnPredicate checker, NaturalSpawner.AfterSpawnCallback runner, CallbackInfo ci) {
        if(group == CustomMobCategory.BIRDS.mobCategory && world.getLevelData().getGameTime() % 20L != 0L) {
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
        if((group == CustomMobCategory.BIRDS.mobCategory || group == CustomMobCategory.AMBIENT_BIRDS.mobCategory) && world.getRandom().nextFloat() < 0.01F && world.getLevelData().getGameTime() % 100L != 0L) {
            return pos.getY() + world.getRandom().nextIntBetweenInclusive(32, 64);
        }
        return pos.getY();
    }
}
