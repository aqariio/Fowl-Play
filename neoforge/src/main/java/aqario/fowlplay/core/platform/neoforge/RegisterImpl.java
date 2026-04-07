package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.core.platform.Register;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class RegisterImpl {
    public static final Object2ObjectOpenHashMap<Supplier<? extends Item>, ResourceKey<CreativeModeTab>> ITEM_TO_GROUPS = new Object2ObjectOpenHashMap<>();
    public static final ObjectArrayList<Registry<?>> REGISTRIES = new ObjectArrayList<>();
    public static final ObjectArrayList<Pair<ModelLayerLocation, Supplier<LayerDefinition>>> MODEL_LAYERS = new ObjectArrayList<>();
    public static final ObjectArrayList<Pair<Supplier<EntityType<?>>, EntityRendererProvider<?>>> ENTITY_RENDERERS = new ObjectArrayList<>();

    public static <T> Registry<T> registry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        if(sync) {
            builder.sync(true);
        }
        Registry<T> registry = builder.create();
        REGISTRIES.add(registry);
        return registry;
    }

    @SafeVarargs
    public static <T extends Item> void addToItemGroup(Supplier<T> item, ResourceKey<CreativeModeTab>... tabs) {
        for(ResourceKey<CreativeModeTab> tab : tabs) {
            ITEM_TO_GROUPS.put(item, tab);
        }
    }

    public static <T extends Entity> void entityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus()).<EntityRenderersEvent.RegisterRenderers>addListener(event ->
            event.registerEntityRenderer(type.get(), provider)
        );
    }

    public static void modelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus()).<EntityRenderersEvent.RegisterLayerDefinitions>addListener(event ->
            event.registerLayerDefinition(location, definition)
        );
    }

    public static <T extends ParticleOptions, P extends ParticleType<T>> void particleFactory(Supplier<P> type, Register.WrappedParticleProvider<T> provider) {
        Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus()).<RegisterParticleProvidersEvent>addListener(event ->
            event.registerSpriteSet(type.get(), provider::create)
        );
    }
}
