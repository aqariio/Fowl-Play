package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.model.BirdModel;
import aqario.fowlplay.common.entity.bird.fowl.waterfowl.DuckEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class DuckRenderer extends FlyingBirdRenderer<DuckEntity> {
    public DuckRenderer(EntityRendererProvider.Context context, Supplier<EntityType<DuckEntity>> entityType) {
        super(context, entityType, modelBuilder -> modelBuilder
            .customNames("Quackers")
            .variantAffixWhen((bird, assetType) ->
                assetType == BirdModel.AssetType.TEXTURE && !bird.isDomestic()
            )
        );
    }
}
