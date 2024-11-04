package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.PartAnimation;
import org.joml.Vector3f;

public class Interpolations {
    public static final PartAnimation.Interpolation LINEAR = PartAnimation.Interpolations.LINEAR;
    public static final PartAnimation.Interpolation SPLINE = PartAnimation.Interpolations.SPLINE;
    public static final PartAnimation.Interpolation STEP = (vector, delta, keyframes, currentFrame, targetFrame, strength) -> {
        Vector3f startFrame = keyframes[currentFrame].transformation();
        return startFrame.lerp(startFrame, delta, vector).mul(strength);
    };
}
