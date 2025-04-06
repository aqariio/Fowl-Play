package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.SparrowEntity;
import aqario.fowlplay.core.FowlPlayEntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestTypes;
import net.minecraft.world.spawner.Spawner;

import java.util.List;

public class SparrowSpawner implements Spawner {
    private static final int SPAWN_COOLDOWN = 1200;
    private static final int MAX_SPARROWS = 6;
    private int ticksUntilNextSpawn;

    @SuppressWarnings("deprecation")
    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnAnimals
            || !world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)
            || FowlPlayConfig.getInstance().sparrowSpawnWeight <= 0
        ) {
            return 0;
        }
        this.ticksUntilNextSpawn--;
        if (this.ticksUntilNextSpawn > 0) {
            return 0;
        }
        this.ticksUntilNextSpawn = SPAWN_COOLDOWN;
        PlayerEntity player = world.getRandomAlivePlayer();
        if (player == null) {
            return 0;
        }
        Random random = world.random;
        int x = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        int z = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        BlockPos pos = player.getBlockPos().add(x, 0, z);
        if (!world.isRegionLoaded(pos.getX() - 10, pos.getZ() - 10, pos.getX() + 10, pos.getZ() + 10)) {
            return 0;
        }
        if (world.isNearOccupiedPointOfInterest(pos, 2)) {
            return this.spawnNearPoi(world, pos);
        }

        return 0;
    }

    private int spawnNearPoi(ServerWorld world, BlockPos pos) {
        if (world.getPointOfInterestStorage()
            .count(holder -> holder.matchesKey(PointOfInterestTypes.HOME), pos, 48, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED)
            > 4L) {
            List<SparrowEntity> nearbySparrows = world.getNonSpectatingEntities(SparrowEntity.class, new Box(pos).expand(48.0, 8.0, 48.0));
            if (nearbySparrows.size() < MAX_SPARROWS
                && FlyingBirdEntity.canSpawnPasserines(FowlPlayEntityType.SPARROW, world, SpawnReason.NATURAL, pos, world.getRandom())
            ) {
                return this.spawn(pos, world);
            }
        }

        return 0;
    }

    private int spawn(BlockPos pos, ServerWorld world) {
        SparrowEntity sparrow = FowlPlayEntityType.SPARROW.create(world);
        if (sparrow == null) {
            return 0;
        }
        sparrow.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
        sparrow.refreshPositionAndAngles(pos, 0.0F, 0.0F);
        world.spawnEntityAndPassengers(sparrow);
        return 1;
    }
}
