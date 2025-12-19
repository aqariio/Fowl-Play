package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.archaeopteryx.common.ai.sensing.ExtendedSensor;
import aqario.archaeopteryx.common.ai.sensing.PredicateSensor;
import aqario.archaeopteryx.core.registry.AtrxMemoryTypes;
import aqario.archaeopteryx.core.registry.AtrxSensorTypes;
import aqario.archaeopteryx.core.util.EntityRetrievalUtil;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;

public class NearbyFoodSensor<E extends BirdEntity & BirdBrain<E>> extends PredicateSensor<ItemEntity, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(AtrxMemoryTypes.NEARBY_ITEMS.get());

    public NearbyFoodSensor() {
        super((item, bird) -> bird.wantsToPickUp(item.getItem()) && bird.hasLineOfSight(item));
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return AtrxSensorTypes.NEARBY_ITEMS.get();
    }

    @Override
    protected void doTick(ServerLevel world, E bird) {
        double radius = bird.getAttributeValue(Attributes.FOLLOW_RANGE);
        List<ItemEntity> nearbyItems = EntityRetrievalUtil.getEntities(bird, radius, ItemEntity.class, item -> this.predicate().test(item, bird));
        bird.setMemory(AtrxMemoryTypes.NEARBY_ITEMS.get(), nearbyItems);

        if(BirdUtils.canPickupFood(bird)) {
            bird.setMemory(FowlPlayMemoryTypes.SEES_FOOD.get(), Unit.INSTANCE);
        }
        else {
            bird.clearMemory(FowlPlayMemoryTypes.SEES_FOOD.get());
        }
    }
}
