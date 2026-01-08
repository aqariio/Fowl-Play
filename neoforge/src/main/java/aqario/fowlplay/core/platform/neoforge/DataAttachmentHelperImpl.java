package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.common.network.neoforge.ChickenVariantPayload;
import aqario.fowlplay.core.neoforge.FowlPlayDataAttachments;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.Chicken;
import net.neoforged.neoforge.network.PacketDistributor;

public class DataAttachmentHelperImpl {
    public static Holder<ChickenVariant> getChickenVariant(Chicken entity) {
        return entity.getData(FowlPlayDataAttachments.CHICKEN_VARIANT);
    }

    public static void setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        entity.setData(FowlPlayDataAttachments.CHICKEN_VARIANT, variant);
    }

    public static void sendChickenVariantUpdate(Chicken entity) {
        ChickenVariantPayload payload = new ChickenVariantPayload(
            entity.getId(),
            getChickenVariant(entity)
        );
        PacketDistributor.sendToPlayersTrackingEntity(entity, payload);
    }
}
