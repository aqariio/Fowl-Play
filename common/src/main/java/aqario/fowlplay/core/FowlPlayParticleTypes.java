package aqario.fowlplay.core;

import net.blay09.mods.balm.core.particles.BalmParticleTypeRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;

public final class FowlPlayParticleTypes {
    private static BalmParticleTypeRegistrar REGISTRAR;

    public static final Holder<SimpleParticleType> SMALL_BUBBLE = register("small_bubble", false);

    private static Holder<SimpleParticleType> register(String name, boolean alwaysShow) {
        return REGISTRAR.register(name, alwaysShow)
            .asHolder();
    }

    public static void init(BalmParticleTypeRegistrar registrar) {
        REGISTRAR = registrar;
    }
}
