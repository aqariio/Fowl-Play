package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.core.neoforge.FowlPlayDataAttachments;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.Chicken;

public class DataAttachmentHelperImpl {
    public static Holder<ChickenVariant> getChickenVariant(Chicken entity) {
        return entity.getData(FowlPlayDataAttachments.CHICKEN_VARIANT);
    }

    public static void setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        entity.setData(FowlPlayDataAttachments.CHICKEN_VARIANT, variant);
    }
}
