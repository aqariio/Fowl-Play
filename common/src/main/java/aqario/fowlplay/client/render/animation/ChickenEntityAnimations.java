package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.AnimationKeyframe;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.animation.PartAnimation;

public class ChickenEntityAnimations {
    public static final Animation CHICKEN_IDLE = Animation.Builder.withLength(1f).looping()
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(1f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(-0.2f, -0.2f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-2.22f, -4.2f, -4.71f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0.2f, -0.2f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-2.22f, 4.2f, 4.71f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(2.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, -10f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 10f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation CHICKEN_WALK = Animation.Builder.withLength(1f).looping()
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, -0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.translate(-0.25f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4167667f, Animator.translate(-0.12f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, -0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.translate(0.25f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.9167666f, Animator.translate(0.12f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, -0.5f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(5f, -1f, -0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.rotate(0f, 2.5f, -5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4167667f, Animator.rotate(0f, 1.5f, -1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(5f, 1f, 0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.rotate(0f, -2.5f, 5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.9167666f, Animator.rotate(0f, -1.5f, 1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(5f, -1f, -0.5f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, -1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.08343333f, Animator.translate(0f, 0.25f, -0.07f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4167667f, Animator.translate(0f, 1.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 1.37f, -0.22f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.75f, Animator.translate(0f, 0.25f, -1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.8343334f, Animator.translate(0f, -0.7f, -0.23f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0.1f, -1f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(4.38f, -10f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(42.5f, -10f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(10f, -10f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.75f, Animator.rotate(-32.5f, -10f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(4.38f, -10f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1.37f, -0.22f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0.25f, -1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3433333f, Animator.translate(0f, -0.7f, -0.23f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.1f, -1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5834334f, Animator.translate(0f, 0.25f, -0.07f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.75f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.9167666f, Animator.translate(0f, 1.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 1.37f, -0.22f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(10f, 10f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(-32.5f, 10f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(4.37f, 10f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.75f, Animator.rotate(42.5f, 10f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(10f, 10f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.2f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.08343333f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.2f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5834334f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0.2f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 1f, 0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.rotate(0f, -2.5f, 5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4167667f, Animator.rotate(0f, -1.5f, 1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0f, -1f, -0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.rotate(0f, 2.5f, -5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.9167666f, Animator.rotate(0f, 1.5f, -1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0f, 1f, 0.5f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .build();

    public static final Animation CHICKEN_FLAP = Animation.Builder.withLength(0f)
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(1f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(-0.2f, -0.2f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-2.22f, -4.2f, -4.71f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0.2f, -0.2f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-2.22f, 4.2f, 4.71f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(2.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-8.11f, -11.58f, -10.05f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-8.11f, 11.58f, 10.05f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation CHICKEN_FLOAT = Animation.Builder.withLength(0f).looping()
        .addPartAnimation(
            "root",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(20f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, -0.5f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-17.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-1.72f, -7.52f, -7.11f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-1.72f, 7.52f, 7.11f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-27.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, -15f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 15f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();
}