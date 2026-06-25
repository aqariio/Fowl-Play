//? if fabric {
package aqario.fowlplay.fabric.core.platform;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.core.FPBuiltInRegistries;
import aqario.fowlplay.fabric.core.FPDataAttachments;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.Chicken;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class DataAttachmentHelperImpl {
    public static Holder<ChickenVariant> getChickenVariant(Chicken entity) {
        return Optional.ofNullable(entity.getAttached(FPDataAttachments.CHICKEN_VARIANT))
            .orElse(FPBuiltInRegistries.CHICKEN_VARIANT.getHolderOrThrow(ChickenVariant.WHITE));
    }

    public static void setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        entity.setAttached(FPDataAttachments.CHICKEN_VARIANT, variant);
    }
}
//?}