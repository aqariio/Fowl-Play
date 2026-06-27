//? if fabric {
package aqario.fowlplay.fabric.common.network;

import aqario.fowlplay.common.network.NetworkManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkManagerImpl {
    public static <T extends CustomPacketPayload> void registerClientReceiver(CustomPacketPayload.Type<T> type, StreamCodec<FriendlyByteBuf, T> codec, NetworkManager.PayloadHandler<T> handler) {
        PayloadTypeRegistry.playS2C().register(type, codec);

        ClientPlayNetworking.registerGlobalReceiver(type, (payload, context) -> handler.receive(payload, new NetworkManager.Context() {
            @Override
            public Minecraft client() {
                return context.client();
            }

            @Override
            public LocalPlayer player() {
                return context.player();
            }
        }));
    }
}
//?}