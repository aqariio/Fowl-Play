package aqario.fowlplay.core.platform;

import aqario.fowlplay.client.RenderRegistry;
import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.common.registry.DataSerializerRegister;
import aqario.fowlplay.common.registry.RegistryBuilder;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface PlatformHelper {
    void biomeModifier$register();

    <T> RegistryBuilder<T> registryBuilder$create(ResourceKey<Registry<T>> registryKey);

    <T extends Mob> Item itemRegister$createSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties properties);

    @SuppressWarnings("unchecked")
    <T extends Item> void creativeTabs$addToEnd(Supplier<T> item, ResourceKey<CreativeModeTab>... tab);

    <T> CommonRegister<T> commonRegister$create(ResourceKey<? extends Registry<T>> key, String namespace);

    <T> CommonRegister<T> commonRegister$create(Registry<T> registry, String namespace);

    DataSerializerRegister dataSerializerRegister$create(String namespace);

    Holder<ChickenVariant> dataAttachmentHelper$getChickenVariant(Chicken entity);

    void dataAttachmentHelper$setChickenVariant(Chicken entity, Holder<ChickenVariant> variant);

    <T extends Entity> void renderRegistry$entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider);

    void renderRegistry$modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition);

    <T extends ParticleOptions, P extends ParticleType<T>> void renderRegistry$particleFactory(Supplier<P> supplier, RenderRegistry.WrappedParticleProvider<T> provider);
}
