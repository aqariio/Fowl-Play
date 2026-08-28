package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.shorebird.GullEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class GullRenderer extends FlyingBirdRenderer<GullEntity> {
    protected GullRenderer(EntityRendererProvider.Context context, Supplier<EntityType<GullEntity>> entityType) {
        super(context, entityType);
    }
}
