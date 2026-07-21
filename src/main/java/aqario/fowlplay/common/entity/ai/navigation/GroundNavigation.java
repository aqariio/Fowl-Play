package aqario.fowlplay.common.entity.ai.navigation;

import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GroundNavigation extends SmoothGroundNavigation {
    public GroundNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new WalkNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);

        return new PathFinder(this.nodeEvaluator, maxVisitedNodes) {
            @Nullable
            @Override
            public Path findPath(PathNavigationRegion navigationRegion, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
                GroundNavigation.this.nodeEvaluator.mob = GroundNavigation.this.mob;
                final Path path = super.findPath(navigationRegion, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
                GroundNavigation.this.nodeEvaluator.mob = null;

                return GroundNavigation.this.patchPath(path);
            }
        };
    }

    public @Nullable Path patchPath(@Nullable Path path) {
        if(path == null) {
            return null;
        }

        Path newPath = new Path(path.nodes, path.getTarget(), path.canReach()) {
            @Override
            public Vec3 getEntityPosAtNode(Entity entity1, int nodeIndex) {
                return GroundNavigation.this.getEntityPosAtNode(nodeIndex);
            }
        };
        Node[] debugNodes = path.getOpenSet();
        Node[] debugSecondNodes = path.getClosedSet();
        Set<Target> debugTargetNodes = path.targetNodes;
        newPath.setDebug(debugNodes, debugSecondNodes, debugTargetNodes);

        return newPath;
    }

    @Override
    public boolean moveTo(@Nullable Path path, double speed) {
        if(path != null && this.mob instanceof FlyingBirdEntity flyingBird) {
            BirdUtils.tryFlyingAlongPath(flyingBird, path);
        }
        return super.moveTo(path, speed);
    }

    @Override
    protected void followThePath() {
        // temporarily set the mob since it gets called in 1.20.1 in NodeEvaluator.getBlockPathType()
        this.nodeEvaluator.mob = this.mob;
        super.followThePath();
        this.nodeEvaluator.mob = null;
    }
}
