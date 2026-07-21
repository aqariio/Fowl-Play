//~ expect_platform

package aqario.fowlplay.common.network;

import aqario.fowlplay.fabric.common.network.NetworkManagerImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkManager {
    public static <T extends CustomPacketPayload> void registerClientReceiver(CustomPacketPayload.Type<T> type, StreamCodec<FriendlyByteBuf, T> codec, PayloadHandler<T> handler) {
        NetworkManagerImpl.registerClientReceiver(type, codec, handler);
    }

    public interface PayloadHandler<T extends CustomPacketPayload> {
        void receive(T payload, Context context);
    }

    public interface Context {
        Minecraft client();

        LocalPlayer player();
    }
}
