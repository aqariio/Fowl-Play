package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BeakItemLayer;
import aqario.fowlplay.client.render.entity.model.BirdModel;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.function.Function;
import java.util.function.Supplier;

public class BirdRenderer<T extends BirdEntity & GeoEntity> extends GeoEntityRenderer<T> {
    public BirdRenderer(EntityRendererProvider.Context context, Supplier<EntityType<T>> entityType) {
        this(context, new BirdModel<>(entityType));
    }

    protected BirdRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
        this.addRenderLayer(new BeakItemLayer<>(this));
    }

    @Override
    public BirdModel<T> getGeoModel() {
        return (BirdModel<T>) super.getGeoModel();
    }

    protected void setVariant(Function<T, String> variant) {
        this.getGeoModel().setVariant(variant.apply(this.getAnimatable()));
    }
}
