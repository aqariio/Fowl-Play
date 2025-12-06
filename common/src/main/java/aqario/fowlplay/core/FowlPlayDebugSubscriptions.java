package aqario.fowlplay.core;

import aqario.fowlplay.common.util.DebugBirdData;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.debug.DebugSubscription;

public class FowlPlayDebugSubscriptions {
    public static final DebugSubscription<DebugBirdData> BIRDS = registerWithValue("birds", DebugBirdData.STREAM_CODEC);

    private static <T> DebugSubscription<T> registerWithValue(String id, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
        return Registry.register(BuiltInRegistries.DEBUG_SUBSCRIPTION, FowlPlay.id(id), new DebugSubscription<>(codec));
    }

    public static void init() {
    }
}
