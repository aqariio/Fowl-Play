package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.core.FowlPlay;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.Identifier;

public class ScarecrowEntityModel extends ScarecrowArmorEntityModel {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "scarecrow"), "main");
    public static final EntityModelLayer INNER_ARMOR = new EntityModelLayer(Identifier.of(FowlPlay.ID, "scarecrow"), "inner_armor");
    public static final EntityModelLayer OUTER_ARMOR = new EntityModelLayer(Identifier.of(FowlPlay.ID, "scarecrow"), "outer_armor");
    private static final String STAND = "stand";
    private final ModelPart stand;

    public ScarecrowEntityModel(ModelPart root) {
        super(root);
        this.stand = root.getChild(STAND);
        this.hat.visible = false;
        this.leftLeg.visible = false;
        this.rightLeg.visible = false;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
            EntityModelPartNames.HAT,
            ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)),
            ModelTransform.pivot(0.0F, 1.0F, 0.0F)
        );
        modelPartData.addChild(
            EntityModelPartNames.BODY,
            ModelPartBuilder.create().uv(8, 16).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 10.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.pivot(0.0F, 10.0F, 0.0F)
        );
        modelPartData.addChild(
            EntityModelPartNames.HEAD,
            ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)),
            ModelTransform.pivot(0.0F, -2.0F, 0.0F)
        );
        modelPartData.addChild(
            EntityModelPartNames.LEFT_ARM,
            ModelPartBuilder.create().uv(32, 16).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.pivot(5.0F, 0.0F, 0.0F)
        );
        modelPartData.addChild(
            EntityModelPartNames.RIGHT_ARM,
            ModelPartBuilder.create().uv(32, 16).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
            ModelTransform.pivot(-5.0F, 0.0F, 0.0F)
        );
        modelPartData.addChild(
            STAND,
            ModelPartBuilder.create().uv(0, 16).cuboid(-3.0F, -2.0F, -1.0F, 2.0F, 16.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.pivot(2.0F, 10.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.stand));
    }
}
