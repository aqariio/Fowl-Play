package aqario.fowlplay.client.render.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class DuckAnimations {
    public static final AnimationDefinition STANDING = AnimationDefinition.Builder.withLength(0f).looping()
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(-0.2f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(2.79f, -3.24f, -5.01f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0.2f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(2.79f, 3.24f, 5.01f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -10f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 10f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();

    public static final AnimationDefinition WALKING = AnimationDefinition.Builder.withLength(1f).looping()
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.posVec(-0.25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7916766f, KeyframeAnimations.posVec(0.25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -1f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(0f, 2.5f, -5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(0f, 1.5f, -1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 1f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(0f, -2.5f, 5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(0f, -1.5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, -1f, -0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.08343333f, KeyframeAnimations.posVec(0f, 0.25f, -0.07f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 1.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1.37f, -0.22f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0.25f, -1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, -0.7f, -0.23f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.1f, -1f), AnimationChannel.Interpolations.CATMULLROM)
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
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1.37f, -0.22f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.25f, -1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -0.7f, -0.23f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.1f, -1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0.25f, -0.07f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9167666f, KeyframeAnimations.posVec(0f, 1.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 1.37f, -0.22f), AnimationChannel.Interpolations.CATMULLROM)
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
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 1f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(0f, -2.5f, 5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(0f, -1.5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, -1f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(0f, 2.5f, -5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(0f, 1.5f, -1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 1f, 0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .build();

    public static final AnimationDefinition FLOATING = AnimationDefinition.Builder.withLength(1f).looping()
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, -0.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-21.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(-0.2f, -0.2f, 0.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-2.22f, -4.2f, -4.71f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0.2f, -0.2f, 0.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-2.22f, 4.2f, 4.71f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(21.57f, -8.12f, -9.95f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(21.57f, 8.12f, 9.95f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();

    public static final AnimationDefinition GLIDING = AnimationDefinition.Builder.withLength(1f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.25f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(65f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f), AnimationChannel.Interpolations.LINEAR)
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
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(92.99f, 5.05f, 4.59f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-1.53f, 2.31f, -13.2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.14583434f, KeyframeAnimations.degreeVec(-1.37f, 3.59f, -9.94f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(-1.55f, 2.1f, -13.72f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7708344f, KeyframeAnimations.degreeVec(-1.65f, -0.09f, -19.25f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-1.53f, 2.31f, -13.2f), AnimationChannel.Interpolations.CATMULLROM)
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
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-1.53f, -2.31f, 13.2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.22916766f, KeyframeAnimations.degreeVec(-1.65f, -0.48f, 17.81f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-1.61f, -1.4f, 15.49f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-1.61f, -1.4f, 15.49f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7291676f, KeyframeAnimations.degreeVec(-1.47f, -2.87f, 11.76f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-1.53f, -2.31f, 13.2f), AnimationChannel.Interpolations.CATMULLROM)
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
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2708343f, KeyframeAnimations.degreeVec(-8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3541677f, KeyframeAnimations.degreeVec(-8.44f, 0.2f, 0.46f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(-9.18f, 0.41f, 0.91f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7708344f, KeyframeAnimations.degreeVec(-7.99f, -0.78f, -1.84f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1.5f, -1.1f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-77.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();

    public static final AnimationDefinition FLAPPING = AnimationDefinition.Builder.withLength(0.5f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.06f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.041676664f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.06f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3541677f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.06f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.25f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(65f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f), AnimationChannel.Interpolations.LINEAR)
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
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(92.99f, 5.05f, 4.59f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.posVec(0.5f, -0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3541677f, KeyframeAnimations.posVec(0.5f, -0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0.03f, -8.54f, -41.26f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.020834332f, KeyframeAnimations.degreeVec(5.51f, -6.05f, -34.35f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.degreeVec(4.65f, 15.46f, 25.14f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.14583434f, KeyframeAnimations.degreeVec(-3.98f, 10.52f, 11.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.1875f, KeyframeAnimations.degreeVec(-5.82f, 1.84f, -12.52f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0.03f, -8.54f, -41.26f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2708343f, KeyframeAnimations.degreeVec(5.51f, -6.05f, -34.35f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3541677f, KeyframeAnimations.degreeVec(4.65f, 15.46f, 25.14f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3958343f, KeyframeAnimations.degreeVec(-3.98f, 10.52f, 11.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4375f, KeyframeAnimations.degreeVec(-5.82f, 1.84f, -12.52f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0.03f, -8.54f, -41.26f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1.18f, -17.86f, -5.39f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.020834332f, KeyframeAnimations.degreeVec(0.97f, -11.92f, -6.72f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.0625f, KeyframeAnimations.degreeVec(-2.8f, -5.86f, 1.71f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.degreeVec(-9.24f, -5.35f, 29.41f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(1.18f, -17.86f, -5.39f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2708343f, KeyframeAnimations.degreeVec(0.97f, -11.92f, -6.72f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3125f, KeyframeAnimations.degreeVec(-2.8f, -5.86f, 1.71f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3541677f, KeyframeAnimations.degreeVec(-9.24f, -5.35f, 29.41f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(1.18f, -17.86f, -5.39f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.posVec(-0.5f, -0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3541677f, KeyframeAnimations.posVec(-0.5f, -0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0.03f, 8.54f, 41.26f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.020834332f, KeyframeAnimations.degreeVec(5.51f, 6.05f, 34.35f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.degreeVec(4.65f, -15.46f, -25.14f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.14583434f, KeyframeAnimations.degreeVec(-3.98f, -10.52f, -11.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.1875f, KeyframeAnimations.degreeVec(-5.82f, -1.84f, 12.52f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0.03f, 8.54f, 41.26f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2708343f, KeyframeAnimations.degreeVec(5.51f, 6.05f, 34.35f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3541677f, KeyframeAnimations.degreeVec(4.65f, -15.46f, -25.14f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3958343f, KeyframeAnimations.degreeVec(-3.98f, -10.52f, -11.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4375f, KeyframeAnimations.degreeVec(-5.82f, -1.84f, 12.52f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0.03f, 8.54f, 41.26f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1.18f, 17.86f, 5.39f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.020834332f, KeyframeAnimations.degreeVec(0.97f, 11.92f, 6.72f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.0625f, KeyframeAnimations.degreeVec(-2.8f, 5.86f, -1.71f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.10416767f, KeyframeAnimations.degreeVec(-9.24f, 5.35f, -29.41f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(1.18f, 17.86f, 5.39f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2708343f, KeyframeAnimations.degreeVec(0.97f, 11.92f, 6.72f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3125f, KeyframeAnimations.degreeVec(-2.8f, 5.86f, -1.71f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3541677f, KeyframeAnimations.degreeVec(-9.24f, 5.35f, -29.41f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(1.18f, 17.86f, 5.39f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1.5f, -1.1f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-77.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();
}