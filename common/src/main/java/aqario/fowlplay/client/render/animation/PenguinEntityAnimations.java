package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.AnimationKeyframe;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.animation.PartAnimation;

public class PenguinEntityAnimations {
    public static final Animation PENGUIN_IDLE = Animation.Builder.withLength(1f).looping()
        .build();

    public static final Animation PENGUIN_WALK = Animation.Builder.withLength(1f).looping()
        .build();

    public static final Animation PENGUIN_SLIDE = Animation.Builder.withLength(0f)
        .addPartAnimation(
            "root",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, -8f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "root",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(90f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-40f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 1.5f, 1f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-75f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-30f, -62.5f, -37.5f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-30f, 62.5f, 37.5f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0.5f, 0f, -1f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-15f, -15f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(-0.5f, 0f, -1f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-15f, 15f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation PENGUIN_SWIM = Animation.Builder.withLength(0f).looping()
        .addPartAnimation(
            "root",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, -8f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "root",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(90f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-40f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.25f, -1.25f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-30f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(71.16f, -64.72f, -135.88f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(71.16f, 64.72f, 135.88f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0.5f, -0.75f, -0.5f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(7.5f, -15f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(-0.5f, -0.75f, -0.5f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(7.5f, 15f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.75f, -1.25f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-60f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation PENGUIN_DANCE = Animation.Builder.withLength(7.708343f).looping()
        .addPartAnimation(
            "root",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.125f, Animator.translate(-0.75f, 0f, -1.25f), Interpolations.STEP),
                new AnimationKeyframe(0.25f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.375f, Animator.translate(0.75f, 0f, -1.25f), Interpolations.STEP),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.2083435f, Animator.translate(0f, -3f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.375f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.5416765f, Animator.translate(0f, -3f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.7083435f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.875f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.458343f, Animator.translate(0f, -3f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.625f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.791677f, Animator.translate(0f, -3f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.958343f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.708343f, Animator.translate(0f, -3f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.875f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.041677f, Animator.translate(0f, -3f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.208343f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.75f, Animator.translate(0f, -3f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.916767f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(7.083433f, Animator.translate(0f, -3f, 0f), Interpolations.STEP),
                new AnimationKeyframe(7.25f, Animator.translate(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "root",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.125f, Animator.rotate(0f, -25f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.25f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.375f, Animator.rotate(0f, 25f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.5f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.5834334f, Animator.rotate(0f, 45f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.6766666f, Animator.rotate(0f, 90f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.75f, Animator.rotate(0f, 135f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.8343334f, Animator.rotate(0f, 180f, 0f), Interpolations.STEP),
                new AnimationKeyframe(0.9167666f, Animator.rotate(0f, 225f, 0f), Interpolations.STEP),
                new AnimationKeyframe(1f, Animator.rotate(0f, 180f, 0f), Interpolations.STEP),
                new AnimationKeyframe(1.0834333f, Animator.rotate(0f, 135f, 0f), Interpolations.STEP),
                new AnimationKeyframe(1.1676667f, Animator.rotate(0f, 180f, 0f), Interpolations.STEP),
                new AnimationKeyframe(1.3433333f, Animator.rotate(0f, 270f, 0f), Interpolations.STEP),
                new AnimationKeyframe(1.4167667f, Animator.rotate(0f, 315f, 0f), Interpolations.STEP),
                new AnimationKeyframe(1.5f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.125f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.875f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.291677f, Animator.rotate(0f, -45f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.676667f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(7.343333f, Animator.rotate(0f, 45f, 0f), Interpolations.STEP),
                new AnimationKeyframe(7.708343f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.291677f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(6.416767f, Animator.translate(0f, 0f, 3f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(6.5f, Animator.translate(0f, 0f, 3f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(6.625f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(7.343333f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(7.458343f, Animator.translate(0f, 0f, 3f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(7.541677f, Animator.translate(0f, 0f, 3f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(7.676667f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.5834333f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.625f, Animator.rotate(0f, -10f, -5f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.8343333f, Animator.rotate(-5f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.0416765f, Animator.rotate(0f, 10f, 5f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.25f, Animator.rotate(5f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.4583435f, Animator.rotate(0f, -10f, -5f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.6766665f, Animator.rotate(-5f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.875f, Animator.rotate(0f, 10f, 5f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.0834335f, Animator.rotate(5f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.125f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.875f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(4f, Animator.rotate(0f, 0f, -10f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(4.343333f, Animator.rotate(0f, 0f, -10f), Interpolations.STEP),
                new AnimationKeyframe(4.375f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.125f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(5.25f, Animator.rotate(0f, 0f, 10f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(5.583433f, Animator.rotate(0f, 0f, 10f), Interpolations.STEP),
                new AnimationKeyframe(5.625f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.291677f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(6.416767f, Animator.rotate(30f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(6.5f, Animator.rotate(30f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(6.625f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(7.343333f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(7.458343f, Animator.rotate(30f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(7.541677f, Animator.rotate(30f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(7.676667f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.5834333f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(1.625f, Animator.translate(2.25f, 0f, -3f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.8343333f, Animator.translate(1f, 0f, -4f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.25f, Animator.translate(3.5f, 0f, -2f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.6766665f, Animator.translate(1f, 0f, -4f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.0834335f, Animator.translate(3.5f, 0f, -2f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.125f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.875f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.375f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.125f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.625f, Animator.translate(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, -15f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.5834333f, Animator.rotate(0f, 0f, -15f), Interpolations.STEP),
                new AnimationKeyframe(1.625f, Animator.rotate(-67.33f, 51f, -9.12f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.8343333f, Animator.rotate(-74.66f, 37.01f, -18.24f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.25f, Animator.rotate(-60f, 65f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.6766665f, Animator.rotate(-74.66f, 37.01f, -18.24f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.0834335f, Animator.rotate(-60f, 65f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.125f, Animator.rotate(0f, 0f, -15f), Interpolations.STEP),
                new AnimationKeyframe(3.875f, Animator.rotate(0f, 0f, -15f), Interpolations.STEP),
                new AnimationKeyframe(3.9583435f, Animator.rotate(0f, -60f, -95f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(4f, Animator.rotate(-30f, -85f, -95f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(4.125f, Animator.rotate(-70f, -85f, -95f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(4.25f, Animator.rotate(-30f, -85f, -95f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(4.375f, Animator.rotate(0f, 0f, -15f), Interpolations.STEP),
                new AnimationKeyframe(5.125f, Animator.rotate(0f, 0f, -15f), Interpolations.STEP),
                new AnimationKeyframe(5.625f, Animator.rotate(0f, 0f, -15f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.5834333f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(1.625f, Animator.translate(-2.25f, 0f, -3f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.8343333f, Animator.translate(-1f, 0f, -4f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.25f, Animator.translate(-3.5f, 0f, -2f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.6766665f, Animator.translate(-1f, 0f, -4f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.0834335f, Animator.translate(-3.5f, 0f, -2f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.125f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.875f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.375f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.125f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.625f, Animator.translate(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 15f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.5834333f, Animator.rotate(0f, 0f, 15f), Interpolations.STEP),
                new AnimationKeyframe(1.625f, Animator.rotate(-67.33f, -51f, 9.12f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(1.8343333f, Animator.rotate(-74.66f, -37.01f, 18.24f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.25f, Animator.rotate(-60f, -65f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(2.6766665f, Animator.rotate(-74.66f, -37.01f, 18.24f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.0834335f, Animator.rotate(-60f, -65f, 0f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(3.125f, Animator.rotate(0f, 0f, 15f), Interpolations.STEP),
                new AnimationKeyframe(3.875f, Animator.rotate(0f, 0f, 15f), Interpolations.STEP),
                new AnimationKeyframe(4.375f, Animator.rotate(0f, 0f, 15f), Interpolations.STEP),
                new AnimationKeyframe(5.125f, Animator.rotate(0f, 0f, 15f), Interpolations.STEP),
                new AnimationKeyframe(5.208343f, Animator.rotate(0f, 60f, 95f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(5.25f, Animator.rotate(-30f, 85f, 95f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(5.375f, Animator.rotate(-70f, 85f, 95f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(5.5f, Animator.rotate(-30f, 85f, 95f), PartAnimation.Interpolations.LINEAR),
                new AnimationKeyframe(5.625f, Animator.rotate(0f, 0f, 15f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.2083435f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(3.375f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.5416765f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(3.7083435f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.458343f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(4.625f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.791677f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(4.958343f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.708343f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(5.875f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.041677f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(6.208343f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.75f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(6.916767f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(7.083433f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(7.25f, Animator.translate(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.2083435f, Animator.rotate(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new AnimationKeyframe(3.375f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.5416765f, Animator.rotate(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new AnimationKeyframe(3.7083435f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.458343f, Animator.rotate(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new AnimationKeyframe(4.625f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.791677f, Animator.rotate(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new AnimationKeyframe(4.958343f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.708343f, Animator.rotate(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new AnimationKeyframe(5.875f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.041677f, Animator.rotate(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new AnimationKeyframe(6.208343f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.75f, Animator.rotate(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new AnimationKeyframe(6.916767f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(7.083433f, Animator.rotate(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new AnimationKeyframe(7.25f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.2083435f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(3.375f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.5416765f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(3.7083435f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.458343f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(4.625f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.791677f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(4.958343f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.708343f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(5.875f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.041677f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(6.208343f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.75f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(6.916767f, Animator.translate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(7.083433f, Animator.translate(0f, 2f, -2f), Interpolations.STEP),
                new AnimationKeyframe(7.25f, Animator.translate(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.2083435f, Animator.rotate(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new AnimationKeyframe(3.375f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(3.5416765f, Animator.rotate(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new AnimationKeyframe(3.7083435f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.458343f, Animator.rotate(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new AnimationKeyframe(4.625f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(4.791677f, Animator.rotate(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new AnimationKeyframe(4.958343f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(5.708343f, Animator.rotate(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new AnimationKeyframe(5.875f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.041677f, Animator.rotate(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new AnimationKeyframe(6.208343f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(6.75f, Animator.rotate(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new AnimationKeyframe(6.916767f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP),
                new AnimationKeyframe(7.083433f, Animator.rotate(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new AnimationKeyframe(7.25f, Animator.rotate(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .build();
}