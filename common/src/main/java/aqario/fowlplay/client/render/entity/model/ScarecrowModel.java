package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.core.FowlPlay;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ScarecrowModel extends ScarecrowArmorModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("scarecrow"), "main");
    public static final ModelLayerLocation INNER_ARMOR = new ModelLayerLocation(FowlPlay.id("scarecrow"), "inner_armor");
    public static final ModelLayerLocation OUTER_ARMOR = new ModelLayerLocation(FowlPlay.id("scarecrow"), "outer_armor");
    private static final String STAND = "stand";
    private final ModelPart stand;

    public ScarecrowModel(ModelPart root) {
        super(root);
        this.stand = root.getChild(STAND);
        this.hat.visible = false;
        this.leftLeg.visible = false;
        this.rightLeg.visible = false;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(
            PartNames.HAT,
            CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
            PartPose.offset(0.0F, 1.0F, 0.0F)
        );
        modelPartData.addOrReplaceChild(
            PartNames.BODY,
            CubeListBuilder.create().texOffs(8, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 10.0F, 0.0F)
        );
        modelPartData.addOrReplaceChild(
            PartNames.HEAD,
            CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -2.0F, 0.0F)
        );
        modelPartData.addOrReplaceChild(
            PartNames.LEFT_ARM,
            CubeListBuilder.create().texOffs(32, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(5.0F, 0.0F, 0.0F)
        );
        modelPartData.addOrReplaceChild(
            PartNames.RIGHT_ARM,
            CubeListBuilder.create().texOffs(32, 16).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
            PartPose.offset(-5.0F, 0.0F, 0.0F)
        );
        modelPartData.addOrReplaceChild(
            STAND,
            CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -2.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(2.0F, 10.0F, 0.0F)
        );
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return Iterables.concat(super.bodyParts(), ImmutableList.of(this.stand));
    }
}
