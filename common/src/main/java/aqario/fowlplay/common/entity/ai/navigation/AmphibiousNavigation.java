package aqario.fowlplay.common.entity.ai.navigation;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.navigation.SmoothAmphibiousPathNavigation;
import org.jetbrains.annotations.Nullable;

public class AmphibiousNavigation extends SmoothAmphibiousPathNavigation {
    private boolean surfaceOnly = false;

    public AmphibiousNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    public AmphibiousNavigation setSurfaceOnly() {
        this.surfaceOnly = true;
        return this;
    }

    @Override
    protected Vec3 getTempMobPos() {
        if(this.surfaceOnly) {
            return new Vec3(this.mob.getX(), this.getSurfaceY(), this.mob.getZ());
        }
        return super.getTempMobPos();
    }

    protected int getSurfaceY() {
        if(this.mob.isInWater()) {
            int i = this.mob.getBlockY();
            BlockState blockState = this.level.getBlockState(BlockPos.containing(this.mob.getX(), i, this.mob.getZ()));
            int j = 0;

            while(blockState.is(Blocks.WATER)) {
                blockState = this.level.getBlockState(BlockPos.containing(this.mob.getX(), ++i, this.mob.getZ()));
                if(++j > 16) {
                    return this.mob.getBlockY();
                }
            }

            return i;
        }
        return Mth.floor(this.mob.getY() + 0.5);
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
