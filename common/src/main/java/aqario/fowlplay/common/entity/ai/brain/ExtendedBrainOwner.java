package aqario.fowlplay.common.entity.ai.brain;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.tslat.smartbrainlib.api.SmartBrainOwner;

import java.util.Set;

public interface ExtendedBrainOwner<E extends LivingEntity & ExtendedBrainOwner<E>> extends SmartBrainOwner<E> {
    default Set<Activity> getScheduleIgnoringActivities() {
        return ObjectArraySet.of(
            Activity.AVOID,
            Activity.FIGHT
        );
    }
}
