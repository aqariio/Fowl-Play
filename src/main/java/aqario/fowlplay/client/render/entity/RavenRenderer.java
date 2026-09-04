package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.CorvidEyesLayer;
import aqario.fowlplay.common.entity.bird.passerine.corvid.RavenEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class RavenRenderer extends FlyingBirdRenderer<RavenEntity> {
    public RavenRenderer(EntityRendererProvider.Context context, Supplier<EntityType<RavenEntity>> entityType) {
        super(context, entityType);
        this.addRenderLayer(new CorvidEyesLayer<>(this));
    }
}
