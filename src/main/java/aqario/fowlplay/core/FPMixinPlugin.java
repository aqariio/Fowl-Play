package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FPConfig;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class FPMixinPlugin implements IMixinConfigPlugin {
    private boolean chickenMixin = true;
    private boolean parrotMixin = true;

    @Override
    public void onLoad(String mixinPackage) {
        FPConfig.load();
        this.chickenMixin = FPConfig.get().replaceChicken;
        this.parrotMixin = FPConfig.get().replaceParrot;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if(targetClassName.toLowerCase().contains("chicken") && !this.chickenMixin) {
            return false;
        }
        if(targetClassName.toLowerCase().contains("parrot") && !this.parrotMixin) {
            return false;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
