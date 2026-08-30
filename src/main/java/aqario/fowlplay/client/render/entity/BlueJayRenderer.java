package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.passerine.corvid.BlueJayEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class BlueJayRenderer extends FlyingBirdRenderer<BlueJayEntity> {
    public BlueJayRenderer(EntityRendererProvider.Context context, Supplier<EntityType<BlueJayEntity>> entityType) {
        super(context, entityType);
    }
}
