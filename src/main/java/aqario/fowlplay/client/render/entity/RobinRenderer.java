package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.passerine.RobinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class RobinRenderer extends FlyingBirdRenderer<RobinEntity> {
    public RobinRenderer(EntityRendererProvider.Context context, Supplier<EntityType<RobinEntity>> entityType) {
        super(context, entityType);
    }
}
