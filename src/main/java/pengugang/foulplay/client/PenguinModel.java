package pengugang.foulplay.client;

import net.minecraft.util.Identifier;
import pengugang.foulplay.FoulPlay;
import pengugang.foulplay.entity.PenguinEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PenguinModel extends AnimatedGeoModel<PenguinEntity> {
    @Override
    public Identifier getModelLocation(PenguinEntity object) {
        return object.isBaby() ? new Identifier(FoulPlay.MODID, "geo/penguin_baby.geo.json") : new Identifier(FoulPlay.MODID, "geo/penguin.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PenguinEntity object) {
        return new Identifier(FoulPlay.MODID, "textures/entity/penguin/penguin.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PenguinEntity animatable) {
        return new Identifier(FoulPlay.MODID, "animations/penguin.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(PenguinEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
