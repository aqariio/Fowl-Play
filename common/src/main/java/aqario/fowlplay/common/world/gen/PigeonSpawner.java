package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.bird.pigeon.PigeonEntity;
import aqario.fowlplay.core.FowlPlayEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PigeonSpawner implements CustomSpawner {
    private static final int SPAWN_COOLDOWN = 3600;
    private static final int MAX_PIGEONS = 6;
    private int ticksUntilNextSpawn;

    @SuppressWarnings("deprecation")
    @Override
    public int tick(ServerLevel world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnAnimals
            || !world.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)
            || FowlPlayConfig.getInstance().pigeonSpawnWeight <= 0
        ) {
            return 0;
        }
        this.ticksUntilNextSpawn--;
        if (this.ticksUntilNextSpawn > 0) {
            return 0;
        }
        this.ticksUntilNextSpawn = SPAWN_COOLDOWN;
        Player player = world.getRandomPlayer();
        if (player == null) {
            return 0;
        }
        RandomSource random = world.random;
        int x = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        int z = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        BlockPos pos = player.blockPosition().offset(x, 0, z);
        if (!world.hasChunksAt(pos.getX() - 10, pos.getZ() - 10, pos.getX() + 10, pos.getZ() + 10)) {
            return 0;
        }
        if (world.isCloseToVillage(pos, 2)) {
            return this.spawnNearPoi(world, pos);
        }

        return 0;
    }

    private int spawnNearPoi(ServerLevel world, BlockPos pos) {
        if (world.getPoiManager()
            .getCountInRange(holder -> holder.is(PoiTypes.HOME), pos, 48, PoiManager.Occupancy.IS_OCCUPIED)
            > 4L) {
            List<PigeonEntity> nearbyPigeons = world.getEntitiesOfClass(PigeonEntity.class, new AABB(pos).inflate(48.0, 8.0, 48.0));
            if (nearbyPigeons.size() < MAX_PIGEONS
                && world.canSeeSky(pos)) {
                return this.spawn(pos, world);
            }
        }

        return 0;
    }

    private int spawn(BlockPos pos, ServerLevel world) {
        PigeonEntity pigeon = FowlPlayEntityTypes.PIGEON.get().create(world);
        if (pigeon == null) {
            return 0;
        }
        pigeon.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null);
        pigeon.moveTo(pos, 0.0F, 0.0F);
        world.addFreshEntityWithPassengers(pigeon);
        return 1;
    }
}
