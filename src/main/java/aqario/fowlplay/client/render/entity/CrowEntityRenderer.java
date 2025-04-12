package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.CrowEntityModel;
import aqario.fowlplay.common.entity.CrowEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class CrowEntityRenderer extends MobEntityRenderer<CrowEntity, CrowEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/crow/crow.png");

    public CrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CrowEntityModel(context.getPart(CrowEntityModel.MODEL_LAYER)), 0.15f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.0225, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(CrowEntity entity) {
        return TEXTURE;
    }
}
