package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.Domesticatable;
import aqario.fowlplay.common.entity.bird.VariantHolder;
import aqario.fowlplay.common.util.ResourcePathBuilder;
import net.minecraft.ChatFormatting;
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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BirdModel<T extends BirdEntity & GeoAnimatable> extends GeoModel<T> {
    private final ResourceLocation entityId;
    @Nullable
    private List<String> customNames;
    private BiPredicate<T, AssetType> babyAffixPredicate = (bird, assetType) -> bird.isBaby();
    private BiPredicate<T, AssetType> domesticAffixPredicate = (bird, assetType) ->
        assetType == AssetType.TEXTURE || !bird.isBaby();
    private BiPredicate<T, AssetType> variantAffixPredicate = (bird, assetType) ->
        assetType == AssetType.TEXTURE;
    private Predicate<T> dontRotateHeadPredicate = bird -> bird.isSleeping() || bird.isIdleAnimationActive();

    public BirdModel(Supplier<EntityType<T>> entity) {
        this.entityId = EntityType.getKey(entity.get());
    }

    protected GeoBone root() {
        return this.getPresentBone("root");
    }

    protected GeoBone body() {
        return this.getPresentBone("body");
    }

    protected GeoBone neck() {
        return this.getPresentBone("neck");
    }

    protected GeoBone head() {
        return this.getPresentBone("head");
    }

    protected GeoBone torso() {
        return this.getPresentBone("torso");
    }

    protected GeoBone leftWing() {
        return this.getPresentBone("left_wing");
    }

    protected GeoBone rightWing() {
        return this.getPresentBone("right_wing");
    }

    protected GeoBone leftLeg() {
        return this.getPresentBone("left_leg");
    }

    protected GeoBone rightLeg() {
        return this.getPresentBone("right_leg");
    }

    protected GeoBone tail() {
        return this.getPresentBone("tail");
    }

    public GeoBone getPresentBone(String name) {
        return this.getAnimationProcessor().getBone(name);
    }

    @Override
    public void setCustomAnimations(T bird, long instanceId, AnimationState<T> state) {
        if(!this.dontRotateHeadPredicate.test(bird)) {
            EntityModelData entityData = state.getData(DataTickets.ENTITY_MODEL_DATA);
            this.head().setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            this.head().setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    public BirdModel<T> dontRotateHeadWhen(Predicate<T> predicate) {
        this.dontRotateHeadPredicate = predicate; // don't rotate when flying, idle animation, swimming, sliding, sleeping
        return this;
    }

    public BirdModel<T> customNames(String... names) {
        this.customNames = Arrays.stream(names).toList();
        return this;
    }

    public BirdModel<T> babyAffixWhen(BiPredicate<T, AssetType> predicate) {
        this.babyAffixPredicate = predicate;
        return this;
    }

    public BirdModel<T> domesticAffixWhen(BiPredicate<T, AssetType> predicate) {
        this.domesticAffixPredicate = predicate;
        return this;
    }

    public BirdModel<T> variantAffixWhen(BiPredicate<T, AssetType> predicate) {
        this.variantAffixPredicate = predicate;
        return this;
    }

    @Override
    public ResourceLocation getModelResource(T bird) {
        return this.generateAssetResource(bird, AssetType.MODEL);
    }

    @Override
    public ResourceLocation getTextureResource(T bird) {
        return this.generateAssetResource(bird, AssetType.TEXTURE);
    }

    @Override
    public ResourceLocation getAnimationResource(T bird) {
        return this.generateAssetResource(bird, AssetType.ANIMATION);
    }

    private ResourceLocation generateAssetResource(T bird, AssetType assetType) {
        String type = this.entityId.getPath();
        ResourcePathBuilder path = new ResourcePathBuilder()
            .add(assetType.directory).add("/entity/").add(type).add("/");

        if(assetType == AssetType.TEXTURE && this.customNames != null) {
            String customName = ChatFormatting.stripFormatting(bird.getName().getString());
            for(String name : this.customNames) {
                if(name.equals(customName)) {
                    return this.entityId.withPath(path.add(name.toLowerCase(Locale.ROOT)).add(assetType.extension).build());
                }
            }
        }

        if(this.babyAffixPredicate.test(bird, assetType)) {
            path.add("baby_");
        }
        if(this.domesticAffixPredicate.test(bird, assetType) && bird instanceof Domesticatable domesticBird && domesticBird.isDomestic()) {
            path.add("domestic_");
        }
        if(this.variantAffixPredicate.test(bird, assetType) && bird instanceof VariantHolder<?> variantBird) {
            path.add(variantBird.getVariantName()).add("_");
        }

        return this.entityId.withPath(path.add(type).add(assetType.extension).build());
    }

    public enum AssetType {
        MODEL("geo", ".geo.json"),
        TEXTURE("textures", ".png"),
        ANIMATION("animations", ".animation.json");

        private final String directory;
        private final String extension;

        AssetType(String directory, String extension) {
            this.directory = directory;
            this.extension = extension;
        }
    }
}
