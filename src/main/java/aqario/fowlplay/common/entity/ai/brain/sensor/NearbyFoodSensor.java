package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FPMemoryTypes;
import aqario.fowlplay.core.FPSensorTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.Comparator;
import java.util.List;

public class NearbyFoodSensor<E extends BirdEntity> extends PredicateSensor<ItemEntity, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
        FPMemoryTypes.NEAREST_FOOD_ITEM.get()
    );

    public NearbyFoodSensor() {
        super((item, bird) -> bird.wantsToPickUp(item.getItem()) && bird.hasLineOfSight(item));
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FPSensorTypes.NEARBY_FOOD.get();
    }

    @Override
    protected void doTick(ServerLevel world, E bird) {
        if(bird.isSleeping()
            || bird.isMemoryPresent(FPMemoryTypes.CANNOT_PICKUP_FOOD.get())
            || bird.isMemoryPresent(FPMemoryTypes.IS_AVOIDING.get())
            || bird.isMemoryPresent(MemoryModuleType.ATTACK_TARGET)
            || bird.getFood().test(bird.getMainHandItem())
        ) {
            bird.clearMemory(FPMemoryTypes.NEAREST_FOOD_ITEM.get());
            return;
        }

        double radius = Math.min(24.0, bird.getAttributeValue(Attributes.FOLLOW_RANGE));
        List<ItemEntity> nearbyItems = EntityRetrievalUtil.getEntities(
            bird,
            radius,
            ItemEntity.class,
            item -> this.predicate().test(item, bird)
        );
        nearbyItems.sort(Comparator.comparingDouble(bird::distanceToSqr));
        if(nearbyItems.isEmpty()) {
            bird.clearMemory(FPMemoryTypes.NEAREST_FOOD_ITEM.get());
            return;
        }
        ItemEntity nearestFoodItem = nearbyItems.getFirst();

        if(isFoodLocationSafe(bird, nearestFoodItem)) {
            bird.setMemory(FPMemoryTypes.NEAREST_FOOD_ITEM.get(), nearestFoodItem);
        }
        else {
            bird.clearMemory(FPMemoryTypes.NEAREST_FOOD_ITEM.get());
        }
    }

    public static boolean isFoodLocationSafe(BirdEntity bird, ItemEntity nearestFoodItem) {
        if(!bird.isMemoryPresent(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)) {
            return true;
        }
        NearestVisibleLivingEntities visibleMobs = bird.getPresentMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
        return visibleMobs.find(entity -> true)
            .filter(entity -> BirdUtils.shouldAvoid(bird, entity))
            .filter(entity -> entity.closerThan(nearestFoodItem, bird.getFleeRange(entity)))
            .findAny()
            .isEmpty();
    }
}
