package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class RegisterImpl {
    public static final Object2ObjectOpenHashMap<Supplier<Item>, ResourceKey<CreativeModeTab>> ITEM_TO_GROUPS = new Object2ObjectOpenHashMap<>();
    public static final DeferredRegister<ChickenVariant> CHICKEN_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.CHICKEN_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<DuckVariant> DUCK_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.DUCK_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<GooseVariant> GOOSE_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.GOOSE_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<GullVariant> GULL_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.GULL_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<PigeonVariant> PIGEON_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.PIGEON_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<SparrowVariant> SPARROW_VARIANTS = DeferredRegister.create(
        FowlPlayRegistries.SPARROW_VARIANT,
        FowlPlay.ID
    );
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(
        FowlPlay.ID
    );
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(
        FowlPlay.ID
    );
    public static final ObjectArrayList<Registry<?>> REGISTRIES = new ObjectArrayList<>();
    public static final ObjectArrayList<Pair<ModelLayerLocation, Supplier<LayerDefinition>>> MODEL_LAYERS = new ObjectArrayList<>();
    public static final ObjectArrayList<Pair<Supplier<EntityType<?>>, EntityRendererProvider<?>>> ENTITY_RENDERERS = new ObjectArrayList<>();

    @SuppressWarnings("unchecked")
    public static <T> void variant(String id, ResourceKey<T> key, Supplier<T> variant) {
        if(key.isFor(FowlPlayRegistries.CHICKEN_VARIANT)) {
            CHICKEN_VARIANTS.register(id, (Supplier<ChickenVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.DUCK_VARIANT)) {
            DUCK_VARIANTS.register(id, (Supplier<DuckVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.GOOSE_VARIANT)) {
            GOOSE_VARIANTS.register(id, (Supplier<GooseVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.GULL_VARIANT)) {
            GULL_VARIANTS.register(id, (Supplier<GullVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.PIGEON_VARIANT)) {
            PIGEON_VARIANTS.register(id, (Supplier<PigeonVariant>) variant);
        }
        else if(key.isFor(FowlPlayRegistries.SPARROW_VARIANT)) {
            SPARROW_VARIANTS.register(id, (Supplier<SparrowVariant>) variant);
        }
    }

    public static Supplier<Block> block(String id, Supplier<Block> block) {
        return BLOCKS.register(id, block);
    }

    @SafeVarargs
    public static Supplier<Item> item(String id, Supplier<Item> item, ResourceKey<CreativeModeTab>... groups) {
        Supplier<Item> registry = ITEMS.register(id, item);
        for(ResourceKey<CreativeModeTab> group : groups) {
            addItemToItemGroup(registry, group);
        }
        return registry;
    }

    @SafeVarargs
    public static Supplier<Item> blockItem(String id, Supplier<Block> block, ResourceKey<CreativeModeTab>... groups) {
        return item(id, () -> new BlockItem(block.get(), new Item.Properties()), groups);
    }

    public static <T extends Mob> Supplier<Item> spawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return item(id, () -> new DeferredSpawnEggItem(entityType, backgroundColor, highlightColor, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    }

    public static <T> Registry<T> registry(ResourceKey<Registry<T>> registryKey, boolean sync) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        if(sync) {
            builder.sync(true);
        }
        Registry<T> registry = builder.create();
        REGISTRIES.add(registry);
        return registry;
    }

    public static void addItemToItemGroup(Supplier<Item> item, ResourceKey<CreativeModeTab> itemGroup) {
        ITEM_TO_GROUPS.put(item, itemGroup);
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
