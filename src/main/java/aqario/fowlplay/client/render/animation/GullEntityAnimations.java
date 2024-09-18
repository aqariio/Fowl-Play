package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.AnimationKeyframe;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.animation.PartAnimation;

public class GullEntityAnimations {
    public static final Animation GULL_IDLE = Animation.Builder.withLength(1.0f).looping()
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
                new AnimationKeyframe(0f, Animator.rotate(-5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
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

    public static final Animation GULL_WALK = Animation.Builder.withLength(1.0f).looping()
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, -0.13f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.08343333f, Animator.translate(0f, 0.25f, -0.07f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 1f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.75f, Animator.translate(0f, 0.25f, -0.25f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.8343334f, Animator.translate(0f, -0.7f, -0.23f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0.1f, -0.13f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(4.38f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(42.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(10f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.75f, Animator.rotate(-32.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(4.38f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0.25f, -0.25f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3433333f, Animator.translate(0f, -0.7f, -0.23f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.1f, -0.13f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5834334f, Animator.translate(0f, 0.25f, -0.07f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.75f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 1f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(10f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(-32.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(4.37f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.75f, Animator.rotate(42.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(10f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.rotate(0f, 0f, 5.0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4167667f, Animator.rotate(0f, 0f, 1.0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0f, 0f, -0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.rotate(0f, 0f, -5.0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.9167666f, Animator.rotate(0f, 0f, -1.0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0f, 0f, 0.5f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .build();

    public static final Animation GULL_FLY = Animation.Builder.withLength(0f)
        .build();

    public static final Animation GULL_FLOAT = Animation.Builder.withLength(0f).looping()
        .build();
}