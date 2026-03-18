package aqario.fowlplay.core.platform;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.Chicken;

public class DataAttachmentHelper {
    @ExpectPlatform
    public static Holder<ChickenVariant> getChickenVariant(Chicken entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        throw new AssertionError();
    }
}
