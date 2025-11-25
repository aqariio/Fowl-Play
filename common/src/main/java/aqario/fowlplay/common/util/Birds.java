package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.Optional;

/**
 * A utility class for birds.
 */
public final class Birds {
    public static final float FAST_SPEED = 1.4F;
    public static final float FLY_SPEED = 2.0F;
    public static final float SWIM_SPEED = 4.0F;
    public static final int ITEM_PICK_UP_RANGE = 32;
    public static final CylindricalRadius FLY_AVOID_RANGE = new CylindricalRadius(8, 6);
    public static final int AVOID_TICKS = 160;
    public static final int CANNOT_PICKUP_FOOD_TICKS = 1200;
    public static final UniformInt STAY_NEAR_ENTITY_RANGE = UniformInt.of(16, 32);

    public static boolean isDaytime(BirdEntity entity) {
        Level world = entity.level();
        return !world.dimensionType().hasFixedTime() && (world.getDayTime() < 12500 || world.getDayTime() > 23000);
    }

    public static boolean shouldLandAtDestination(FlyingBirdEntity bird, BlockPos destination) {
        Level world = bird.level();
        return !world.getBlockState(destination).isAir()
            || !world.getBlockState(destination.below()).isAir()
            || !world.getFluidState(destination).isEmpty()
            || !world.getFluidState(destination.below()).isEmpty();
    }

    // TODO: birds like ducks and geese should prefer to walk, only flying when absolutely necessary
    public static void tryFlyingAlongPath(FlyingBirdEntity bird, Path path) {
        // noinspection ConstantConditions
        if(bird.canStartFlying()
            && (shouldFlyToDestination(bird, path, path.getTarget().getCenter())
            && !(bird.getType().is(FowlPlayEntityTypeTags.WATERBIRDS)
            && bird.isInWaterOrBubble())
            || shouldFlyFromAvoidTarget(bird))
        ) {
            bird.startFlying();
        }
    }

    public static boolean shouldFlyToDestination(FlyingBirdEntity bird, Path path, Vec3 target) {
        if(!path.canReach()) {
            return true;
        }
        Vec3 pos = bird.position();
        double dx = target.x - pos.x;
        double dy = target.y - pos.y;
        double dz = target.z - pos.z;
        double dxz2 = dx * dx + dz * dz;
        double dy2 = dy * dy;
        double xzRadius = bird.getWalkRange().horizontal();
        double yRadius = bird.getWalkRange().vertical();
        return dxz2 > xzRadius * xzRadius || dy2 > yRadius * yRadius;
    }

    public static boolean shouldFlyFromAvoidTarget(FlyingBirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        if(!BrainUtils.hasMemory(brain, MemoryModuleType.AVOID_TARGET)
            || !BrainUtils.hasMemory(brain, FowlPlayMemoryTypes.IS_AVOIDING.get())
        ) {
            return false;
        }
        LivingEntity target = BrainUtils.getMemory(brain, MemoryModuleType.AVOID_TARGET);
        // noinspection ConstantConditions
        if((target.isSprinting() && !target.isSpectator()) || target.isPassenger()) {
            return true;
        }
        Vec3 pos = bird.position();
        Vec3 targetPos = target.position();
        double dx = targetPos.x - pos.x;
        double dy = targetPos.y - pos.y;
        double dz = targetPos.z - pos.z;
        double dxz2 = dx * dx + dz * dz;
        double dy2 = dy * dy;
        double xzRadius = FLY_AVOID_RANGE.horizontal();
        double yRadius = FLY_AVOID_RANGE.vertical();
        return dxz2 <= xzRadius * xzRadius && dy2 <= yRadius * yRadius;
    }

    public static boolean isNotFlightless(Entity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.BIRDS)
            && !entity.getType().is(FowlPlayEntityTypeTags.FLIGHTLESS);
    }

    public static <T extends BirdEntity> void alertOthers(T bird, LivingEntity attacker) {
        getNearbyVisibleAdults(bird).forEach(other -> {
            Brain<?> brain = other.getBrain();
            if(attacker instanceof Player) {
                BrainUtils.setForgettableMemory(brain, FowlPlayMemoryTypes.CANNOT_PICKUP_FOOD.get(), true, CANNOT_PICKUP_FOOD_TICKS);
            }
            BrainUtils.clearMemory(brain, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            BrainUtils.setForgettableMemory(brain, MemoryModuleType.AVOID_TARGET, attacker, AVOID_TICKS);
        });
    }

    public static <T extends BirdEntity> List<? extends AgeableMob> getNearbyVisibleAdults(T bird) {
        return Optional.ofNullable(BrainUtils.getMemory(bird, FowlPlayMemoryTypes.NEAREST_VISIBLE_ADULTS.get()))
            .orElse(ImmutableList.of());
    }

    public static boolean isPlayerHoldingFood(BirdEntity bird, LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(bird.getFood());
    }

    public static boolean canPickupFood(BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        if(BrainUtils.hasMemory(brain, FowlPlayMemoryTypes.CANNOT_PICKUP_FOOD.get())) {
            return false;
        }
        if(!BrainUtils.hasMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get())) {
            return false;
        }
        List<ItemEntity> foodItems = BrainUtils.getMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get());
        // noinspection ConstantConditions
        if(foodItems.isEmpty() || bird.getFood().test(bird.getMainHandItem())) {
            return false;
        }
        NearestVisibleLivingEntities visibleMobs = BrainUtils.getMemory(brain, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
        if(visibleMobs == null) {
            return true;
        }
        List<LivingEntity> avoidTargets = visibleMobs.find(entity -> true)
            .filter(entity -> shouldAvoid(bird, entity))
            .filter(entity -> entity.closerThan(foodItems.getFirst(), bird.getFleeRange(entity)))
            .toList();

        return avoidTargets.isEmpty();
    }

    public static boolean shouldAvoid(BirdEntity bird, LivingEntity target) {
        Brain<?> brain = bird.getBrain();
        if(!(bird.shouldAvoid(target) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) && !wasHurtBy(bird, target)) {
            return false;
        }
        if(target instanceof Player player && bird instanceof TrustingBirdEntity trusting && trusting.trusts(player)) {
            return false;
        }
        LivingEntity attackTarget = BrainUtils.getMemory(brain, MemoryModuleType.ATTACK_TARGET);
        if(attackTarget != null && attackTarget.equals(target)) {
            return false;
        }
        return !bird.shouldAttack(target);
    }

    public static boolean wasHurtBy(BirdEntity bird, LivingEntity entity) {
        LivingEntity hurtBy = BrainUtils.getMemory(bird, MemoryModuleType.HURT_BY_ENTITY);
        return hurtBy != null && hurtBy.equals(entity);
    }

    public static boolean isPerched(BirdEntity entity) {
        return (!(entity instanceof FlyingBirdEntity bird) || !bird.isFlying())
            && TargetingUtil.isPerch(entity, entity.getBlockPosBelowThatAffectsMyMovement());
    }
}
