//? if fabric {
package aqario.fowlplay.fabric;

import aqario.fowlplay.client.FowlPlayClient;
import net.fabricmc.api.ClientModInitializer;

public class FowlPlayClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FowlPlayClient.init();
    }
}
//?}
