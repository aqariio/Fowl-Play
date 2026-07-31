//? if forge {
/*package aqario.fowlplay.forge.common.network;

import aqario.fowlplay.common.network.NetworkManager;
import aqario.fowlplay.forge.core.FowlPlayForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class NetworkManagerImpl {
    public static <T extends CustomPacketPayload> void registerClientReceiver(CustomPacketPayload.Type<T> type, StreamCodec<FriendlyByteBuf, T> codec, NetworkManager.PayloadHandler<T> handler) {
        FowlPlayForge.eventBus().<RegisterPayloadHandlersEvent>addListener(event -> event.registrar("1").playToClient(
            type,
            codec,
            (payload, context) -> handler.receive(payload, new NetworkManager.Context() {
                @Override
                public Minecraft client() {
                    return null;
                }

                @Override
                public LocalPlayer player() {
                    return (LocalPlayer) context.player();
                }
            })
        ));
    }
}
*///?}