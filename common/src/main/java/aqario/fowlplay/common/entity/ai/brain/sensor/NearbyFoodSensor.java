package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
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
        super((item, bird) -> bird.canGather(item.getStack()) && bird.canSee(item));
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
    protected void sense(ServerWorld world, E bird) {
        Brain<?> brain = bird.getBrain();
        double radius = bird.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        List<ItemEntity> nearbyItems = EntityRetrievalUtil.getEntities(bird, radius, entity -> entity instanceof ItemEntity item && predicate().test(item, bird));
        BrainUtils.setMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get(), nearbyItems);

        if(Birds.canPickupFood(bird)) {
            BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.SEES_FOOD.get(), Unit.INSTANCE);
        }
        else {
            BrainUtils.clearMemory(brain, FowlPlayMemoryModuleType.SEES_FOOD.get());
        }
    }
}
