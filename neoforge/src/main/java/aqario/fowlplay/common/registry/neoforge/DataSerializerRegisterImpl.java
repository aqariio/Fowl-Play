package aqario.fowlplay.common.registry.neoforge;

import aqario.fowlplay.common.registry.DataSerializerRegister;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Objects;

public class DataSerializerRegisterImpl extends DataSerializerRegister {
    private final DeferredRegister<EntityDataSerializer<?>> registry;

    private DataSerializerRegisterImpl(DeferredRegister<EntityDataSerializer<?>> registry, String namespace) {
        super(namespace);
        this.registry = registry;
    }

    public static DataSerializerRegister create(String namespace) {
        return new DataSerializerRegisterImpl(DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, namespace), namespace);
    }

    @Override
    public <T> void register(String name, EntityDataSerializer<T> serializer) {
        this.registry.register(name, () -> serializer);
    }

    @Override
    protected void platformRegister() {
        this.registry.register(Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus()));
    }
}
