package aqario.fowlplay.mixin.common;

import aqario.fowlplay.common.entity.FowlPlaySpawnGroup;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
    @Inject(method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/WorldChunk;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, WorldChunk chunk, SpawnHelper.Checker checker, SpawnHelper.Runner runner, CallbackInfo ci) {
        if(group == FowlPlaySpawnGroup.BIRD.spawnGroup && world.getLevelProperties().getTime() % 20L != 0L) {
            ci.cancel();
        }
    }
}
