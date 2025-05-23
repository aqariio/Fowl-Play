package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.DuckEntityModel;
import aqario.fowlplay.common.entity.DuckEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class DuckEntityRenderer extends MobEntityRenderer<DuckEntity, DuckEntityModel> {
    private static final Identifier QUACKERS_TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/duck/quackers.png");

    public DuckEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckEntityModel(context.getPart(DuckEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(DuckEntity duck) {
        String string = Formatting.strip(duck.getName().getString());
        if ("Quackers".equals(string)) {
            return QUACKERS_TEXTURE;
        }
        return duck.getVariant().value().texture();
    }
}
