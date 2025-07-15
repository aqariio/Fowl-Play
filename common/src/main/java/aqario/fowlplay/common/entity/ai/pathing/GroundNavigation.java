package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import org.jetbrains.annotations.Nullable;

public class GroundNavigation extends SmoothGroundNavigation {
    public GroundNavigation(MobEntity mob, World level) {
        super(mob, level);
    }

    @Override
    public boolean startMovingAlong(@Nullable Path path, double speed) {
        if(path != null && this.entity instanceof FlyingBirdEntity flyingBird) {
            Birds.tryFlyingAlongPath(flyingBird, path);
        }
        return super.startMovingAlong(path, speed);
    }
}
