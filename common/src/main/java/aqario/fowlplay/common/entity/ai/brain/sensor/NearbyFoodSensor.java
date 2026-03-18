package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;

public class NearbyFoodSensor<E extends BirdEntity> extends PredicateSensor<ItemEntity, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(SBLMemoryTypes.NEARBY_ITEMS.get());

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
        Brain<?> brain = bird.getBrain();
        double radius = bird.getAttributeValue(Attributes.FOLLOW_RANGE);
        List<ItemEntity> nearbyItems = EntityRetrievalUtil.getEntities(bird, radius, ItemEntity.class, item -> this.predicate().test(item, bird));
        BrainUtils.setMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get(), nearbyItems);

        if(BirdUtils.canPickupFood(bird)) {
            BrainUtils.setMemory(brain, FowlPlayMemoryTypes.SEES_FOOD.get(), Unit.INSTANCE);
        }
        else {
            BrainUtils.clearMemory(brain, FowlPlayMemoryTypes.SEES_FOOD.get());
        }
    }
}
