package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.RobinEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.RobinEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class RobinEntityRenderer extends MobEntityRenderer<RobinEntity, RobinEntityModel> {
    public RobinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new RobinEntityModel(context.getPart(RobinEntityModel.MODEL_LAYER)), 0.15f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0F, -0.085F, -0.25625F);
            }
        });
    }

    @Override
    public Identifier getTexture(RobinEntity entity) {
        return Identifier.of(FowlPlay.ID, "textures/entity/robin/" + entity.getVariant().getId() + "_robin.png");
    }
}
