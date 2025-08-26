package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCache;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GroundNavigation extends SmoothGroundNavigation {
    public GroundNavigation(MobEntity mob, World level) {
        super(mob, level);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int maxVisitedNodes) {
        this.nodeMaker = new LandPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);

        return new PathNodeNavigator(this.nodeMaker, maxVisitedNodes) {
            @Nullable
            @Override
            public Path findPathToAny(ChunkCache navigationRegion, MobEntity mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
                final Path path = super.findPathToAny(navigationRegion, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);

                return GroundNavigation.this.patchPath(path);
            }
        };
    }

    public @Nullable Path patchPath(@Nullable Path path) {
        if(path == null) {
            return null;
        }

        Path newPath = new Path(path.nodes, path.getTarget(), path.reachesTarget()) {
            @Override
            public Vec3d getNodePosition(Entity entity1, int nodeIndex) {
                return GroundNavigation.this.getEntityPosAtNode(nodeIndex);
            }
        };
        PathNode[] debugNodes = path.getDebugNodes();
        PathNode[] debugSecondNodes = path.getDebugSecondNodes();
        Set<TargetPathNode> debugTargetNodes = path.debugTargetNodes;
        newPath.setDebugInfo(debugNodes, debugSecondNodes, debugTargetNodes);

        return newPath;
    }

    @Override
    public boolean startMovingAlong(@Nullable Path path, double speed) {
        if(path != null && this.entity instanceof FlyingBirdEntity flyingBird) {
            Birds.tryFlyingAlongPath(flyingBird, path);
        }
        return super.startMovingAlong(path, speed);
    }
}
