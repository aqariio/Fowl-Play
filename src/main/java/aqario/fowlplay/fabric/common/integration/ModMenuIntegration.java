//? if fabric {
package aqario.fowlplay.fabric.common.integration;

import aqario.fowlplay.common.integration.YACLIntegration;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return YACLIntegration::createScreen;
    }
}
//?}
