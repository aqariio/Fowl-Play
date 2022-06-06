package pengugang.fowlplay.entity.ai;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import pengugang.fowlplay.entity.PenguinEntity;

public class WanderInWaterGoal extends MoveToTargetPosGoal {
    private static final int field_30385 = 1200;
    private final PenguinEntity penguin;

    public WanderInWaterGoal(PenguinEntity penguin, double speed) {
        super(penguin, penguin.isBaby() ? 2.0 : speed, 24);
        this.penguin = penguin;
        this.lowestY = -1;
    }

    public boolean shouldContinue() {
        return !this.penguin.isTouchingWater() && this.tryingTime <= 1200 && this.isTargetPos(this.penguin.world, this.targetPos);
    }

    public boolean canStart() {
        if (this.penguin.isBaby() && !this.penguin.isTouchingWater()) {
            return super.canStart();
        } else {
            return !this.penguin.isTouchingWater() && super.canStart();
        }
    }

    public boolean shouldResetPath() {
        return this.tryingTime % 160 == 0;
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOf(Blocks.WATER);
    }
}