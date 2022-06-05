package pengugang.foulplay.entity.ai;

import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import pengugang.foulplay.entity.PenguinEntity;

public class PenguinWanderAroundGoal extends WanderAroundFarGoal {
    public PenguinWanderAroundGoal(PathAwareEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d, 1);
    }

    @Override
    protected Vec3d getWanderTarget() {
        return NoPenaltyTargeting.find(this.mob, 5, 7);
    }
}