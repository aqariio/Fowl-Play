package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.CorvidEyesLayer;
import aqario.fowlplay.common.entity.bird.passerine.corvid.CrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class CrowRenderer extends FlyingBirdRenderer<CrowEntity> {
    public CrowRenderer(EntityRendererProvider.Context context, Supplier<EntityType<CrowEntity>> entityType) {
        super(context, entityType);
        this.addRenderLayer(new CorvidEyesLayer<>(this));
    }
}
