package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FPMemoryTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;

public class NearbyFoodSensor<E extends BirdEntity> extends PredicateSensor<ItemEntity, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
        SBLMemoryTypes.NEARBY_ITEMS.get(),
        FPMemoryTypes.SEES_FOOD.get()
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
        return SBLSensors.NEARBY_ITEMS.get();
    }

    @Override
    protected void doTick(ServerLevel world, E bird) {
        double radius = bird.getAttributeValue(Attributes.FOLLOW_RANGE);
        List<ItemEntity> nearbyItems = EntityRetrievalUtil.getEntities(bird, radius, ItemEntity.class, item -> this.predicate().test(item, bird));
        bird.setMemory(SBLMemoryTypes.NEARBY_ITEMS.get(), nearbyItems);

        if(BirdUtils.shouldPickupFood(bird)) {
            bird.setMemory(FPMemoryTypes.SEES_FOOD.get(), Unit.INSTANCE);
        }
        else {
            bird.clearMemory(FPMemoryTypes.SEES_FOOD.get());
        }
    }
}
