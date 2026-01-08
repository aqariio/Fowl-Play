package aqario.fowlplay.core.platform.fabric;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.core.FowlPlayBuiltInRegistries;
import aqario.fowlplay.core.fabric.FowlPlayDataAttachments;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.Chicken;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class DataAttachmentHelperImpl {
    public static Holder<ChickenVariant> getChickenVariant(Chicken entity) {
        return Optional.ofNullable(entity.getAttached(FowlPlayDataAttachments.CHICKEN_VARIANT))
            .orElse(FowlPlayBuiltInRegistries.CHICKEN_VARIANT.getHolderOrThrow(ChickenVariant.WHITE));
    }

    public static void setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        entity.setAttached(FowlPlayDataAttachments.CHICKEN_VARIANT, variant);
    }

    public static void sendChickenVariantUpdate(Chicken entity) {
    }
}
