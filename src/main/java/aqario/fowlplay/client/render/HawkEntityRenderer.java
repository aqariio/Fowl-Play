package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.HawkEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.entity.HawkEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class HawkEntityRenderer extends MobEntityRenderer<HawkEntity, HawkEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/hawk/red_tailed_hawk.png");

    public HawkEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HawkEntityModel(context.getPart(HawkEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(HawkEntity hawk) {
        return TEXTURE;
    }
}
