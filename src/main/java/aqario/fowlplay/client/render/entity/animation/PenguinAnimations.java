package aqario.fowlplay.client.render.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class PenguinAnimations {
    public static final AnimationDefinition STANDING = AnimationDefinition.Builder.withLength(0f).looping()
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, -0.75f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-1.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -2.5f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 2.5f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();

    public static final AnimationDefinition WALKING = AnimationDefinition.Builder.withLength(1f).looping()
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(2.72f, -5f, -2.51f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0.31f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(2.72f, 5f, 2.51f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0.31f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(-0.25f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(-0.22f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.posVec(0.25f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0.23f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 3.12f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, -3.12f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, -5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.34f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 1.75f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.75f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.25f, -2.75f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, -0.34f, -0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(3.13f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(3.13f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.75f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -0.25f, -2.75f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.34f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 1.75f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.75f, -0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(3.13f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .build();

    public static final AnimationDefinition SLIDING = AnimationDefinition.Builder.withLength(0f)
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -8f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, -62.5f, -37.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 62.5f, 37.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0.5f, 0f, -0.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-15f, -15f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(-0.5f, 0f, -0.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-15f, 15f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, -0.15f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .build();

    public static final AnimationDefinition SLIDING_TRANSITION = AnimationDefinition.Builder.withLength(0.75f)
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -3.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -8f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(1.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, -0.75f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-1.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-75f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0.5f, -0.15f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-30f, -62.5f, -37.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-30f, 62.5f, 37.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0.13f, 1.15f, -1.87f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0.19f, 1.58f, -0.86f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.posVec(0.4f, 0.38f, 0.02f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0.5f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -2.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-23.75f, -5.62f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-15f, -15f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(-0.06f, 0.88f, 0.56f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(-0.12f, -0.25f, 1.13f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(-0.25f, 0.03f, -0.61f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.posVec(-0.38f, 0.03f, 0.34f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(-0.5f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 2.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-18.75f, 5.63f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-42.5f, 8.75f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-15f, 15f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .build();

    public static final AnimationDefinition STANDING_TRANSITION = AnimationDefinition.Builder.withLength(1f)
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -8f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -7.5f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(84.97f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1.75f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-6.75f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(1.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.5f, -0.75f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-75f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-75f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-15.72f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-1.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, -0.15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.5f, -0.15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, -62.5f, -37.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 62.5f, 37.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0.5f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0.25f, 4f, 0.25f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0.25f, 4f, 0.25f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.posVec(0.2f, 4.61f, 0.21f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0.13f, 3.75f, -1.62f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.875f, KeyframeAnimations.posVec(0.06f, 1.32f, -0.92f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-15f, -15f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-50f, -8.75f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-50f, -8.75f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.degreeVec(-61.09f, -7.38f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-37.5f, -5.62f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, -2.5f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(-0.5f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(-0.5f, 0f, -0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(-0.25f, 3.75f, 0.75f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.posVec(-0.1f, 2.13f, -0.09f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-15f, 15f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-15f, 15f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-65f, 8.75f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-28.68f, 4.99f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 2.5f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .build();

    public static final AnimationDefinition SWIMMING = AnimationDefinition.Builder.withLength(0.5f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -8f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.25f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "tail",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.25f, -1.25f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "neck",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(69.15f, -57.84f, -142.13f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-21.62f, -46.83f, -36.59f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(69.15f, -57.84f, -142.13f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(69.15f, 57.84f, 142.13f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-21.62f, 46.83f, 36.59f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(69.15f, 57.84f, 142.13f), AnimationChannel.Interpolations.CATMULLROM)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.75f, -0.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(6.14f, -15.59f, 5.15f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.75f, -0.5f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(6.14f, 15.59f, -5.15f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.75f, -1.25f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "head",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-60f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        ).build();

    public static final AnimationDefinition DANCING = AnimationDefinition.Builder.withLength(7.708343f).looping()
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.125f, KeyframeAnimations.posVec(-0.75f, 0f, -1.25f), Interpolations.STEP),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0.75f, 0f, -1.25f), Interpolations.STEP),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, KeyframeAnimations.posVec(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(3.375f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, KeyframeAnimations.posVec(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(3.7083435f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.875f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, KeyframeAnimations.posVec(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(4.625f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, KeyframeAnimations.posVec(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(4.958343f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, KeyframeAnimations.posVec(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(5.875f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, KeyframeAnimations.posVec(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(6.208343f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, KeyframeAnimations.posVec(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(6.916767f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, KeyframeAnimations.posVec(0f, -3f, 0f), Interpolations.STEP),
                new Keyframe(7.25f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "root",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, -25f, 0f), Interpolations.STEP),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 25f, 0f), Interpolations.STEP),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(0f, 45f, 0f), Interpolations.STEP),
                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(0f, 90f, 0f), Interpolations.STEP),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 135f, 0f), Interpolations.STEP),
                new Keyframe(0.8343334f, KeyframeAnimations.degreeVec(0f, 180f, 0f), Interpolations.STEP),
                new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(0f, 225f, 0f), Interpolations.STEP),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 180f, 0f), Interpolations.STEP),
                new Keyframe(1.0834333f, KeyframeAnimations.degreeVec(0f, 135f, 0f), Interpolations.STEP),
                new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(0f, 180f, 0f), Interpolations.STEP),
                new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(0f, 270f, 0f), Interpolations.STEP),
                new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(0f, 315f, 0f), Interpolations.STEP),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.291677f, KeyframeAnimations.degreeVec(0f, -45f, 0f), Interpolations.STEP),
                new Keyframe(6.676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.343333f, KeyframeAnimations.degreeVec(0f, 45f, 0f), Interpolations.STEP),
                new Keyframe(7.708343f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.291677f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(6.416767f, KeyframeAnimations.posVec(0f, 0f, 3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(6.5f, KeyframeAnimations.posVec(0f, 0f, 3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(6.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(7.343333f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(7.458343f, KeyframeAnimations.posVec(0f, 0f, 3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(7.541677f, KeyframeAnimations.posVec(0f, 0f, 3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(7.676667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "body",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, -10f, -5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.0416765f, KeyframeAnimations.degreeVec(0f, 10f, 5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.4583435f, KeyframeAnimations.degreeVec(0f, -10f, -5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.6766665f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.875f, KeyframeAnimations.degreeVec(0f, 10f, 5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.0834335f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(4f, KeyframeAnimations.degreeVec(0f, 0f, -10f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(4.343333f, KeyframeAnimations.degreeVec(0f, 0f, -10f), Interpolations.STEP),
                new Keyframe(4.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(5.25f, KeyframeAnimations.degreeVec(0f, 0f, 10f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(5.583433f, KeyframeAnimations.degreeVec(0f, 0f, 10f), Interpolations.STEP),
                new Keyframe(5.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.291677f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(6.416767f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(6.5f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(6.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(7.343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(7.458343f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(7.541677f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(7.676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5834333f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(1.625f, KeyframeAnimations.posVec(2.25f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.8343333f, KeyframeAnimations.posVec(1f, 0f, -4f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.25f, KeyframeAnimations.posVec(3.5f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.6766665f, KeyframeAnimations.posVec(1f, 0f, -4f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.0834335f, KeyframeAnimations.posVec(3.5f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.125f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.875f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.375f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.125f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.625f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "left_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -15f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(-67.33f, 51f, -9.12f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(-74.66f, 37.01f, -18.24f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(-60f, 65f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.6766665f, KeyframeAnimations.degreeVec(-74.66f, 37.01f, -18.24f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.0834335f, KeyframeAnimations.degreeVec(-60f, 65f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.125f, KeyframeAnimations.degreeVec(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(3.875f, KeyframeAnimations.degreeVec(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(3.9583435f, KeyframeAnimations.degreeVec(0f, -60f, -95f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(4f, KeyframeAnimations.degreeVec(-30f, -85f, -95f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(4.125f, KeyframeAnimations.degreeVec(-70f, -85f, -95f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(4.25f, KeyframeAnimations.degreeVec(-30f, -85f, -95f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(4.375f, KeyframeAnimations.degreeVec(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(5.125f, KeyframeAnimations.degreeVec(0f, 0f, -15f), Interpolations.STEP),
                new Keyframe(5.625f, KeyframeAnimations.degreeVec(0f, 0f, -15f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5834333f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(1.625f, KeyframeAnimations.posVec(-2.25f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.8343333f, KeyframeAnimations.posVec(-1f, 0f, -4f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.25f, KeyframeAnimations.posVec(-3.5f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.6766665f, KeyframeAnimations.posVec(-1f, 0f, -4f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.0834335f, KeyframeAnimations.posVec(-3.5f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.125f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.875f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.375f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.125f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.625f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "right_wing",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 15f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(-67.33f, -51f, 9.12f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(-74.66f, -37.01f, 18.24f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(-60f, -65f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2.6766665f, KeyframeAnimations.degreeVec(-74.66f, -37.01f, 18.24f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.0834335f, KeyframeAnimations.degreeVec(-60f, -65f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(3.125f, KeyframeAnimations.degreeVec(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(3.875f, KeyframeAnimations.degreeVec(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(4.375f, KeyframeAnimations.degreeVec(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(5.125f, KeyframeAnimations.degreeVec(0f, 0f, 15f), Interpolations.STEP),
                new Keyframe(5.208343f, KeyframeAnimations.degreeVec(0f, 60f, 95f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(5.25f, KeyframeAnimations.degreeVec(-30f, 85f, 95f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(5.375f, KeyframeAnimations.degreeVec(-70f, 85f, 95f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(5.5f, KeyframeAnimations.degreeVec(-30f, 85f, 95f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(5.625f, KeyframeAnimations.degreeVec(0f, 0f, 15f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(3.375f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(3.7083435f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(4.625f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(4.958343f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(5.875f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(6.208343f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(6.916767f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(7.25f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "left_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, KeyframeAnimations.degreeVec(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(3.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, KeyframeAnimations.degreeVec(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(3.7083435f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, KeyframeAnimations.degreeVec(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(4.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, KeyframeAnimations.degreeVec(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(4.958343f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, KeyframeAnimations.degreeVec(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(5.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, KeyframeAnimations.degreeVec(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(6.208343f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, KeyframeAnimations.degreeVec(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(6.916767f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, KeyframeAnimations.degreeVec(-62.77f, -22.52f, -11.15f), Interpolations.STEP),
                new Keyframe(7.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(3.375f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(3.7083435f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(4.625f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(4.958343f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(5.875f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(6.208343f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(6.916767f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, KeyframeAnimations.posVec(0f, 2f, -2f), Interpolations.STEP),
                new Keyframe(7.25f, KeyframeAnimations.posVec(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .addAnimation(
            "right_leg",
            new AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.2083435f, KeyframeAnimations.degreeVec(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(3.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(3.5416765f, KeyframeAnimations.degreeVec(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(3.7083435f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.458343f, KeyframeAnimations.degreeVec(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(4.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(4.791677f, KeyframeAnimations.degreeVec(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(4.958343f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(5.708343f, KeyframeAnimations.degreeVec(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(5.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.041677f, KeyframeAnimations.degreeVec(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(6.208343f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(6.75f, KeyframeAnimations.degreeVec(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(6.916767f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP),
                new Keyframe(7.083433f, KeyframeAnimations.degreeVec(-62.77f, 22.52f, 11.15f), Interpolations.STEP),
                new Keyframe(7.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), Interpolations.STEP)
            )
        )
        .build();
}