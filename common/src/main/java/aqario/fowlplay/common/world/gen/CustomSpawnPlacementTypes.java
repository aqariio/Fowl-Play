package aqario.fowlplay.common.world.gen;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.Nullable;

public interface CustomSpawnPlacementTypes {
    SpawnPlacementType GROUND = new SpawnPlacementType() {
        @Override
        public boolean isSpawnPositionOk(LevelReader worldView, BlockPos spawnPos, @Nullable EntityType<?> entityType) {
            return spawnsOnGround(worldView, spawnPos, entityType);
        }

        @Override
        public BlockPos adjustSpawnPosition(LevelReader world, BlockPos pos) {
            BlockPos blockPos = pos.below();
            return world.getBlockState(blockPos).isPathfindable(PathComputationType.LAND) ? blockPos : pos;
        }
    };
    SpawnPlacementType SEMIAQUATIC = new SpawnPlacementType() {
        @Override
        public boolean isSpawnPositionOk(LevelReader worldView, BlockPos spawnPos, @Nullable EntityType<?> entityType) {
            return spawnsOnWater(worldView, spawnPos, entityType) || spawnsOnGround(worldView, spawnPos, entityType);
        }

        @Override
        public BlockPos adjustSpawnPosition(LevelReader world, BlockPos pos) {
            BlockPos groundPos = pos.below();
            return world.getBlockState(groundPos).isPathfindable(PathComputationType.LAND) ? groundPos : pos;
        }
    };
    SpawnPlacementType AQUATIC = CustomSpawnPlacementTypes::spawnsOnWater;

    private static boolean isClearForSpawn(LevelReader world, BlockPos pos, EntityType<?> entityType) {
        BlockState blockState = world.getBlockState(pos);
        return NaturalSpawner.isValidEmptySpawnBlock(world, pos, blockState, blockState.getFluidState(), entityType);
    }

    private static boolean spawnsOnGround(LevelReader world, BlockPos spawnPos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().isWithinBounds(spawnPos)) {
            BlockPos headPos = spawnPos.above();
            return isClearForSpawn(world, spawnPos, entityType) && (entityType.getHeight() <= 1 || isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }

    private static boolean spawnsOnWater(LevelReader world, BlockPos spawnPos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().isWithinBounds(spawnPos)) {
            BlockPos headPos = spawnPos.above();
            return world.getFluidState(spawnPos.below()).is(FluidTags.WATER)
                && (entityType.getHeight() <= 1 || isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }
}
