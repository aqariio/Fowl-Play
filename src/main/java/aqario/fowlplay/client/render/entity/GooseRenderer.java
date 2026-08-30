package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.fowl.waterfowl.GooseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class GooseRenderer extends FlyingBirdRenderer<GooseEntity> {
    public GooseRenderer(EntityRendererProvider.Context context, Supplier<EntityType<GooseEntity>> entityType) {
        super(context, entityType);
    }
}
