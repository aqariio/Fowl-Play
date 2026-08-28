package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BundleItemLayer;
import aqario.fowlplay.common.entity.bird.dove.PigeonEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class PigeonRenderer extends BirdRenderer<PigeonEntity> {
    public PigeonRenderer(EntityRendererProvider.Context context, Supplier<EntityType<PigeonEntity>> entityType) {
        super(context, entityType);
        this.addRenderLayer(new BundleItemLayer<>(this));
        this.setVariant(pigeon -> {
            String string = ChatFormatting.stripFormatting(pigeon.getName().getString());
            if("Martha".equals(string)) {
                return "martha";
            }
            return pigeon.getVariant().value().name();
        });
    }
}
