package aqario.fowlplay.client.render.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class GooseAnimations {
    public static final Animation STANDING = Animation.Builder.create(0.0F).looping()
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(1.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.2942F, -0.7446F, -4.9942F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-0.2F, -0.2F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.2942F, 0.7446F, 4.9942F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.2F, -0.2F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, -10.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 10.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation WALKING = Animation.Builder.create(1.0F).looping()
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(4.375F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createRotationalVector(42.5F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5F, AnimationHelper.createRotationalVector(10.0F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75F, AnimationHelper.createRotationalVector(-32.5F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(1.0F, AnimationHelper.createRotationalVector(4.375F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, -0.07F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 1.5F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 1.37F, -0.22F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, -1.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0.0F, -0.7F, -0.23F), Transformation.Interpolations.CUBIC),
                new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.1F, -1.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(10.0F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createRotationalVector(-32.5F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5F, AnimationHelper.createRotationalVector(4.375F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75F, AnimationHelper.createRotationalVector(42.5F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(1.0F, AnimationHelper.createRotationalVector(10.0F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.37F, -0.22F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, -1.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0.0F, -0.7F, -0.23F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.1F, -1.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, -0.07F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0.0F, 1.5F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 1.37F, -0.22F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 1.0F, 0.5F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0F, -2.5F, 5.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0F, -1.5F, 1.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, -1.0F, -0.5F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0F, 2.5F, -5.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0.0F, 1.5F, -1.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 1.0F, 0.5F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.2F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.2F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7917F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.2F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .build();

    public static final Animation SWIMMING = Animation.Builder.create(0.0F).looping()
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, -0.5F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(2.7812F, 0.7965F, -4.9043F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-0.2F, -0.2F, 0.5F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(2.7812F, -0.7965F, 4.9043F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.2F, -0.2F, 0.5F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(21.5708F, -8.1239F, -9.9474F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, -1.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(21.5708F, 8.1239F, 9.9474F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, -1.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation GLIDING = Animation.Builder.create(0.0F).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(85.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.25F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(92.993F, -5.049F, -4.59F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.5F, 3.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(92.993F, 5.049F, 4.59F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.5F, 3.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-1.5344F, 2.306F, -13.2016F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-5.1959F, -17.1479F, 15.5775F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-1.5344F, -2.306F, 13.2016F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-5.1959F, 17.1479F, -15.5775F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-87.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.5F, 0.0F), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation FLAPPING = Animation.Builder.create(0.25F).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.06F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1042F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.06F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(85.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.25F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(92.993F, -5.049F, -4.59F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.5F, 3.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(92.993F, 5.049F, 4.59F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.5F, 3.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0297F, -8.5421F, -41.2564F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0208F, AnimationHelper.createRotationalVector(5.5105F, -6.0459F, -34.3511F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1042F, AnimationHelper.createRotationalVector(4.6526F, 15.4604F, 25.1413F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1458F, AnimationHelper.createRotationalVector(-3.9812F, 10.52F, 11.4746F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1875F, AnimationHelper.createRotationalVector(-5.8166F, 1.8448F, -12.5234F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0297F, -8.5421F, -41.2564F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1042F, AnimationHelper.createTranslationalVector(0.5F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(1.1754F, -17.8578F, -5.391F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0208F, AnimationHelper.createRotationalVector(0.9725F, -11.9151F, -6.7204F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0625F, AnimationHelper.createRotationalVector(-2.8031F, -5.8629F, 1.7081F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1042F, AnimationHelper.createRotationalVector(-9.2414F, -5.3456F, 29.4142F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createRotationalVector(1.1754F, -17.8578F, -5.391F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0297F, 8.5421F, 41.2564F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0208F, AnimationHelper.createRotationalVector(5.5105F, 6.0459F, 34.3511F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1042F, AnimationHelper.createRotationalVector(4.6526F, -15.4604F, -25.1413F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1458F, AnimationHelper.createRotationalVector(-3.9812F, -10.52F, -11.4746F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1875F, AnimationHelper.createRotationalVector(-5.8166F, -1.8448F, 12.5234F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0297F, 8.5421F, 41.2564F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1042F, AnimationHelper.createTranslationalVector(-0.5F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(1.1754F, 17.8578F, 5.391F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0208F, AnimationHelper.createRotationalVector(0.9725F, 11.9151F, 6.7204F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0625F, AnimationHelper.createRotationalVector(-2.8031F, 5.8629F, -1.7081F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1042F, AnimationHelper.createRotationalVector(-9.2414F, 5.3456F, -29.4142F), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25F, AnimationHelper.createRotationalVector(1.1754F, 17.8578F, 5.391F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-87.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.5F, 0.0F), Transformation.Interpolations.CUBIC)
            )
        )
        .build();
}