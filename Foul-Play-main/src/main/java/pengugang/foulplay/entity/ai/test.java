package pengugang.foulplay.entity.ai;

import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

public class test extends WanderAroundFarGoal {
    public test(PathAwareEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d, 1);
    }

    @Override
    protected Vec3d getWanderTarget() {
        return NoPenaltyTargeting.find(this.mob, 5, 7);
    }
}