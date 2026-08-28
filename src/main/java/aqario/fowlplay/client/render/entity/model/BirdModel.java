package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.ResourcePathBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.function.Supplier;

public class BirdModel<T extends BirdEntity & GeoAnimatable> extends GeoModel<T> {
    private final ResourceLocation entityId;
    private String headBone = "neck";
    @Nullable
    private String variant;

    public BirdModel(Supplier<EntityType<T>> entity) {
        this.entityId = EntityType.getKey(entity.get());
    }

    @Override
    public void setCustomAnimations(T entity, long instanceId, AnimationState<T> state) {
        GeoBone head = getAnimationProcessor().getBone(this.headBone);

        if(head != null) {
            EntityModelData entityData = state.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    public void setHeadBone(String headBone) {
        this.headBone = headBone;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        String bird = this.entityId.getPath();
        ResourcePathBuilder path = new ResourcePathBuilder()
            .add("geo/entity/")
            .add(bird + "/")
            .addIf("baby_", animatable.isBaby())
            .addIf(this.variant + "_", this.variant != null)
            .add(bird)
            .add(".geo.json");

        return this.entityId.withPath(path.build());
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        String bird = this.entityId.getPath();
        ResourcePathBuilder path = new ResourcePathBuilder()
            .add("textures/entity/")
            .add(bird)
            .add("/")
            .addIf("baby_", animatable.isBaby())
            .addIf(this.variant + "_", this.variant != null)
            .add(bird)
            .add(".png");

        return this.entityId.withPath(path.build());
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        String bird = this.entityId.getPath();
        ResourcePathBuilder path = new ResourcePathBuilder()
            .add("animations/entity/")
            .add(bird)
            .add("/")
            .addIf("baby_", animatable.isBaby())
            .addIf(this.variant + "_", this.variant != null)
            .add(bird)
            .add(".animation.json");

        return this.entityId.withPath(path.build());
    }
}
