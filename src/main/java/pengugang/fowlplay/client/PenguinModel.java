package pengugang.fowlplay.client;

import net.minecraft.util.Identifier;
import pengugang.fowlplay.FowlPlay;
import pengugang.fowlplay.entity.PenguinEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PenguinModel extends AnimatedGeoModel<PenguinEntity> {
    @Override
    public Identifier getModelLocation(PenguinEntity object) {
        return object.isBaby() ? new Identifier(FowlPlay.MODID, "geo/penguin_baby.geo.json") : new Identifier(FowlPlay.MODID, "geo/penguin.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PenguinEntity object) {
        return new Identifier(FowlPlay.MODID, "textures/entity/penguin/penguin.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PenguinEntity animatable) {
        return new Identifier(FowlPlay.MODID, "animations/penguin.animation.json");
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
