package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

public class AmphibiousNavigation extends AmphibiousPathNavigation {
    public AmphibiousNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    public boolean moveTo(@Nullable Path path, double speed) {
        if(path != null && this.mob instanceof FlyingBirdEntity flyingBird) {
            Birds.tryFlyingAlongPath(flyingBird, path);
        }
        return super.moveTo(path, speed);
    }
}
