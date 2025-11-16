package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class DomesticGooseEntityModel extends GooseEntityModel {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(FowlPlay.id("domestic_goose"), "main");

    public DomesticGooseEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.5F, 0.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 23).cuboid(-1.5F, -7.0F, -1.75F, 3.0F, 9.0F, 3.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -5.0F, -2.0F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
            .uv(0, 7).cuboid(-1.0F, -3.25F, -1.75F, 2.0F, 2.0F, 1.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -6.0F, -1.75F));

        head.addChild("beak", ModelPartBuilder.create().uv(22, 0).cuboid(-1.0F, 0.0F, -2.5F, 2.0F, 1.0F, 3.0F, new Dilation(-0.001F))
            .uv(0, 3).cuboid(-1.0F, -0.75F, -2.5F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -1.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -6.0F, -6.5F, 6.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.5F, 2.5F, -0.2618F, 0.0F, 0.0F));

        torso.addChild("cube_r1", ModelPartBuilder.create().uv(16, 16).cuboid(-0.5F, -3.0F, -0.5F, 5.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -3.25F, 2.75F, 0.3491F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(1, 24).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -6.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(1, 24).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 11.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, -6.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -7.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(24, 9).cuboid(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, -0.1F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(24, 0).mirrored().cuboid(-8.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, -7.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(24, 9).mirrored().cuboid(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-8.0F, -0.1F, -1.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(34, 17).cuboid(-1.5F, -1.0F, 1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
            .uv(40, 17).cuboid(-1.0F, -1.002F, 1.75F, 2.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.75F, 6.0F, -0.0436F, 0.0F, 0.0F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(41, 17).cuboid(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.0F, 2.0F, 0.0F, -0.5236F, 0.0F));

        tail.addChild("cube_r3", ModelPartBuilder.create().uv(41, 17).mirrored().cuboid(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, -1.0F, 2.0F, 0.0F, 0.5236F, 0.0F));

        root.addChild("left_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F))
            .uv(-1, 0).mirrored().cuboid(-1.5F, 4.0F, -3.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.25F, 1.0F, 2.5F));

        root.addChild("right_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F))
            .uv(-1, 0).cuboid(-1.5F, 4.0F, -3.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.25F, 1.0F, 2.5F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}