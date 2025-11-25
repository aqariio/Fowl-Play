package aqario.fowlplay.client.render.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import org.joml.Vector3f;

public class Interpolations {
    public static final AnimationChannel.Interpolation LINEAR = AnimationChannel.Interpolations.LINEAR;
    public static final AnimationChannel.Interpolation SPLINE = AnimationChannel.Interpolations.CATMULLROM;
    public static final AnimationChannel.Interpolation STEP = (vector, delta, keyframes, currentFrame, targetFrame, strength) -> {
        Vector3f startFrame = keyframes[currentFrame].target();
        return startFrame.lerp(startFrame, delta, vector).mul(strength);
    };
}
