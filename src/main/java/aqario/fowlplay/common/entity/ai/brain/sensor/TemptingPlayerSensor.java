package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TemptingPlayerSensor extends Sensor<BirdEntity> {
    public static final int TARGET_DETECTION_DISTANCE = 32;
    private static final TargetPredicate TEMPTER_PREDICATE = TargetPredicate.createNonAttackable()
        .setBaseMaxDistance(TARGET_DETECTION_DISTANCE)
        .ignoreVisibility();

    protected void sense(ServerWorld world, BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        List<PlayerEntity> list = world.getPlayers()
            .stream()
            .filter(EntityPredicates.EXCEPT_SPECTATOR)
            .filter(player -> TEMPTER_PREDICATE.test(bird, player))
            .filter(player -> bird.isInRange(player, TARGET_DETECTION_DISTANCE))
            .filter(player -> this.test(player, bird))
            .filter(player -> !bird.hasPassenger(player))
            .sorted(Comparator.comparingDouble(bird::squaredDistanceTo))
            .collect(Collectors.toList());
        if (!list.isEmpty()) {
            PlayerEntity player = list.getFirst();
            brain.remember(MemoryModuleType.TEMPTING_PLAYER, player);
        }
        else {
            brain.forget(MemoryModuleType.TEMPTING_PLAYER);
        }
    }

    private boolean test(PlayerEntity player, BirdEntity bird) {
        return bird.getFood().test(player.getMainHandStack()) || bird.getFood().test(player.getOffHandStack());
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.TEMPTING_PLAYER);
    }
}