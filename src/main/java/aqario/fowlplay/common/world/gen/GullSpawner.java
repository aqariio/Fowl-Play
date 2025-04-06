package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.GullEntity;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.Spawner;

import java.util.List;

public class GullSpawner implements Spawner {
    private static final int SPAWN_COOLDOWN = 6000;
    private static final int MAX_SPAWN_HEIGHT = 48;
    private int cooldown;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnAnimals
            || !world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)
            || FowlPlayConfig.getInstance().gullSpawnWeight <= 0
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
            .east(-40 + random.nextInt(81))
            .south(-40 + random.nextInt(81));
        BlockState block = world.getBlockState(spawnPos);
        FluidState fluid = world.getFluidState(spawnPos);
        if (SpawnHelper.isClearForSpawn(world, spawnPos, block, fluid, FowlPlayEntityType.GULL)
            && world.getBiome(spawnPos).isIn(FowlPlayBiomeTags.SPAWNS_GULLS)
        ) {
            if (spawnPos.getY() - world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawnPos.getX(), spawnPos.getZ()) > MAX_SPAWN_HEIGHT) {
                return 0;
            }
            List<GullEntity> nearbyGulls = world.getNonSpectatingEntities(
                GullEntity.class,
                new Box(spawnPos).expand(72, 48, 72)
            );
            if (!nearbyGulls.isEmpty()) {
                return 0;
            }
            GullEntity gull = FowlPlayEntityType.GULL.create(world);
            if (gull == null) {
                return 0;
            }
            gull.initialize(world, world.getLocalDifficulty(spawnPos), SpawnReason.NATURAL, null, null);
            gull.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
            world.spawnEntityAndPassengers(gull);
            return Math.abs(world.getRandom().nextInt(4) - world.getRandom().nextInt(4)) + 1;
        }

        return 0;
    }
}
