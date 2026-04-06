package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.Register;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public final class FowlPlayParticleTypes {
    public static final Supplier<SimpleParticleType> SMALL_BUBBLE = register("small_bubble", false);

    private static Supplier<SimpleParticleType> register(String name, boolean alwaysShow) {
        return Register.particleType(name, () -> new SimpleParticleType(alwaysShow));
    }

    public static void init() {
    }
}
