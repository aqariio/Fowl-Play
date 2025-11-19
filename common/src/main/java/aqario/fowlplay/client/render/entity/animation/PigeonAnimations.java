package aqario.fowlplay.client.render.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class PigeonAnimations {
    public static final AnimationDefinition STANDING = AnimationDefinition.Builder.withLength(1.0F).looping()
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0.3f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(-0.2f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-7.23f, -4.53f, -2.84f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0.2f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-7.23f, 4.53f, 2.84f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-0.05f, -4.83f, -3.7f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-0.05f, 4.83f, 3.7f), AnimationChannel.Interpolations.LINEAR)
            )
        ).build();

    public static final AnimationDefinition WALKING = AnimationDefinition.Builder.withLength(1.0f).looping()
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.08343333f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7916766f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 1f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(10f, -2.5f, 5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(10f, -1.5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(10f, -1f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(10f, 2.5f, -5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(10f, 1.5f, -1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(10f, 1f, 0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.posVec(-0.25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.posVec(-0.12f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7916766f, KeyframeAnimations.posVec(0.25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9167666f, KeyframeAnimations.posVec(0.12f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, -1f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(-10f, 2.5f, -5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-10f, 1.5f, -1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-5f, 1f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(-10f, -2.5f, 5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(-10f, -1.5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-5f, -1f, -0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.08343333f, KeyframeAnimations.posVec(0f, 0.25f, 0.43f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 1.5f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1.37f, 0.28f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0.25f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, -0.7f, 0.27f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.1f, -0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(4.38f, -10f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(42.5f, -10f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(10f, -10f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-32.5f, -10f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(4.38f, -10f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1.37f, 0.28f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.25f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -0.7f, 0.27f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.1f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0.25f, 0.43f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9167666f, KeyframeAnimations.posVec(0f, 1.5f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 1.37f, 0.28f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 10f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-32.5f, 10f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(4.37f, 10f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(42.5f, 10f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(10f, 10f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .build();

    public static final AnimationDefinition SWIMMING = AnimationDefinition.Builder.withLength(0f).looping()
        .build();

    public static final AnimationDefinition GLIDING = AnimationDefinition.Builder.withLength(0f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.35f, 0.9f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(50f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0.15f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-82.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-1.53f, 2.31f, -13.2f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5.2f, -17.15f, 15.58f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-1.53f, -2.31f, 13.2f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5.2f, 17.15f, -15.58f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(92.99f, -5.05f, -4.59f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(92.99f, 5.05f, 4.59f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();

    public static final AnimationDefinition FLAPPING = AnimationDefinition.Builder.withLength(0.25f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.06f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.posVec(0f, 0.65f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.06f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.degreeVec(40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.35f, 0.9f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(50f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0.15f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-82.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0.5f, 0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1.2f, -12.78f, -47.55f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.020834332f, KeyframeAnimations.degreeVec(5.76f, -7.27f, -36.54f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.0625f, KeyframeAnimations.degreeVec(10.98f, 8.63f, 4.57f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(18.52f, 29.85f, 59.94f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.1875f, KeyframeAnimations.degreeVec(-3.98f, 10.52f, 11.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.22916766f, KeyframeAnimations.degreeVec(-4.56f, -11.14f, -27.82f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(1.2f, -12.78f, -47.55f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(2.78f, -17.69f, -10.64f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.020834332f, KeyframeAnimations.degreeVec(2.54f, -11.68f, -14.38f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.0625f, KeyframeAnimations.degreeVec(2.95f, -5.53f, -5.82f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.degreeVec(1.27f, -5.68f, -3.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.14583434f, KeyframeAnimations.degreeVec(-6.83f, -11.1f, 32.87f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-11.11f, -19.08f, 52.48f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(-6.61f, -27.05f, 42.71f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(2.78f, -17.69f, -10.64f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(-0.5f, 0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1.2f, 12.78f, 47.55f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.020834332f, KeyframeAnimations.degreeVec(5.76f, 7.27f, 36.54f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.0625f, KeyframeAnimations.degreeVec(10.98f, -8.63f, -4.57f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(18.52f, -29.85f, -59.94f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.1875f, KeyframeAnimations.degreeVec(-3.98f, -10.52f, -11.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.22916766f, KeyframeAnimations.degreeVec(-4.56f, 11.14f, 27.82f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(1.2f, 12.78f, 47.55f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(2.78f, 17.69f, 10.64f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.020834332f, KeyframeAnimations.degreeVec(2.54f, 11.68f, 14.38f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.0625f, KeyframeAnimations.degreeVec(5.42f, 5.2f, 5.8f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.degreeVec(1.27f, 5.68f, 3.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.14583434f, KeyframeAnimations.degreeVec(-6.98f, 11.1f, -32.87f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-11.11f, 19.08f, -52.48f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(-6.61f, 27.05f, -42.71f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(2.78f, 17.69f, 10.64f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(92.99f, -5.05f, -4.59f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(92.99f, 5.05f, 4.59f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();

    public static final AnimationDefinition SITTING = AnimationDefinition.Builder.withLength(0f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.7f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.25f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.25f, -0.35f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(-0.3f, 0f, 0.4f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-7.84f, -9.05f, -15.74f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0.3f, -0.2f, 0.4f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-7.69f, 6.79f, 11.82f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-35f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-53.13f, -16.6f, -7.53f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-51.7f, 15.72f, 12.65f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();
}