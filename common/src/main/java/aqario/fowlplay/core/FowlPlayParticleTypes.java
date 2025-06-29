package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.particle.DefaultParticleType;

import java.util.function.Supplier;

public class FowlPlayParticleTypes {
    public static final Supplier<DefaultParticleType> SMALL_BUBBLE = register("small_bubble", false);

    private static Supplier<DefaultParticleType> register(String name, boolean alwaysShow) {
        return PlatformHelper.registerParticleType(name, () -> new DefaultParticleType(alwaysShow));
    }

    public static void init() {
    }
}
