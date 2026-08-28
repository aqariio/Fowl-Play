package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import java.util.function.Supplier;

public class PenguinRenderer extends BirdRenderer<PenguinEntity> {
    public PenguinRenderer(EntityRendererProvider.Context context, Supplier<EntityType<PenguinEntity>> entityType) {
        super(context, entityType, modelBuilder -> modelBuilder
            .customNames("Pingu")
        );
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, PenguinEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
        String name = ChatFormatting.stripFormatting(animatable.getName().getString());
        if(name.equalsIgnoreCase("rico")) {
            poseStack.scale(1.1F, 1F, 1F);
        }
        if(name.equalsIgnoreCase("skipper")) {
            poseStack.scale(1.25F, 0.9F, 1F);
        }
        if(name.equalsIgnoreCase("kowalski")) {
            poseStack.scale(1F, 1.1F, 1F);
        }
        if(name.equalsIgnoreCase("private")) {
            poseStack.scale(1.2F, 0.85F, 1F);
        }
    }
}
