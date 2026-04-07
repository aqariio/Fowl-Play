package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.core.neoforge.FowlPlayNeoForge;
import aqario.fowlplay.core.platform.Register;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class RegisterImpl {
    public static <T> Registry<T> registry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        if(sync) {
            builder.sync(true);
        }
        Registry<T> registry = builder.create();
        FowlPlayNeoForge.eventBus().<NewRegistryEvent>addListener(event -> event.register(registry));
        return registry;
    }

    @SafeVarargs
    public static <T extends Item> void addToCreativeTab(Supplier<T> item, ResourceKey<CreativeModeTab>... tabs) {
        for(ResourceKey<CreativeModeTab> tab : tabs) {
            FowlPlayNeoForge.eventBus().<BuildCreativeModeTabContentsEvent>addListener(event -> {
                if(tab == event.getTabKey()) {
                    event.accept(item.get());
                }
            });
        }
    }

    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        FowlPlayNeoForge.eventBus().<EntityRenderersEvent.RegisterRenderers>addListener(event ->
            event.registerEntityRenderer(type.get(), provider)
        );
    }

    public static void modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        FowlPlayNeoForge.eventBus().<EntityRenderersEvent.RegisterLayerDefinitions>addListener(event ->
            event.registerLayerDefinition(location, definition)
        );
    }

    public static <T extends ParticleOptions, P extends ParticleType<T>> void particleFactory(Supplier<P> type, Register.WrappedParticleProvider<T> provider) {
        FowlPlayNeoForge.eventBus().<RegisterParticleProvidersEvent>addListener(event ->
            event.registerSpriteSet(type.get(), provider::create)
        );
    }
}
