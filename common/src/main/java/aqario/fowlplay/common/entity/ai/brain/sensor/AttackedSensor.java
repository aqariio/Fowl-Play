package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.TrustingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FPMemoryTypes;
import aqario.fowlplay.core.FPSensorTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;

import java.util.List;

public class AttackedSensor<E extends BirdEntity> extends PredicateSensor<DamageSource, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ImmutableList.of(
        MemoryModuleType.HURT_BY,
        MemoryModuleType.HURT_BY_ENTITY,
        MemoryModuleType.AVOID_TARGET,
        FPMemoryTypes.SEES_FOOD.get(),
        FPMemoryTypes.CANNOT_PICKUP_FOOD.get()
    );

    public AttackedSensor() {
        super((damageSource, entity) -> true);
        this.setScanRate(bird -> 10);
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FPSensorTypes.ATTACKED.get();
    }

    @Override
    protected void doTick(ServerLevel world, E bird) {
        DamageSource damageSource = bird.getLastDamageSource();
        if(damageSource == null) {
            bird.clearMemory(MemoryModuleType.HURT_BY);
            bird.clearMemory(MemoryModuleType.HURT_BY_ENTITY);
            return;
        }
        if(this.predicate().test(damageSource, bird)) {
            bird.setMemory(MemoryModuleType.HURT_BY, damageSource);

            if(damageSource.getEntity() instanceof LivingEntity attacker && attacker.isAlive() && attacker.level() == bird.level()) {
                bird.setMemory(MemoryModuleType.HURT_BY_ENTITY, attacker);
                onAttacked(bird, attacker);
            }
            return;
        }
        bird.getMemory(MemoryModuleType.HURT_BY_ENTITY).ifPresent(attacker -> {
            if(!attacker.isAlive() || attacker.level() != bird.level()) {
                bird.clearMemory(MemoryModuleType.HURT_BY_ENTITY);
            }
        });
    }

    public static <T extends BirdEntity> void onAttacked(T bird, LivingEntity attacker) {
        bird.clearMemory(FPMemoryTypes.SEES_FOOD.get());
        if(attacker instanceof Player player) {
            bird.setMemoryWithExpiry(FPMemoryTypes.CANNOT_PICKUP_FOOD.get(), Unit.INSTANCE, BirdUtils.CANNOT_PICKUP_FOOD_TICKS);
            if(bird instanceof TrustingBirdEntity trustingBird && trustingBird.trusts(player)) {
                trustingBird.stopTrusting(player);
            }
        }
        if(attacker.getType() != bird.getType() && !bird.shouldAttack(attacker)) {
            BirdUtils.alertOthers(bird, attacker);
        }
    }
}
