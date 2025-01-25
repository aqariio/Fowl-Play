package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.HawkEntity;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.Spawner;

import java.util.List;

public class HawkSpawner implements Spawner {
    private static final int SPAWN_COOLDOWN = 7200;
    private int cooldown;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnAnimals
            || !world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)
            || FowlPlayConfig.getInstance().hawkSpawnWeight <= 0
        ) {
            return 0;
        }
        this.cooldown--;
        if (this.cooldown > 0) {
            return 0;
        }
        Random random = world.random;
        this.cooldown = SPAWN_COOLDOWN + (random.nextInt(60) - random.nextInt(60)) * 20;
        PlayerEntity player = world.getRandomAlivePlayer();
        if (player == null || player.isSpectator()) {
            return 0;
        }
        BlockPos playerPos = player.getBlockPos();
        BlockPos spawnPos = playerPos
            .up(30 + random.nextInt(20))
            .east(-10 + random.nextInt(21))
            .south(-10 + random.nextInt(21));
        BlockState block = world.getBlockState(spawnPos);
        FluidState fluid = world.getFluidState(spawnPos);
        if (SpawnHelper.isClearForSpawn(world, spawnPos, block, fluid, FowlPlayEntityType.HAWK)
            && world.getBiome(spawnPos).isIn(FowlPlayBiomeTags.SPAWNS_HAWKS)
        ) {
            List<HawkEntity> nearbyHawks = world.getNonSpectatingEntities(
                HawkEntity.class,
                new Box(spawnPos).expand(72, 48, 72)
            );
            if (!nearbyHawks.isEmpty()) {
                return 0;
            }
            HawkEntity hawk = FowlPlayEntityType.HAWK.create(world);
            if (hawk == null) {
                return 0;
            }
            hawk.initialize(world, world.getLocalDifficulty(spawnPos), SpawnReason.NATURAL, null, null);
            hawk.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
            world.spawnEntityAndPassengers(hawk);
            return 1;
        }

        return 0;
    }
}
