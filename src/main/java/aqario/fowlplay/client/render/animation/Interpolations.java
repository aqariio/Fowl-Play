package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.entity.animation.Transformation;
import org.joml.Vector3f;

public class Interpolations {
    public static final Transformation.Interpolation LINEAR = Transformation.Interpolations.LINEAR;
    public static final Transformation.Interpolation SPLINE = Transformation.Interpolations.CUBIC;
    public static final Transformation.Interpolation STEP = (vector, delta, keyframes, currentFrame, targetFrame, strength) -> {
        Vector3f startFrame = keyframes[currentFrame].target();
        return startFrame.lerp(startFrame, delta, vector).mul(strength);
    };
}
