package aqario.fowlplay.common.worldgen;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;

public final class SpawnPredicates {
    @SuppressWarnings("unused")
    public static boolean canSpawnPasserines(EntityType<? extends BirdEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return hasSkyAccess(world, pos)
            && ((world.getBlockState(pos.below()).getBlock() instanceof LeavesBlock
            && world.getBlockState(pos.below()).getValue(BlockStateProperties.DISTANCE) < 7)
            || isMidairSpawn(world, pos));
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnShorebirds(EntityType<? extends BirdEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return hasSkyAccess(world, pos)
            && (world.getBlockState(pos.below()).is(FowlPlayBlockTags.SHOREBIRDS_SPAWNABLE_ON)
            || world.getFluidState(pos.below()).is(FluidTags.WATER)
            || isMidairSpawn(world, pos));
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnWaterfowl(EntityType<? extends BirdEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return hasSkyAccess(world, pos)
            && (world.getFluidState(pos.below()).is(FluidTags.WATER)
            || isMidairSpawn(world, pos));
    }

    private static boolean hasSkyAccess(LevelAccessor world, BlockPos pos) {
        return world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) <= pos.getY();
    }

    private static boolean isMidairSpawn(LevelAccessor world, BlockPos pos) {
        return world.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) <= pos.getY() - 32
            && world.getBlockState(pos.below()).isAir();
    }
}
