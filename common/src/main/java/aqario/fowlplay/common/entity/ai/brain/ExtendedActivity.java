package aqario.fowlplay.common.entity.ai.brain;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.schedule.Activity;

/**
 * An extension of {@link Activity} that has the ability to be distinguished from an identically named activity registered with another mod id
 */
public class ExtendedActivity extends Activity {
    public ExtendedActivity(ResourceLocation name) {
        super(name.toString());
    }
}
