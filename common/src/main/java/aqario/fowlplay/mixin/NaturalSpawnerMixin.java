package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.CustomMobCategory;
import aqario.fowlplay.common.worldgen.CustomSpawnPlacementType;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
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

import java.util.Optional;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
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
    private static void fowlplay$addCustomSpawnPlacementTypeChecks(SpawnPlacements.Type location, LevelReader world, BlockPos pos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
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
        if(location == CustomSpawnPlacementType.ground()) {
            cir.setReturnValue(fowlplay$spawnsOnGround(world, pos, entityType));
        }
        else if(location == CustomSpawnPlacementType.semiaquatic()) {
            cir.setReturnValue(fowlplay$spawnsOnWater(world, pos, entityType) || fowlplay$spawnsOnGround(world, pos, entityType));
        }
        else if(location == CustomSpawnPlacementType.aquatic()) {
            cir.setReturnValue(fowlplay$spawnsOnWater(world, pos, entityType));
        }
    }

    @Inject(
        method = "spawnCategoryForChunk(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/LevelChunk;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void fowlplay$reduceSpawningFrequency(MobCategory group, ServerLevel world, LevelChunk chunk, NaturalSpawner.SpawnPredicate checker, NaturalSpawner.AfterSpawnCallback runner, CallbackInfo ci) {
        if(group == CustomMobCategory.birds() && world.getLevelData().getGameTime() % 20L != 0L) {
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
        if((group == CustomMobCategory.birds() || group == CustomMobCategory.ambientBirds()) && world.getRandom().nextFloat() < 0.01F && world.getLevelData().getGameTime() % 100L != 0L) {
            return pos.getY() + world.getRandom().nextIntBetweenInclusive(32, 64);
        }
        return pos.getY();
    }

//    @Redirect(
//        method = "spawnMobsForChunkGeneration",
//        at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/world/level/biome/MobSpawnSettings;getMobs(Lnet/minecraft/world/entity/MobCategory;)Lnet/minecraft/util/random/WeightedRandomList;"
//        )
//    )
//    private static WeightedRandomList<MobSpawnSettings.SpawnerData> fowlplay$spawnBirdsOnChunkGeneration(MobSpawnSettings spawnSettings, MobCategory category) {
//        return spawnSettings.getMobs(MobCategory.CREATURE);
//    }

    @Inject(
        method = "spawnMobsForChunkGeneration",
        at = @At("HEAD")
    )
    private static void fowlplay$spawnBirdsOnChunkGeneration(ServerLevelAccessor levelAccessor, Holder<Biome> biome, ChunkPos chunkPos, RandomSource random, CallbackInfo ci) {
        fowlplay$spawnMobsForChunkGeneration(CustomMobCategory.birds(), levelAccessor, biome, chunkPos, random);
        fowlplay$spawnMobsForChunkGeneration(CustomMobCategory.ambientBirds(), levelAccessor, biome, chunkPos, random);
    }

    // basically the same thing as vanilla but edited so that I can choose the mob category
    @Unique
    private static void fowlplay$spawnMobsForChunkGeneration(MobCategory category, ServerLevelAccessor levelAccessor, Holder<Biome> biome, ChunkPos chunkPos, RandomSource random) {
        MobSpawnSettings mobSpawnSettings = biome.value().getMobSettings();
        WeightedRandomList<MobSpawnSettings.SpawnerData> weightedRandomList = mobSpawnSettings.getMobs(category);
        if(!weightedRandomList.isEmpty()) {
            int i = chunkPos.getMinBlockX();
            int j = chunkPos.getMinBlockZ();

            while(random.nextFloat() < mobSpawnSettings.getCreatureProbability()) {
                Optional<MobSpawnSettings.SpawnerData> optional = weightedRandomList.getRandom(random);
                if(optional.isPresent()) {
                    MobSpawnSettings.SpawnerData spawnerData = optional.get();
                    int k = spawnerData.minCount + random.nextInt(1 + spawnerData.maxCount - spawnerData.minCount);
                    SpawnGroupData spawnGroupData = null;
                    int l = i + random.nextInt(16);
                    int m = j + random.nextInt(16);
                    int n = l;
                    int o = m;

                    for(int p = 0; p < k; p++) {
                        boolean bl = false;

                        for(int q = 0; !bl && q < 4; q++) {
                            BlockPos blockPos = NaturalSpawner.getTopNonCollidingPos(levelAccessor, spawnerData.type, l, m);
                            if(spawnerData.type.canSummon() && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.getPlacementType(spawnerData.type), levelAccessor, blockPos, spawnerData.type)) {
                                float f = spawnerData.type.getWidth();
                                double d = Mth.clamp(l, (double) i + f, i + 16.0 - f);
                                double e = Mth.clamp(m, (double) j + f, j + 16.0 - f);
                                if(!levelAccessor.noCollision(spawnerData.type.getAABB(d, blockPos.getY(), e))
                                    || !SpawnPlacements.checkSpawnRules(
                                    spawnerData.type, levelAccessor, MobSpawnType.CHUNK_GENERATION, BlockPos.containing(d, blockPos.getY(), e), levelAccessor.getRandom()
                                )) {
                                    continue;
                                }

                                Entity entity;
                                try {
                                    entity = spawnerData.type.create(levelAccessor.getLevel());
                                }
                                catch(Exception var27) {
                                    FowlPlay.LOGGER.warn("Failed to create mob", var27);
                                    continue;
                                }

                                if(entity == null) {
                                    continue;
                                }

                                entity.moveTo(d, blockPos.getY(), e, random.nextFloat() * 360.0F, 0.0F);
                                if(entity instanceof Mob mob && mob.checkSpawnRules(levelAccessor, MobSpawnType.CHUNK_GENERATION) && mob.checkSpawnObstruction(levelAccessor)) {
                                    spawnGroupData = mob.finalizeSpawn(
                                        levelAccessor, levelAccessor.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.CHUNK_GENERATION, spawnGroupData, null
                                    );
                                    levelAccessor.addFreshEntityWithPassengers(mob);
                                    bl = true;
                                }
                            }

                            l += random.nextInt(5) - random.nextInt(5);

                            for(m += random.nextInt(5) - random.nextInt(5); l < i || l >= i + 16 || m < j || m >= j + 16; m = o + random.nextInt(5) - random.nextInt(5)) {
                                l = n + random.nextInt(5) - random.nextInt(5);
                            }
                        }
                    }
                }
            }
        }
    }
}
