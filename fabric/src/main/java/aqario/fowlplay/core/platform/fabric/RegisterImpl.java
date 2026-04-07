package aqario.fowlplay.core.platform.fabric;

import aqario.fowlplay.core.platform.Register;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class RegisterImpl {
    public static <T> Registry<T> registry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        FabricRegistryBuilder<T, MappedRegistry<T>> builder = FabricRegistryBuilder.createSimple(registryKey);
        if(sync) {
            builder.attribute(RegistryAttribute.SYNCED);
        }
        return builder.buildAndRegister();
    }

    @SafeVarargs
    public static <T extends Item> void addToItemGroup(Supplier<T> item, ResourceKey<CreativeModeTab>... tabs) {
        T entry = item.get();
        for(ResourceKey<CreativeModeTab> tab : tabs) {
            ItemGroupEvents.modifyEntriesEvent(tab).register(entries ->
                entries.accept(entry)
            );
        }
    }

    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        EntityRendererRegistry.register(type.get(), provider);
    }

    public static void modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        EntityModelLayerRegistry.registerModelLayer(location, definition::get);
    }

    public static <T extends ParticleOptions, P extends ParticleType<T>> void particleFactory(Supplier<P> supplier, Register.WrappedParticleProvider<T> provider) {
        ParticleFactoryRegistry.getInstance().register(supplier.get(), provider::create);
    }
}
