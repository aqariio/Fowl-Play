package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.passerine.SparrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class SparrowRenderer extends FlyingBirdRenderer<SparrowEntity> {
    public SparrowRenderer(EntityRendererProvider.Context context, Supplier<EntityType<SparrowEntity>> entityType) {
        super(context, entityType);
    }
}
