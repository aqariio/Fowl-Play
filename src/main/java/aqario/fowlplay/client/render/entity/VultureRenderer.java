package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.raptor.VultureEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class VultureRenderer extends FlyingBirdRenderer<VultureEntity> {
    protected VultureRenderer(EntityRendererProvider.Context context, Supplier<EntityType<VultureEntity>> entityType) {
        super(context, entityType);
    }
}
