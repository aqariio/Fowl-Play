package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import net.minecraft.world.World;

public class FlightNavigation extends BirdNavigation {
    private final FlyingBirdEntity bird;

    public FlightNavigation(FlyingBirdEntity bird, World world) {
        super(bird, world);
        this.bird = bird;
    }

//    @Override
//    public boolean startMovingTo(double x, double y, double z, double speed) {
//        this.bird.setMoveControl(Birds.shouldFlyToTarget(this.bird, new Vec3d(x, y, z)));
//        this.bird.getMoveControl().moveTo(x, y, z, speed);
//        return true;
//    }
//
//    @Override
//    public boolean startMovingTo(Entity entity, double speed) {
//        this.bird.setMoveControl(Birds.shouldFlyToTarget(this.bird, entity.getPos()));
//        this.bird.getMoveControl().moveTo(entity.getX(), entity.getY(), entity.getZ(), speed);
//        return true;
//    }
}
