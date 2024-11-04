package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.AnimationKeyframe;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.animation.PartAnimation;

public class BlueJayEntityAnimations {
    public static final Animation BLUE_JAY_IDLE = Animation.Builder.withLength(0f).looping()
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(-0.2f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.22f, -5.95f, -2.32f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0.2f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.22f, 5.95f, 2.32f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.1f, -0.25f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(12.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-0.05f, -4.83f, -3.7f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-0.05f, 4.83f, 3.7f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation BLUE_JAY_WALK = Animation.Builder.withLength(1f).looping()
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(17.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.rotate(19f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(17.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.rotate(19f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(17.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, -0.15f, -0.2f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-15f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-2.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.translate(0f, 0f, 0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.8343334f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.rotate(50f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.8343334f, Animator.rotate(-25f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3433333f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.translate(0f, 0f, 0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3433333f, Animator.rotate(-25f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.rotate(50f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "root",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .build();

    public static final Animation BLUE_JAY_FLOAT = Animation.Builder.withLength(0f).looping()
        .build();

    public static final Animation BLUE_JAY_FLAP = Animation.Builder.withLength(0.5f).looping()
        .addPartAnimation(
            "root",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.06f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.041676664f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.10416767f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0.06f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2916767f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3541677f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.06f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "root",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(35f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.5f, 0.25f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(50f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1f, -0.85f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-80f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.10416767f, Animator.translate(0.5f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3541677f, Animator.translate(0.5f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.03f, -8.54f, -41.26f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.020834332f, Animator.rotate(5.51f, -6.05f, -34.35f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.10416767f, Animator.rotate(4.65f, 15.46f, 25.14f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.14583434f, Animator.rotate(-3.98f, 10.52f, 11.47f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.1875f, Animator.rotate(-5.82f, 1.84f, -12.52f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(0.03f, -8.54f, -41.26f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2708343f, Animator.rotate(5.51f, -6.05f, -34.35f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3541677f, Animator.rotate(4.65f, 15.46f, 25.14f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3958343f, Animator.rotate(-3.98f, 10.52f, 11.47f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4375f, Animator.rotate(-5.82f, 1.84f, -12.52f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0.03f, -8.54f, -41.26f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_wing_outer",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(1.18f, -17.86f, -5.39f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.020834332f, Animator.rotate(0.97f, -11.92f, -6.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.0625f, Animator.rotate(-2.8f, -5.86f, 1.71f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.10416767f, Animator.rotate(-9.24f, -5.35f, 29.41f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(1.18f, -17.86f, -5.39f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2708343f, Animator.rotate(0.97f, -11.92f, -6.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3125f, Animator.rotate(-2.8f, -5.86f, 1.71f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3541677f, Animator.rotate(-9.24f, -5.35f, 29.41f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(1.18f, -17.86f, -5.39f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.10416767f, Animator.translate(-0.5f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3541677f, Animator.translate(-0.5f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing_open",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.03f, 8.54f, 41.26f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.020834332f, Animator.rotate(5.51f, 6.05f, 34.35f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.10416767f, Animator.rotate(4.65f, -15.46f, -25.14f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.14583434f, Animator.rotate(-3.98f, -10.52f, -11.47f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.1875f, Animator.rotate(-5.82f, -1.84f, 12.52f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(0.03f, 8.54f, 41.26f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2708343f, Animator.rotate(5.51f, 6.05f, 34.35f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3541677f, Animator.rotate(4.65f, -15.46f, -25.14f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3958343f, Animator.rotate(-3.98f, -10.52f, -11.47f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.4375f, Animator.rotate(-5.82f, -1.84f, 12.52f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0.03f, 8.54f, 41.26f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing_outer",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(1.18f, 17.86f, 5.39f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.020834332f, Animator.rotate(0.97f, 11.92f, 6.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.0625f, Animator.rotate(-2.8f, 5.86f, -1.71f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.10416767f, Animator.rotate(-9.24f, 5.35f, -29.41f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(1.18f, 17.86f, 5.39f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.2708343f, Animator.rotate(0.97f, 11.92f, 6.72f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3125f, Animator.rotate(-2.8f, 5.86f, -1.71f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3541677f, Animator.rotate(-9.24f, 5.35f, -29.41f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(1.18f, 17.86f, 5.39f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.25f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
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
        .build();

    public static final Animation BLUE_JAY_GLIDE = Animation.Builder.withLength(2f).looping()
        .addPartAnimation(
            "root",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(35f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.5f, 0.25f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(50f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1f, -0.85f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-80f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
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
        .build();
}