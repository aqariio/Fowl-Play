package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.ai.navigation.BirdRandomPos;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.CylindricalRadius;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

/**
 * A collection of behaviours that control the flying behaviour of birds.
 */
public class FlightBehaviours {
    private static final CylindricalRadius FALL_RECOVERY_RANGE = new CylindricalRadius(16, 8);

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> stopFlying() {
        return new AnonymousBehaviour<>(
            bird -> {
                bird.stopFlying();
                return true;
            }
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> stopFalling() {
        return new AnonymousBehaviour<E>(
            bird -> {
                Vec3 target = BirdRandomPos.getAir(bird, FALL_RECOVERY_RANGE);
                return target != null && bird.startFlyingAlongPath(
                    bird.createFlightPath(BlockPos.containing(target), 0),
                    1.0
                );
            }
        ).startCondition(bird -> bird.fallDistance > 1 && bird.canStartFlying());
    }
}
