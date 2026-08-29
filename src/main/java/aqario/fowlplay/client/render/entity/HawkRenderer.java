package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.raptor.HawkEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class HawkRenderer extends FlyingBirdRenderer<HawkEntity> {
    public HawkRenderer(EntityRendererProvider.Context context, Supplier<EntityType<HawkEntity>> entityType) {
        super(context, entityType);
    }
}
