package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BundleItemLayer;
import aqario.fowlplay.common.entity.bird.dove.PigeonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class PigeonRenderer extends FlyingBirdRenderer<PigeonEntity> {
    public PigeonRenderer(EntityRendererProvider.Context context, Supplier<EntityType<PigeonEntity>> entityType) {
        super(context, entityType, modelBuilder -> modelBuilder
            .customNames("Martha")
        );
        this.addRenderLayer(new BundleItemLayer<>(this));
    }
}
