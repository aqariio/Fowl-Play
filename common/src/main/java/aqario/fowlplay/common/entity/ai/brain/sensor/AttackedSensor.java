package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySensorType;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class AttackedSensor<E extends BirdEntity> extends PredicateSensor<DamageSource, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ImmutableList.of(
        MemoryModuleType.HURT_BY,
        MemoryModuleType.HURT_BY_ENTITY,
        MemoryModuleType.AVOID_TARGET,
        FowlPlayMemoryModuleType.SEES_FOOD.get(),
        FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD.get()
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
        return FowlPlaySensorType.ATTACKED.get();
    }

    @Override
    protected void doTick(ServerLevel world, E bird) {
        Brain<?> brain = bird.getBrain();
        DamageSource damageSource = bird.getLastDamageSource();
        if(damageSource == null) {
            BrainUtils.clearMemory(brain, MemoryModuleType.HURT_BY);
            BrainUtils.clearMemory(brain, MemoryModuleType.HURT_BY_ENTITY);
            return;
        }
        if(this.predicate().test(damageSource, bird)) {
            BrainUtils.setMemory(brain, MemoryModuleType.HURT_BY, damageSource);

            if(damageSource.getEntity() instanceof LivingEntity attacker && attacker.isAlive() && attacker.level() == bird.level()) {
                BrainUtils.setMemory(brain, MemoryModuleType.HURT_BY_ENTITY, attacker);
                onAttacked(bird, attacker);
            }
            return;
        }
        BrainUtils.withMemory(brain, MemoryModuleType.HURT_BY_ENTITY, attacker -> {
            if(!attacker.isAlive() || attacker.level() != bird.level()) {
                BrainUtils.clearMemory(brain, MemoryModuleType.HURT_BY_ENTITY);
            }
        });
    }

    public static <T extends BirdEntity> void onAttacked(T bird, LivingEntity attacker) {
        Brain<?> brain = bird.getBrain();
        BrainUtils.clearMemory(brain, FowlPlayMemoryModuleType.SEES_FOOD.get());
        if(attacker instanceof Player player) {
            BrainUtils.setForgettableMemory(brain, FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD.get(), true, Birds.CANNOT_PICKUP_FOOD_TICKS);
            if(bird instanceof TrustingBirdEntity trustingBird && trustingBird.trusts(player)) {
                trustingBird.stopTrusting(player);
            }
        }
        if(attacker.getType() != bird.getType() && !bird.shouldAttack(attacker)) {
            Birds.alertOthers(bird, attacker);
        }
    }
}
