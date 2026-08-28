package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.passerine.CardinalEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class CardinalRenderer extends BirdRenderer<CardinalEntity> {
    public CardinalRenderer(EntityRendererProvider.Context context, Supplier<EntityType<CardinalEntity>> entityType) {
        super(context, entityType);
    }
}
