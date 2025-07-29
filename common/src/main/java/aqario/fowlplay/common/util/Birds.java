package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.Optional;

/**
 * A utility class for birds.
 */
public final class Birds {
    public static final float NORMAL_SPEED = 1.0F;
    public static final float FAST_SPEED = 1.4F;
    public static final float FLY_SPEED = 2.0F;
    public static final float SWIM_SPEED = 4.0F;
    public static final int ITEM_PICK_UP_RANGE = 32;
    public static final SquareRadius FLY_AVOID_RANGE = new SquareRadius(6, 6);
    public static final int AVOID_TICKS = 160;
    public static final int CANNOT_PICKUP_FOOD_TICKS = 1200;
    public static final UniformIntProvider STAY_NEAR_ENTITY_RANGE = UniformIntProvider.create(16, 32);

    public static void tryFlyingAlongPath(FlyingBirdEntity bird, Path path) {
        // noinspection ConstantConditions
        if(bird.canStartFlying()
            && (shouldFlyToDestination(bird, path.getTarget().toCenterPos()) && !(bird.getType().isIn(FowlPlayEntityTypeTags.WATERBIRDS) && bird.isInsideWaterOrBubbleColumn())
            || shouldFlyFromAvoidTarget(bird))
        ) {
            bird.startFlying();
        }
    }

    public static boolean shouldFlyToDestination(FlyingBirdEntity bird, Vec3d target) {
        Vec3d pos = bird.getPos();
        double dx = target.x - pos.x;
        double dy = target.y - pos.y;
        double dz = target.z - pos.z;
        double dxz2 = dx * dx + dz * dz;
        double dy2 = dy * dy;
        double xzRadius = bird.getWalkRange().xzRadius();
        double yRadius = bird.getWalkRange().yRadius();
        return dxz2 > xzRadius * xzRadius || dy2 > yRadius * yRadius;
    }

    public static boolean shouldFlyFromAvoidTarget(FlyingBirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        if(!BrainUtils.hasMemory(brain, MemoryModuleType.AVOID_TARGET)
            || !BrainUtils.hasMemory(brain, FowlPlayMemoryModuleType.IS_AVOIDING.get())
        ) {
            return false;
        }
        LivingEntity target = BrainUtils.getMemory(brain, MemoryModuleType.AVOID_TARGET);
        // noinspection ConstantConditions
        if((target.isSprinting() && !target.isSpectator()) || target.hasVehicle()) {
            return true;
        }
        Vec3d pos = bird.getPos();
        Vec3d targetPos = target.getPos();
        double dx = targetPos.x - pos.x;
        double dy = targetPos.y - pos.y;
        double dz = targetPos.z - pos.z;
        double dxz2 = dx * dx + dz * dz;
        double dy2 = dy * dy;
        double xzRadius = FLY_AVOID_RANGE.xzRadius();
        double yRadius = FLY_AVOID_RANGE.yRadius();
        return dxz2 <= xzRadius * xzRadius && dy2 <= yRadius * yRadius;
    }

    // angle is in radians
    public static boolean isWithinAngle(Vec3d normalVec, Vec3d targetVec, double angle) {
        normalVec = normalVec.normalize();
        targetVec = targetVec.normalize();

        // cosine of angle between the two vectors
        float cosVectorAngle = (float) normalVec.dotProduct(targetVec);

        // if cosine of the vectors' angle >= cosine of max angle the target vector is within the angle
        float cosMaxAngle = MathHelper.cos((float) angle);
        return cosVectorAngle >= cosMaxAngle;
    }

    // angle is in radians
    public static boolean isPosWithinViewAngle(PathAwareEntity entity, BlockPos pos, double angle) {
        Vec3d lookVec = entity.getRotationVec(1.0F);

        Vec3d target = Vec3d.ofCenter(pos);
        Vec3d targetVec = target.subtract(entity.getPos());

        return isWithinAngle(lookVec, targetVec, angle);
    }

    public static boolean notFlightless(Entity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.BIRDS)
            && !entity.getType().isIn(FowlPlayEntityTypeTags.FLIGHTLESS);
    }

    public static <T extends BirdEntity> void alertOthers(T bird, LivingEntity attacker) {
        getNearbyVisibleAdults(bird).forEach(other -> {
            Brain<?> brain = other.getBrain();
            if(attacker instanceof PlayerEntity) {
                BrainUtils.setForgettableMemory(brain, FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD.get(), true, CANNOT_PICKUP_FOOD_TICKS);
            }
            BrainUtils.clearMemory(brain, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            BrainUtils.setForgettableMemory(brain, MemoryModuleType.AVOID_TARGET, attacker, AVOID_TICKS);
        });
    }

    public static <T extends BirdEntity> List<? extends PassiveEntity> getNearbyVisibleAdults(T bird) {
        return bird.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get()).orElse(ImmutableList.of());
    }

    public static boolean isPlayerHoldingFood(BirdEntity bird, LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(bird.getFood());
    }

    public static boolean canPickupFood(BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        if(BrainUtils.hasMemory(brain, FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD.get())) {
            return false;
        }
        if(!BrainUtils.hasMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get())) {
            return false;
        }
        List<ItemEntity> foodItems = BrainUtils.getMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get());
        // noinspection ConstantConditions
        if(foodItems.isEmpty() || bird.getFood().test(bird.getMainHandStack())) {
            return false;
        }
        LivingTargetCache visibleMobs = BrainUtils.getMemory(brain, MemoryModuleType.VISIBLE_MOBS);
        if(visibleMobs == null) {
            return false;
        }
        List<LivingEntity> avoidTargets = visibleMobs.stream(entity -> true)
            .filter(entity -> shouldAvoid(bird, entity))
            .filter(entity -> entity.isInRange(foodItems.get(0), bird.getFleeRange(entity)))
            .toList();

        return avoidTargets.isEmpty();
    }

    public static boolean shouldAvoid(BirdEntity bird, LivingEntity target) {
        Brain<?> brain = bird.getBrain();
        if(!(bird.shouldAvoid(target) && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) && !shouldAvoidAttacker(brain, target)) {
            return false;
        }
        if(target instanceof PlayerEntity player && bird instanceof TrustingBirdEntity trusting && trusting.trusts(player)) {
            return false;
        }
        Optional<LivingEntity> attackTarget = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if(attackTarget != null && attackTarget.isPresent() && attackTarget.get().equals(target)) {
            return false;
        }
        return !bird.shouldAttack(target);
    }

    public static boolean shouldAvoidAttacker(Brain<?> brain, LivingEntity attacker) {
        LivingEntity hurtBy = BrainUtils.getMemory(brain, MemoryModuleType.HURT_BY_ENTITY);
        return hurtBy != null && hurtBy.equals(attacker);
    }

    public static boolean canAttack(BirdEntity bird) {
        return !bird.isInsideWaterOrBubbleColumn() && !LookTargetUtil.hasBreedTarget(bird);
    }

    public static boolean canAquaticAttack(BirdEntity bird) {
        return !LookTargetUtil.hasBreedTarget(bird);
    }

    public static boolean isPerched(BirdEntity entity) {
        return entity.getWorld().getBlockState(entity.getVelocityAffectingPos()).isIn(FowlPlayBlockTags.PERCHES);
    }
}
