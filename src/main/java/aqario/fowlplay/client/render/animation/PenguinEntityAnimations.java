package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class PenguinEntityAnimations {
    public static final Animation PENGUIN_IDLE = Animation.Builder.create(0f).looping()
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(1.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.5f, -0.75f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-1.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, -2.5f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation PENGUIN_WALK = Animation.Builder.create(1f).looping()
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(2.72f, -5f, -2.51f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0.31f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(2.72f, 5f, 2.51f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0.31f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(-0.25f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(-0.22f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0.25f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0.23f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 3.12f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, -3.12f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(0f, -5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.34f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 1.75f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.75f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, -0.25f, -2.75f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, -0.34f, -0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(3.13f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(3.13f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.75f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -0.25f, -2.75f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -0.34f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 1.75f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.75f, -0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(3.13f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .build();

    public static final Animation PENGUIN_SLIDE = Animation.Builder.create(0f)
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -8f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(90f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-40f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 1f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-75f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-30f, -62.5f, -37.5f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-30f, 62.5f, 37.5f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.5f, 0f, -0.5f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-15f, -15f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.5f, 0f, -0.5f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-15f, 15f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.5f, -0.15f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-12.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation PENGUIN_SWIM = Animation.Builder.create(0.5f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -8f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(90f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -1.25f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-40f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, -1.25f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-30f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(69.15f, -57.84f, -142.13f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(-21.62f, -46.83f, -36.59f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(69.15f, -57.84f, -142.13f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(69.15f, 57.84f, 142.13f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(-21.62f, 46.83f, 36.59f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(69.15f, 57.84f, 142.13f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.75f, -0.5f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(6.14f, -15.59f, 5.15f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.75f, -0.5f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(6.14f, 15.59f, -5.15f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.75f, -1.25f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-60f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        ).build();

    public static final Animation PENGUIN_DANCE = Animation.Builder.create(7.708343f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(-0.75f, 0f, -1.25f), Interpolations.STEP),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0.75f, 0f, -1.25f), Interpolations.STEP),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(3.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(3.7083435f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(4.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(4.958343f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(5.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(6.208343f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(6.916767f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(7.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, -25f, 0f), Interpolations.STEP),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 25f, 0f), Interpolations.STEP),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.5834334f, AnimationHelper.createRotationalVector(0f, 45f, 0f), Interpolations.STEP),
                new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 90f, 0f), Interpolations.STEP),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(0f, 135f, 0f), Interpolations.STEP),
                new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(0f, 180f, 0f), Interpolations.STEP),
                new Keyframe(0.9167666f, AnimationHelper.createRotationalVector(0f, 225f, 0f), Interpolations.STEP),
                new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 180f, 0f), Interpolations.STEP),
                new Keyframe(1.0834333f, AnimationHelper.createRotationalVector(0f, 135f, 0f), Interpolations.STEP),
                new Keyframe(1.1676667f, AnimationHelper.createRotationalVector(0f, 180f, 0f), Interpolations.STEP),
                new Keyframe(1.3433333f, AnimationHelper.createRotationalVector(0f, 270f, 0f), Interpolations.STEP),
                new Keyframe(1.4167667f, AnimationHelper.createRotationalVector(0f, 315f, 0f), Interpolations.STEP),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.875f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.291677f, AnimationHelper.createRotationalVector(0f, -45f, 0f), Interpolations.STEP),
                new Keyframe(6.676667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.343333f, AnimationHelper.createRotationalVector(0f, 45f, 0f), Interpolations.STEP),
                new Keyframe(7.708343f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.291677f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(6.416767f, AnimationHelper.createTranslationalVector(0f, 0f, 3f), Transformation.Interpolations.LINEAR),
                new Keyframe(6.5f, AnimationHelper.createTranslationalVector(0f, 0f, 3f), Transformation.Interpolations.LINEAR),
                new Keyframe(6.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(7.343333f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(7.458343f, AnimationHelper.createTranslationalVector(0f, 0f, 3f), Transformation.Interpolations.LINEAR),
                new Keyframe(7.541677f, AnimationHelper.createTranslationalVector(0f, 0f, 3f), Transformation.Interpolations.LINEAR),
                new Keyframe(7.676667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.5834333f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.625f, AnimationHelper.createRotationalVector(0f, -10f, -5f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.0416765f, AnimationHelper.createRotationalVector(0f, 10f, 5f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.4583435f, AnimationHelper.createRotationalVector(0f, -10f, -5f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.875f, AnimationHelper.createRotationalVector(0f, 10f, 5f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.0834335f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.875f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(4f, AnimationHelper.createRotationalVector(0f, 0f, -10f), Transformation.Interpolations.LINEAR),
                new Keyframe(4.343333f, AnimationHelper.createRotationalVector(0f, 0f, -10f), Interpolations.STEP),
                new Keyframe(4.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(5.25f, AnimationHelper.createRotationalVector(0f, 0f, 10f), Transformation.Interpolations.LINEAR),
                new Keyframe(5.583433f, AnimationHelper.createRotationalVector(0f, 0f, 10f), Interpolations.STEP),
                new Keyframe(5.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.291677f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(6.416767f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(6.5f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(6.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(7.343333f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(7.458343f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(7.541677f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(7.676667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.5834333f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(1.625f, AnimationHelper.createTranslationalVector(2.25f, 0f, -3f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.8343333f, AnimationHelper.createTranslationalVector(1f, 0f, -4f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.25f, AnimationHelper.createTranslationalVector(3.5f, 0f, -2f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.6766665f, AnimationHelper.createTranslationalVector(1f, 0f, -4f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.0834335f, AnimationHelper.createTranslationalVector(3.5f, 0f, -2f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, -15f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.5834333f, AnimationHelper.createRotationalVector(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(1.625f, AnimationHelper.createRotationalVector(-67.33f, 51f, -9.12f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(-74.66f, 37.01f, -18.24f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.25f, AnimationHelper.createRotationalVector(-60f, 65f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(-74.66f, 37.01f, -18.24f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.0834335f, AnimationHelper.createRotationalVector(-60f, 65f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.125f, AnimationHelper.createRotationalVector(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(3.875f, AnimationHelper.createRotationalVector(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(3.9583435f, AnimationHelper.createRotationalVector(0f, -60f, -95f), Transformation.Interpolations.LINEAR),
                new Keyframe(4f, AnimationHelper.createRotationalVector(-30f, -85f, -95f), Transformation.Interpolations.LINEAR),
                new Keyframe(4.125f, AnimationHelper.createRotationalVector(-70f, -85f, -95f), Transformation.Interpolations.LINEAR),
                new Keyframe(4.25f, AnimationHelper.createRotationalVector(-30f, -85f, -95f), Transformation.Interpolations.LINEAR),
                new Keyframe(4.375f, AnimationHelper.createRotationalVector(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(5.125f, AnimationHelper.createRotationalVector(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(5.625f, AnimationHelper.createRotationalVector(0f, 0f, -15f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.5834333f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(1.625f, AnimationHelper.createTranslationalVector(-2.25f, 0f, -3f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.8343333f, AnimationHelper.createTranslationalVector(-1f, 0f, -4f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.25f, AnimationHelper.createTranslationalVector(-3.5f, 0f, -2f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.6766665f, AnimationHelper.createTranslationalVector(-1f, 0f, -4f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.0834335f, AnimationHelper.createTranslationalVector(-3.5f, 0f, -2f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 15f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.5834333f, AnimationHelper.createRotationalVector(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(1.625f, AnimationHelper.createRotationalVector(-67.33f, -51f, 9.12f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(-74.66f, -37.01f, 18.24f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.25f, AnimationHelper.createRotationalVector(-60f, -65f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(-74.66f, -37.01f, 18.24f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.0834335f, AnimationHelper.createRotationalVector(-60f, -65f, 0f), Transformation.Interpolations.LINEAR),
                new Keyframe(3.125f, AnimationHelper.createRotationalVector(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(3.875f, AnimationHelper.createRotationalVector(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(4.375f, AnimationHelper.createRotationalVector(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(5.125f, AnimationHelper.createRotationalVector(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(5.208343f, AnimationHelper.createRotationalVector(0f, 60f, 95f), Transformation.Interpolations.LINEAR),
                new Keyframe(5.25f, AnimationHelper.createRotationalVector(-30f, 85f, 95f), Transformation.Interpolations.LINEAR),
                new Keyframe(5.375f, AnimationHelper.createRotationalVector(-70f, 85f, 95f), Transformation.Interpolations.LINEAR),
                new Keyframe(5.5f, AnimationHelper.createRotationalVector(-30f, 85f, 95f), Transformation.Interpolations.LINEAR),
                new Keyframe(5.625f, AnimationHelper.createRotationalVector(0f, 0f, 15f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(3.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(3.7083435f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(4.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(4.958343f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(5.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(6.208343f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(6.916767f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(7.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, AnimationHelper.createRotationalVector(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(3.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, AnimationHelper.createRotationalVector(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(3.7083435f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, AnimationHelper.createRotationalVector(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(4.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, AnimationHelper.createRotationalVector(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(4.958343f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, AnimationHelper.createRotationalVector(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(5.875f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, AnimationHelper.createRotationalVector(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(6.208343f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, AnimationHelper.createRotationalVector(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(6.916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, AnimationHelper.createRotationalVector(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(7.25f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(3.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(3.7083435f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(4.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(4.958343f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(5.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(6.208343f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(6.916767f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, AnimationHelper.createTranslationalVector(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(7.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, AnimationHelper.createRotationalVector(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(3.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, AnimationHelper.createRotationalVector(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(3.7083435f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, AnimationHelper.createRotationalVector(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(4.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, AnimationHelper.createRotationalVector(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(4.958343f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, AnimationHelper.createRotationalVector(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(5.875f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, AnimationHelper.createRotationalVector(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(6.208343f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, AnimationHelper.createRotationalVector(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(6.916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, AnimationHelper.createRotationalVector(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(7.25f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .build();
}