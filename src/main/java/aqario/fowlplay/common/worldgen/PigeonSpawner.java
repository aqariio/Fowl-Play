package aqario.fowlplay.common.worldgen;

import aqario.fowlplay.common.config.FPConfig;
import aqario.fowlplay.common.entity.bird.dove.PigeonEntity;
import aqario.fowlplay.core.FPEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PigeonSpawner implements CustomSpawner {
    private static final int SPAWN_COOLDOWN = 3600;
    private static final int MAX_PIGEONS = 6;
    private int ticksUntilNextSpawn;

    @SuppressWarnings("deprecation")
    @Override
    public void tick(ServerLevel world, boolean spawnEnemies) {
        if(!world.getGameRules().get(GameRules.SPAWN_MOBS)
            || FPConfig.getInstance().pigeonSpawnWeight <= 0
        ) {
            return;
        }
        this.ticksUntilNextSpawn--;
        if(this.ticksUntilNextSpawn > 0) {
            return;
        }
        this.ticksUntilNextSpawn = SPAWN_COOLDOWN;
        Player player = world.getRandomPlayer();
        if(player == null) {
            return;
        }
        RandomSource random = world.getRandom();
        int x = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        int z = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        BlockPos pos = player.blockPosition().offset(x, 0, z);
        if(!world.hasChunksAt(pos.getX() - 10, pos.getZ() - 10, pos.getX() + 10, pos.getZ() + 10)) {
            return;
        }
        if(world.isCloseToVillage(pos, 2)) {
            this.spawnNearPoi(world, pos);
        }
    }

    private void spawnNearPoi(ServerLevel world, BlockPos pos) {
        if(world.getPoiManager()
            .getCountInRange(holder -> holder.is(PoiTypes.HOME), pos, 48, PoiManager.Occupancy.IS_OCCUPIED)
            > 4L) {
            List<PigeonEntity> nearbyPigeons = world.getEntitiesOfClass(PigeonEntity.class, new AABB(pos).inflate(48.0, 8.0, 48.0));
            if(nearbyPigeons.size() < MAX_PIGEONS
                && world.canSeeSky(pos)) {
                this.spawn(pos, world);
            }
        }
    }

    private void spawn(BlockPos pos, ServerLevel level) {
        PigeonEntity pigeon = FPEntityTypes.PIGEON.get().create(level, EntitySpawnReason.NATURAL);
        if(pigeon == null) {
            return;
        }
        pigeon.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), EntitySpawnReason.NATURAL, null);
        pigeon.snapTo(pos, 0.0F, 0.0F);
        level.addFreshEntityWithPassengers(pigeon);
    }
}
