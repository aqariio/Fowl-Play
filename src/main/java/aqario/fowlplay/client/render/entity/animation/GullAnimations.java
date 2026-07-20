package aqario.fowlplay.client.render.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class GullAnimations {
    public static final AnimationDefinition STANDING = AnimationDefinition.Builder.withLength(1.0f).looping()
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
                new Keyframe(0f, KeyframeAnimations.degreeVec(-2.22f, -4.2f, -4.71f), AnimationChannel.Interpolations.LINEAR)
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
                new Keyframe(0f, KeyframeAnimations.degreeVec(-2.22f, 4.2f, 4.71f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
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

    public static final AnimationDefinition WALKING = AnimationDefinition.Builder.withLength(1.0f).looping()
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

    public static final AnimationDefinition SWIMMING = AnimationDefinition.Builder.withLength(1f).looping()
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.LINEAR)
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
                new Keyframe(0f, KeyframeAnimations.posVec(-0.2f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(2.78f, -4.2f, -4.71f), AnimationChannel.Interpolations.LINEAR)
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
                new Keyframe(0f, KeyframeAnimations.degreeVec(2.78f, 4.2f, 4.71f), AnimationChannel.Interpolations.LINEAR)
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

    public static final AnimationDefinition GLIDING = AnimationDefinition.Builder.withLength(0f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.75f, 2.25f), AnimationChannel.Interpolations.LINEAR)
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
                new Keyframe(0f, KeyframeAnimations.degreeVec(-1.68f, 6.95f, -15.05f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-7.96f, -24.51f, 16.31f), AnimationChannel.Interpolations.LINEAR)
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
                new Keyframe(0f, KeyframeAnimations.degreeVec(-1.68f, -6.95f, 15.05f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-7.96f, 24.51f, -16.31f), AnimationChannel.Interpolations.LINEAR)
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
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-70f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();

    public static final AnimationDefinition FLAPPING = AnimationDefinition.Builder.withLength(1f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.posVec(0.5f, 0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0.03f, -8.54f, -41.26f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(5.51f, -6.05f, -34.35f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(4.65f, 15.46f, 25.14f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-3.98f, 10.52f, 11.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-5.82f, 1.84f, -12.52f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0.03f, -8.54f, -41.26f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1.18f, -17.86f, -5.39f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0.97f, -11.92f, -6.72f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.8f, -5.86f, 1.71f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-3.73f, -5.68f, -3.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-10.5f, -1.88f, 49.15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-8.21f, -6.59f, 45.13f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(1.18f, -17.86f, -5.39f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.posVec(-0.5f, 0.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.25f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_open",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0.03f, 8.54f, 41.26f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(5.51f, 6.05f, 34.35f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(4.65f, -15.46f, -25.14f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-3.98f, -10.52f, -11.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-5.82f, -1.84f, 12.52f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0.03f, 8.54f, 41.26f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing_outer",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1.18f, 17.86f, 5.39f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0.97f, 11.92f, 6.72f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.8f, 5.86f, -1.71f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-3.73f, 5.68f, 3.47f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-10.5f, 1.88f, -49.15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-8.21f, 6.59f, -45.13f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(1.18f, 17.86f, 5.39f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .build();

    public static final AnimationDefinition SLEEPING_WATER = AnimationDefinition.Builder.withLength(1.0F).looping()
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-121.0F, 0.0F, 180.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.25F, -1.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.7891F, -3.2224F, -4.9225F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.2F, -0.2F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.7891F, 3.2224F, 4.9225F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.2F, -0.2F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(21.5708F, -8.1239F, -9.9474F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(21.5708F, 8.1239F, 9.9474F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.75F, -1.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();

    public static final AnimationDefinition SLEEPING_LAND = AnimationDefinition.Builder.withLength(1.0F).looping()
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-121.0F, 0.0F, 180.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.25F, -1.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.7835F, -3.2077F, -4.8363F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.2F, -0.2F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.7835F, 3.2077F, 4.8363F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.2F, -0.2F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-63.5156F, -5.7272F, -9.231F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, 1.0F, 0.25F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-63.5156F, 5.7272F, 9.231F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, 1.0F, 0.25F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.75F, -1.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();
}