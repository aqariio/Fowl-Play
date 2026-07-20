package aqario.fowlplay.common.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;

public class ActivityListBuilder<E extends LivingEntity & SmartBrainOwner<E>> {
    private final Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends E>> activityList = new Object2ObjectOpenHashMap<>();

    public ActivityListBuilder<E> add(BrainActivityGroup<? extends E> activityGroup) {
        if(!activityGroup.getBehaviours().isEmpty()) {
            this.activityList.put(activityGroup.getActivity(), activityGroup);
        }
        return this;
    }

    public Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends E>> build() {
        return this.activityList;
    }
}
