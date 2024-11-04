package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.ChunkCache;
import org.jetbrains.annotations.Nullable;

public class FlightPathNodeMaker extends LandPathNodeMaker {
    private float oldWalkablePenalty;

    @Override
    public void init(ChunkCache cachedWorld, MobEntity entity) {
        super.init(cachedWorld, entity);
        this.oldWalkablePenalty = entity.getPenalty(PathNodeType.WALKABLE);
        entity.addPathfindingPenalty(PathNodeType.WALKABLE, 6.0F);
    }

    @Override
    public void clear() {
        this.entity.addPathfindingPenalty(PathNodeType.WALKABLE, this.oldWalkablePenalty);
        super.clear();
    }

    @Override
    public PathNode getStart() {
        if (!(this.entity instanceof FlyingBirdEntity bird) || !bird.isFlying()) {
            return super.getStart();
        }
        return this.getStart(
            new BlockPos(
                MathHelper.floor(this.entity.getBounds().minX), MathHelper.floor(this.entity.getBounds().minY + 0.5), MathHelper.floor(this.entity.getBounds().minZ)
            )
        );
    }

    @Override
    public TargetPathNode getNode(double x, double y, double z) {
        return this.getTargetPathNode(x, y + 0.5, z);
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        int i = super.getSuccessors(successors, node);
        PathNodeType pathNodeType = this.getNodeType(node.x, node.y + 1, node.z);
        PathNodeType pathNodeType2 = this.getNodeType(node.x, node.y, node.z);
        int j;
        if (this.entity.getPenalty(pathNodeType) >= 0.0F && pathNodeType2 != PathNodeType.STICKY_HONEY) {
            j = MathHelper.floor(Math.max(1.0F, this.entity.getStepHeight()));
        }
        else {
            j = 0;
        }

        double d = this.getFeetY(new BlockPos(node.x, node.y, node.z));
        PathNode pathNode = this.getPathNode(node.x, node.y + 1, node.z, Math.max(0, j - 1), d, Direction.UP, pathNodeType2);
        PathNode pathNode2 = this.getPathNode(node.x, node.y - 1, node.z, j, d, Direction.DOWN, pathNodeType2);
        if (this.isValidAerialAdjacentSuccessor(pathNode, node)) {
            successors[i++] = pathNode;
        }

        if (this.isValidAerialAdjacentSuccessor(pathNode2, node) && pathNodeType2 != PathNodeType.TRAPDOOR) {
            successors[i++] = pathNode2;
        }

        return i;
    }

    private boolean isValidAerialAdjacentSuccessor(@Nullable PathNode node, PathNode successor) {
        return this.isValidAdjacentSuccessor(node, successor) && node.type == PathNodeType.OPEN;
    }

    @Override
    public PathNodeType getDefaultNodeType(PathContext context, int x, int y, int z) {
        PathNodeType pathNodeType = context.getNodeType(x, y, z);
        if (pathNodeType == PathNodeType.OPEN) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (Direction direction : Direction.values()) {
                mutable.set(x, y, z).move(direction);
                PathNodeType pathNodeType2 = context.getNodeType(mutable.getX(), mutable.getY(), mutable.getZ());
                if (pathNodeType2 == PathNodeType.BLOCKED) {
                    return PathNodeType.OPEN;
                }
            }

            return PathNodeType.OPEN;
        }
        else {
            return super.getDefaultNodeType(context, x, y, z);
        }
    }
}
