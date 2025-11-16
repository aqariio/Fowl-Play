package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class FowlPlayParticleTypes {
    public static final Supplier<SimpleParticleType> SMALL_BUBBLE = register("small_bubble", false);

    private static Supplier<SimpleParticleType> register(String name, boolean alwaysShow) {
        return PlatformHelper.registerParticleType(name, () -> new SimpleParticleType(alwaysShow));
    }

    public static void init() {
    }
}
