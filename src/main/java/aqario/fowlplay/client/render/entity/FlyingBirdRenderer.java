package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.model.BirdModel;
import aqario.fowlplay.client.render.entity.model.FlyingBirdModel;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.function.Function;
import java.util.function.Supplier;

public class FlyingBirdRenderer<T extends FlyingBirdEntity & GeoEntity> extends BirdRenderer<T> {
    protected FlyingBirdRenderer(EntityRendererProvider.Context context, Supplier<EntityType<T>> entityType) {
        super(context, new FlyingBirdModel<>(entityType));
    }

    public FlyingBirdRenderer(
        EntityRendererProvider.Context context,
        Supplier<EntityType<T>> entityType,
        Function<BirdModel<T>, BirdModel<T>> modelBuilder
    ) {
        super(context, modelBuilder.apply(new FlyingBirdModel<>(entityType)));
    }

    @Override
    public FlyingBirdModel<T> getGeoModel() {
        return (FlyingBirdModel<T>) super.getGeoModel();
    }
}
