package pengugang.fowlplay.client;

import net.minecraft.util.Identifier;
import pengugang.fowlplay.FowlPlay;
import pengugang.fowlplay.entity.PenguinEntity;
import pengugang.fowlplay.entity.SeagullEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SeagullModel extends AnimatedGeoModel<SeagullEntity> {
    @Override
    public Identifier getModelResource(SeagullEntity object) {
        return object.isFlying() ? new Identifier(FowlPlay.MODID, "geo/seagull_flying.geo.json") : new Identifier(FowlPlay.MODID, "geo/seagull.geo.json");
    }

    @Override
    public Identifier getTextureResource(SeagullEntity object) {
        return new Identifier(FowlPlay.MODID, "textures/entity/seagull/seagull.png");
    }

    @Override
    public Identifier getAnimationResource(SeagullEntity animatable) {
        return new Identifier(FowlPlay.MODID, "animations/seagull.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(SeagullEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
