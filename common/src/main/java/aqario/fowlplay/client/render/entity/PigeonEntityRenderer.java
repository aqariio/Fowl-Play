package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.feature.PigeonBundleFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.PigeonEntityModel;
import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class PigeonEntityRenderer extends MobEntityRenderer<PigeonEntity, PigeonEntityModel> {
    private static final Identifier MARTHA_TEXTURE = FowlPlay.id("textures/entity/pigeon/martha.png");

    public PigeonEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PigeonEntityModel(context.getPart(PigeonEntityModel.MODEL_LAYER)), 0.2f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.0225, -0.1475)
        ));
        this.addFeature(new PigeonBundleFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(PigeonEntity pigeon) {
        String string = Formatting.strip(pigeon.getName().getString());
        if("Martha".equals(string)) {
            return MARTHA_TEXTURE;
        }
        return pigeon.getVariant().texture();
    }
}
