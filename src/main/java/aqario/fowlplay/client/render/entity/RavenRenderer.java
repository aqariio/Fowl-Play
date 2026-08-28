package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.passerine.RavenEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class RavenRenderer extends FlyingBirdRenderer<RavenEntity> {
    protected RavenRenderer(EntityRendererProvider.Context context, Supplier<EntityType<RavenEntity>> entityType) {
        super(context, entityType);
    }
}
