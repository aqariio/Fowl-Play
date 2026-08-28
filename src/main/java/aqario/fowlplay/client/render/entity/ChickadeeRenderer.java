package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.passerine.ChickadeeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class ChickadeeRenderer extends BirdRenderer<ChickadeeEntity> {
    public ChickadeeRenderer(EntityRendererProvider.Context context, Supplier<EntityType<ChickadeeEntity>> entityType) {
        super(context, entityType);
    }
}
