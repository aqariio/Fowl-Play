//? if forge {
/*package aqario.fowlplay.forge.core.platform;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.forge.core.FPDataAttachments;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.Chicken;

public class DataAttachmentHelperImpl {
    public static Holder<ChickenVariant> getChickenVariant(Chicken entity) {
        return entity.getData(FPDataAttachments.CHICKEN_VARIANT);
    }

    public static void setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        entity.setData(FPDataAttachments.CHICKEN_VARIANT, variant);
    }
}
*///?}