package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.DuckEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.DuckEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class DuckEntityRenderer extends MobEntityRenderer<DuckEntity, DuckEntityModel> {
    public DuckEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckEntityModel(context.getPart(DuckEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0, -0.05375, -0.1475);
            }
        });
    }

    @Override
    public Identifier getTexture(DuckEntity duck) {
        String string = Formatting.strip(duck.getName().getString());
        if ("Quackers".equals(string)) {
            return Identifier.of(FowlPlay.ID, "textures/entity/duck/muscovy_duck.png");
        }
        return Identifier.of(FowlPlay.ID, "textures/entity/duck/" + duck.getVariant().getId() + "_duck.png");
    }
}
