package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.client.RenderRegistry;
import aqario.fowlplay.client.neoforge.RenderRegistryImpl;
import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.common.registry.DataSerializerRegister;
import aqario.fowlplay.common.registry.RegistryBuilder;
import aqario.fowlplay.common.registry.neoforge.*;
import aqario.fowlplay.common.worldgen.neoforge.BiomeModifierImpl;
import aqario.fowlplay.core.platform.PlatformHelper;
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

public class PlatformNeoForge implements PlatformHelper {
    @Override
    public void biomeModifier$register() {
        BiomeModifierImpl.register();
    }

    @Override
    public <T> RegistryBuilder<T> registryBuilder$create(ResourceKey<Registry<T>> registryKey) {
        return RegistryBuilderImpl.create(registryKey);
    }

    @Override
    public <T extends Mob> Item itemRegister$createSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties properties) {
        return ItemRegisterImpl.createSpawnEgg(entity, primaryColor, secondaryColor, properties);
    }

    @SafeVarargs
    @Override
    public final <T extends Item> void creativeTabs$addToEnd(Supplier<T> item, ResourceKey<CreativeModeTab>... tab) {
        CreativeTabsImpl.addToEnd(item, tab);
    }

    @Override
    public <T> CommonRegister<T> commonRegister$create(ResourceKey<? extends Registry<T>> key, String namespace) {
        return CommonRegisterImpl.create(key, namespace);
    }

    @Override
    public <T> CommonRegister<T> commonRegister$create(Registry<T> registry, String namespace) {
        return CommonRegisterImpl.create(registry, namespace);
    }

    @Override
    public DataSerializerRegister dataSerializerRegister$create(String namespace) {
        return DataSerializerRegisterImpl.create(namespace);
    }

    @Override
    public Holder<ChickenVariant> dataAttachmentHelper$getChickenVariant(Chicken entity) {
        return DataAttachmentHelperImpl.getChickenVariant(entity);
    }

    @Override
    public void dataAttachmentHelper$setChickenVariant(Chicken entity, Holder<ChickenVariant> variant) {
        DataAttachmentHelperImpl.setChickenVariant(entity, variant);
    }

    @Override
    public <T extends Entity> void renderRegistry$entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        RenderRegistryImpl.entityRenderer(type, provider);
    }

    @Override
    public void renderRegistry$modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        RenderRegistryImpl.modelLayer(location, definition);
    }

    @Override
    public <T extends ParticleOptions, P extends ParticleType<T>> void renderRegistry$particleFactory(Supplier<P> supplier, RenderRegistry.WrappedParticleProvider<T> provider) {
        RenderRegistryImpl.particleFactory(supplier, provider);
    }
}
