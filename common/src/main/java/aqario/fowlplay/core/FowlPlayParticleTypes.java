package aqario.fowlplay.core;

import aqario.fowlplay.common.registry.CommonRegister;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public final class FowlPlayParticleTypes {
    public static final CommonRegister<ParticleType<?>> REGISTRAR = CommonRegister.create(
        BuiltInRegistries.PARTICLE_TYPE,
        FowlPlay.ID
    );

    public static final Supplier<SimpleParticleType> SMALL_BUBBLE = register("small_bubble", false);

    private static Supplier<SimpleParticleType> register(String name, boolean alwaysShow) {
        return REGISTRAR.register(name, () -> new SimpleParticleType(alwaysShow));
    }
}
