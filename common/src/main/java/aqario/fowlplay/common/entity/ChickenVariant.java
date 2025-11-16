package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.platform.PlatformHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.function.Supplier;

public record ChickenVariant(String id) {
    public static final Codec<ChickenVariant> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(ChickenVariant::id)
        ).apply(instance, ChickenVariant::new)
    );
    public static final Supplier<ChickenVariant> WHITE = register("white");
    public static final Supplier<ChickenVariant> RED_JUNGLEFOWL = register("red_junglefowl");

    private static Supplier<ChickenVariant> register(String id) {
        return PlatformHelper.registerVariant(id, () -> new ChickenVariant(id));
    }

    public static void init() {
    }
}
