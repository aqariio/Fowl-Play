package aqario.fowlplay.core.platform;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.Chicken;

public class DataAttachmentHelper {
    public static Holder<ChickenVariant> getChickenVariant(Chicken entity) {
        return FowlPlay.PLATFORM.dataAttachmentHelper$getChickenVariant(entity);
    }

    public static void setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        FowlPlay.PLATFORM.dataAttachmentHelper$setChickenVariant(entity, variant);
    }
}
