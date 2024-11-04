package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.AnimationKeyframe;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.animation.PartAnimation;

public class GullEntityAnimations {
    public static final Animation GULL_IDLE = Animation.Builder.withLength(1.0f).looping()
        .addPartAnimation(
            "neck",
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
                new AnimationKeyframe(0f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
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
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.translate(-0.25f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.translate(0.25f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, -1f, -0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.rotate(0f, 2.5f, -5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4167667f, Animator.rotate(0f, 1.5f, -1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0f, 1f, 0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.rotate(0f, -2.5f, 5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.9167666f, Animator.rotate(0f, -1.5f, 1f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0f, -1f, -0.5f), PartAnimation.Interpolations.SPLINE)
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

    public static final Animation GULL_FLOAT = Animation.Builder.withLength(1f).looping()
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-21.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
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
                new AnimationKeyframe(0f, Animator.rotate(2.78f, -4.2f, -4.71f), PartAnimation.Interpolations.LINEAR)
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
                new AnimationKeyframe(0f, Animator.rotate(2.78f, 4.2f, 4.71f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-2.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1f, -1f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(21.57f, -8.12f, -9.95f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1f, -1f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(21.57f, 8.12f, 9.95f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(22.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation GULL_GLIDE = Animation.Builder.withLength(2f).looping()
        .addPartAnimation(
            "root",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(20f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, -0.75f, 2.25f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(65f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(92.99f, -5.05f, -4.59f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(92.99f, 5.05f, 4.59f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(2f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-1.53f, 2.31f, -13.2f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.rotate(-1.37f, 3.59f, -9.94f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1.0834333f, Animator.rotate(-1.55f, 2.1f, -13.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1.5416767f, Animator.rotate(-1.65f, -0.09f, -19.25f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(2f, Animator.rotate(-1.53f, 2.31f, -13.2f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_wing_outer",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-5.2f, -17.15f, 15.58f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(2f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-1.53f, -2.31f, 13.2f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4583433f, Animator.rotate(-1.65f, -0.48f, 17.81f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.8343334f, Animator.rotate(-1.61f, -1.4f, 15.49f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1.1676667f, Animator.rotate(-1.61f, -1.4f, 15.49f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1.4583433f, Animator.rotate(-1.47f, -2.87f, 11.76f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(2f, Animator.rotate(-1.53f, -2.31f, 13.2f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing_outer",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-5.2f, 17.15f, -15.58f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(2f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5416766f, Animator.rotate(-8f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7083434f, Animator.rotate(-8.44f, 0.2f, 0.46f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.9167666f, Animator.rotate(-9.18f, 0.41f, 0.91f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1.5416767f, Animator.rotate(-7.99f, -0.78f, -1.84f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(2f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1f, -1f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-70f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation GULL_FLAP = Animation.Builder.withLength(1f).looping()
        .addPartAnimation(
            "root",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.06f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.08343333f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.20834334f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.06f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5834334f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7083434f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0.06f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "root",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(20f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, -0.75f, 2.25f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(65f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(92.99f, -5.05f, -4.59f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(92.99f, 5.05f, 4.59f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.20834334f, Animator.translate(0.5f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7083434f, Animator.translate(0.5f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.03f, -8.54f, -41.26f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.041676664f, Animator.rotate(5.51f, -6.05f, -34.35f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.20834334f, Animator.rotate(4.65f, 15.46f, 25.14f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.rotate(-3.98f, 10.52f, 11.47f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.375f, Animator.rotate(-5.82f, 1.84f, -12.52f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0.03f, -8.54f, -41.26f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5416766f, Animator.rotate(5.51f, -6.05f, -34.35f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7083434f, Animator.rotate(4.65f, 15.46f, 25.14f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.rotate(-3.98f, 10.52f, 11.47f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.875f, Animator.rotate(-5.82f, 1.84f, -12.52f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0.03f, -8.54f, -41.26f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_wing_outer",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(1.18f, -17.86f, -5.39f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.041676664f, Animator.rotate(0.97f, -11.92f, -6.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.125f, Animator.rotate(-2.8f, -5.86f, 1.71f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.20834334f, Animator.rotate(-9.24f, -5.35f, 29.41f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(1.18f, -17.86f, -5.39f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5416766f, Animator.rotate(0.97f, -11.92f, -6.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.625f, Animator.rotate(-2.8f, -5.86f, 1.71f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7083434f, Animator.rotate(-9.24f, -5.35f, 29.41f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(1.18f, -17.86f, -5.39f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.20834334f, Animator.translate(-0.5f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7083434f, Animator.translate(-0.5f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.03f, 8.54f, 41.26f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.041676664f, Animator.rotate(5.51f, 6.05f, 34.35f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.20834334f, Animator.rotate(4.65f, -15.46f, -25.14f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.rotate(-3.98f, -10.52f, -11.47f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.375f, Animator.rotate(-5.82f, -1.84f, 12.52f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0.03f, 8.54f, 41.26f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5416766f, Animator.rotate(5.51f, 6.05f, 34.35f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7083434f, Animator.rotate(4.65f, -15.46f, -25.14f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7916766f, Animator.rotate(-3.98f, -10.52f, -11.47f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.875f, Animator.rotate(-5.82f, -1.84f, 12.52f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0.03f, 8.54f, 41.26f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing_outer",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(1.18f, 17.86f, 5.39f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.041676664f, Animator.rotate(0.97f, 11.92f, 6.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.125f, Animator.rotate(-2.8f, 5.86f, -1.71f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.20834334f, Animator.rotate(-9.24f, 5.35f, -29.41f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(1.18f, 17.86f, 5.39f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5416766f, Animator.rotate(0.97f, 11.92f, 6.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.625f, Animator.rotate(-2.8f, 5.86f, -1.71f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.7083434f, Animator.rotate(-9.24f, 5.35f, -29.41f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(1.18f, 17.86f, 5.39f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1f, -1f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-70f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();
}