package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.Domesticatable;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.TrustingBirdEntity;
import aqario.fowlplay.core.FPMemoryTypes;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;

import java.util.List;

/**
 * A utility class for birds.
 */
public final class BirdUtils {
    public static final float FAST_SPEED = 1.4F;
    public static final float FLY_SPEED = 2.0F;
    public static final float SWIM_SPEED = 4.0F;
    public static final int ITEM_PICK_UP_RANGE = 32;
    public static final CylindricalRadius FLY_AVOID_RANGE = new CylindricalRadius(8, 6);
    public static final int AVOID_TICKS = 160;
    public static final int CANNOT_PICKUP_FOOD_TICKS = 1200;
    public static final UniformInt STAY_NEAR_ENTITY_RANGE = UniformInt.of(16, 32);

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
//            && !(bird.getType().is(FowlPlayEntityTypeTags.WATERBIRDS)
//            && bird.isInWaterOrBubble())
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
        if(!bird.isMemoryPresent(MemoryModuleType.AVOID_TARGET)
            || !bird.isMemoryPresent(FPMemoryTypes.IS_AVOIDING.get())
        ) {
            return false;
        }
        LivingEntity target = bird.getPresentMemory(MemoryModuleType.AVOID_TARGET);
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

    public static <E extends BirdEntity> boolean isSelfAndTargetInWater(E self, LivingEntity target) {
        return self.isInWaterOrBubble() && target.isUnderWater() && target.position().y < self.position().y;
    }

    public static boolean isNotFlightless(Entity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.BIRDS)
            && !entity.getType().is(FowlPlayEntityTypeTags.FLIGHTLESS);
    }

    public static boolean isPerchingBird(Entity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.PERCHING_BIRDS);
    }

    public static <T extends BirdEntity> void alertOthers(T bird, LivingEntity attacker) {
        getNearbyVisibleAdults(bird).forEach(other -> {
            if(attacker instanceof Player) {
                other.setMemoryWithExpiry(FPMemoryTypes.CANNOT_PICKUP_FOOD.get(), Unit.INSTANCE, CANNOT_PICKUP_FOOD_TICKS);
            }
            other.clearMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            other.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, attacker, AVOID_TICKS);
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends BirdEntity> List<T> getNearbyVisibleAdults(T bird) {
        return (List<T>) bird.getMemory(FPMemoryTypes.NEAREST_VISIBLE_ADULTS.get())
            .orElse(ImmutableList.of());
    }

    public static boolean isPlayerHoldingFood(BirdEntity bird, LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(bird.getFood());
    }

    public static boolean shouldPickupFood(BirdEntity bird) {
        if(bird.isMemoryPresent(FPMemoryTypes.CANNOT_PICKUP_FOOD.get())) {
            return false;
        }
        if(!bird.isMemoryPresent(SBLMemoryTypes.NEARBY_ITEMS.get())) {
            return false;
        }
        List<ItemEntity> foodItems = bird.getPresentMemory(SBLMemoryTypes.NEARBY_ITEMS.get());
        if(bird.getFood().test(bird.getMainHandItem())) {
            return false;
        }
        if(!bird.isMemoryPresent(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)) {
            return true;
        }
        NearestVisibleLivingEntities visibleMobs = bird.getPresentMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
        List<LivingEntity> avoidTargets = visibleMobs.find(entity -> true)
            .filter(entity -> shouldAvoid(bird, entity))
            .filter(entity -> entity.closerThan(foodItems.get(0), bird.getFleeRange(entity)))
            .toList();

        return avoidTargets.isEmpty();
    }

    public static boolean shouldAvoid(BirdEntity bird, LivingEntity target) {
        if(!(bird.shouldAvoid(target) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) && !wasHurtBy(bird, target)) {
            return false;
        }
        if(target instanceof Player player) {
            if(bird instanceof TrustingBirdEntity trusting && trusting.trusts(player)) {
                return false;
            }
            if(bird instanceof Domesticatable domestic && domestic.isDomestic()) {
                return false;
            }
        }
        if(bird.isMemoryPresent(MemoryModuleType.ATTACK_TARGET)
            && bird.getPresentMemory(MemoryModuleType.ATTACK_TARGET).equals(target)
        ) {
            return false;
        }
        return !bird.shouldAttack(target);
    }

    public static boolean wasHurtBy(BirdEntity bird, LivingEntity entity) {
        return bird.getMemory(MemoryModuleType.HURT_BY_ENTITY)
            .map(hurtBy -> hurtBy.equals(entity))
            .orElse(false);
    }

    public static boolean isPerched(BirdEntity entity) {
        return (!(entity instanceof FlyingBirdEntity bird) || !bird.isFlying())
            && TargetingUtils.isPerch(entity, entity.getBlockPosBelowThatAffectsMyMovement());
    }
}
