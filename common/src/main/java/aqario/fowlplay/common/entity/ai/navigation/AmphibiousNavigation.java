package aqario.fowlplay.common.entity.ai.navigation;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.tslat.smartbrainlib.api.core.navigation.SmoothAmphibiousPathNavigation;
import org.jetbrains.annotations.Nullable;

public class AmphibiousNavigation extends SmoothAmphibiousPathNavigation {
    public AmphibiousNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    public @Nullable Path patchPath(@Nullable Path path) {
        Path newPath = super.patchPath(path);
        if(newPath == null) {
            return null;
        }
        // noinspection ConstantConditions
        Path.DebugData debugNodeInfo = path.debugData();
        if(debugNodeInfo != null) {
            newPath.setDebug(debugNodeInfo.openSet(), debugNodeInfo.closedSet(), debugNodeInfo.targetNodes());
        }
        return newPath;
    }

    @Override
    public boolean moveTo(@Nullable Path path, double speed) {
        if(path != null && this.mob instanceof FlyingBirdEntity flyingBird) {
            Birds.tryFlyingAlongPath(flyingBird, path);
        }
        return super.moveTo(path, speed);
    }
}
