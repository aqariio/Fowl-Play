package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Optional;

/**
 * A utility class for bird entities.
 */
public final class Birds {
    public static final float WALK_SPEED = 1.0F;
    public static final float RUN_SPEED = 1.4F;
    public static final float FLY_SPEED = 2.0F;
    public static final float SWIM_SPEED = 4.0F;
    public static final int ITEM_PICK_UP_RANGE = 32;
    public static final int WALK_RANGE = 16;
    public static final UniformIntProvider FOLLOW_ADULT_RANGE = UniformIntProvider.create(5, 16);
    public static final UniformIntProvider STAY_NEAR_ENTITY_RANGE = UniformIntProvider.create(16, 32);

    public static boolean shouldFlyToTarget(FlyingBirdEntity bird, Vec3d target) {
        return bird.getPos().squaredDistanceTo(target) > WALK_RANGE * WALK_RANGE;
    }

    public static boolean notFlightless(Entity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.BIRDS)
            && !entity.getType().isIn(FowlPlayEntityTypeTags.FLIGHTLESS);
    }

    public static boolean canPickupFood(BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        if (!brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)) {
            return false;
        }
        Optional<LivingTargetCache> visibleMobs = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
        if (visibleMobs == null || visibleMobs.isEmpty()) {
            return false;
        }
        ItemEntity wantedItem = brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM).get();
        Optional<LivingEntity> avoidTarget = visibleMobs.get().stream(entity -> true)
            .filter(entity -> shouldAvoid(brain, bird, entity))
            .filter(entity -> entity.isInRange(wantedItem, bird.getFleeRange()))
            .findFirst();

        return !bird.getFood().test(bird.getMainHandStack()) && avoidTarget.isEmpty();
    }

    public static boolean shouldAvoid(Brain<?> brain, BirdEntity bird, LivingEntity target) {
        if (!bird.shouldAvoid(target)) {
            return false;
        }
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) {
            return false;
        }
        if (target instanceof PlayerEntity player && bird instanceof TrustingBirdEntity trusting && trusting.trusts(player)) {
            return false;
        }
        Optional<LivingEntity> attackTarget = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if (attackTarget != null && attackTarget.isPresent() && attackTarget.get().equals(target)) {
            return false;
        }
        return true;
    }

    public static boolean isPerching(BirdEntity entity) {
        return entity.getWorld().getBlockState(entity.getBlockPos().down()).isIn(FowlPlayBlockTags.PERCHES);
    }

    public static boolean noFoodInRange(BirdEntity bird) {
        Optional<ItemEntity> item = bird.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return item.isEmpty() || !item.get().isInRange(bird, ITEM_PICK_UP_RANGE);
    }
}
