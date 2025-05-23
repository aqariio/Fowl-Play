package aqario.fowlplay.common.config;

import aqario.fowlplay.core.FowlPlay;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.net.URI;

public class ModMenuIntegration implements ModMenuApi {
    // amazing idea from Do A Barrel Roll mod
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FowlPlay.isYACLLoaded()) {
            return YACLIntegration::createScreen;
        }
        String link = "https://modrinth.com/mod/yacl/versions";
        return parent ->
            new ConfirmLinkScreen(result -> {
                if (result) {
                    Util.getOperatingSystem().open(URI.create(link));
                }
                MinecraftClient.getInstance().setScreen(parent);
            },
                Text.translatable("config.info.require_yacl"),
                Text.literal(link),
                link,
                ScreenTexts.CANCEL,
                true
            );
    }
}
