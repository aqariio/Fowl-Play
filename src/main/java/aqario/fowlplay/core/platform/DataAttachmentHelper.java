//~ expect_platform

package aqario.fowlplay.core.platform;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.fabric.core.platform.DataAttachmentHelperImpl;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.Chicken;

public class DataAttachmentHelper {
    public static Holder<ChickenVariant> getChickenVariant(Chicken entity) {
        return DataAttachmentHelperImpl.getChickenVariant(entity);
    }

    public static void setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        DataAttachmentHelperImpl.setChickenVariant(entity, variant);
    }
}
