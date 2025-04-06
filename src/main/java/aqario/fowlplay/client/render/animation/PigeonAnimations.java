package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class PigeonAnimations {
    public static final Animation STANDING = Animation.Builder.create(1.0F).looping()
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0.3f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(2.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.2f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.23f, -4.53f, -2.84f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.2f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.23f, 4.53f, 2.84f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-0.05f, -4.83f, -3.7f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-0.05f, 4.83f, 3.7f), Transformation.Interpolations.LINEAR)
            )
        ).build();

    public static final Animation WALKING = Animation.Builder.create(1.0f).looping()
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.2f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.08343333f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.2f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5834334f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.2f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(10f, 1f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(10f, -2.5f, 5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(10f, -1.5f, 1f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(10f, -1f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createRotationalVector(10f, 2.5f, -5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createRotationalVector(10f, 1.5f, -1f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(10f, 1f, 0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(-0.25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(-0.12f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createTranslationalVector(0.25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createTranslationalVector(0.12f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, -0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, -1f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-10f, 2.5f, -5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(-10f, 1.5f, -1f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(-5f, 1f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createRotationalVector(-10f, -2.5f, 5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createRotationalVector(-10f, -1.5f, 1f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-5f, -1f, -0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.08343333f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0.43f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 1.5f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 1.37f, 0.28f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, 0.25f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.8343334f, AnimationHelper.createTranslationalVector(0f, -0.7f, 0.27f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.1f, -0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(4.38f, -10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(42.5f, -10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(10f, -10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(-32.5f, -10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(4.38f, -10f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 1.37f, 0.28f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0.25f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createTranslationalVector(0f, -0.7f, 0.27f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.1f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5834334f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0.43f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createTranslationalVector(0f, 1.5f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 1.37f, 0.28f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(10f, 10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(-32.5f, 10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(4.37f, 10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(42.5f, 10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(10f, 10f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .build();

    public static final Animation FLOATING = Animation.Builder.create(0f).looping()
        .build();

    public static final Animation GLIDING = Animation.Builder.create(0f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(35f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.35f, 0.9f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(50f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0.15f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-82.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-1.53f, 2.31f, -13.2f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5.2f, -17.15f, 15.58f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-1.53f, -2.31f, 13.2f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5.2f, 17.15f, -15.58f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(92.99f, -5.05f, -4.59f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(92.99f, 5.05f, 4.59f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation FLAPPING = Animation.Builder.create(0.25f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.06f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createTranslationalVector(0f, 0.65f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0.06f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(35f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createRotationalVector(40f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(35f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.35f, 0.9f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(50f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0.15f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-82.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(1.2f, -12.78f, -47.55f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.020834332f, AnimationHelper.createRotationalVector(5.76f, -7.27f, -36.54f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0625f, AnimationHelper.createRotationalVector(10.98f, 8.63f, 4.57f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(18.52f, 29.85f, 59.94f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1875f, AnimationHelper.createRotationalVector(-3.98f, 10.52f, 11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.22916766f, AnimationHelper.createRotationalVector(-4.56f, -11.14f, -27.82f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(1.2f, -12.78f, -47.55f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(2.78f, -17.69f, -10.64f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.020834332f, AnimationHelper.createRotationalVector(2.54f, -11.68f, -14.38f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0625f, AnimationHelper.createRotationalVector(2.95f, -5.53f, -5.82f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createRotationalVector(1.27f, -5.68f, -3.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.14583434f, AnimationHelper.createRotationalVector(-6.83f, -11.1f, 32.87f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-11.11f, -19.08f, 52.48f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-6.61f, -27.05f, 42.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(2.78f, -17.69f, -10.64f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(-0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(1.2f, 12.78f, 47.55f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.020834332f, AnimationHelper.createRotationalVector(5.76f, 7.27f, 36.54f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0625f, AnimationHelper.createRotationalVector(10.98f, -8.63f, -4.57f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(18.52f, -29.85f, -59.94f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1875f, AnimationHelper.createRotationalVector(-3.98f, -10.52f, -11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.22916766f, AnimationHelper.createRotationalVector(-4.56f, 11.14f, 27.82f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(1.2f, 12.78f, 47.55f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(2.78f, 17.69f, 10.64f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.020834332f, AnimationHelper.createRotationalVector(2.54f, 11.68f, 14.38f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0625f, AnimationHelper.createRotationalVector(5.42f, 5.2f, 5.8f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createRotationalVector(1.27f, 5.68f, 3.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.14583434f, AnimationHelper.createRotationalVector(-6.98f, 11.1f, -32.87f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-11.11f, 19.08f, -52.48f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-6.61f, 27.05f, -42.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(2.78f, 17.69f, 10.64f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(92.99f, -5.05f, -4.59f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(92.99f, 5.05f, 4.59f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation SITTING = Animation.Builder.create(0f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -1.7f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.25f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(35f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.25f, -0.35f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-25f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.3f, 0f, 0.4f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.84f, -9.05f, -15.74f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.3f, -0.2f, 0.4f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.69f, 6.79f, 11.82f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-35f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-53.13f, -16.6f, -7.53f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-51.7f, 15.72f, 12.65f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();
}