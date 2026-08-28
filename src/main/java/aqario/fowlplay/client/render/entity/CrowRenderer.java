package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.passerine.CrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class CrowRenderer extends FlyingBirdRenderer<CrowEntity> {
    public CrowRenderer(EntityRendererProvider.Context context, Supplier<EntityType<CrowEntity>> entityType) {
        super(context, entityType);
    }
}
