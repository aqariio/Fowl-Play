//? if forge {
/*package aqario.fowlplay.forge.common.registry;

import aqario.fowlplay.common.registry.CommonRegistry;
import aqario.fowlplay.common.registry.DataSerializerRegister;
import aqario.fowlplay.forge.core.FowlPlayForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class DataSerializerRegisterImpl extends DataSerializerRegister {
    private final DeferredRegister<EntityDataSerializer<?>> registry;

    private DataSerializerRegisterImpl(DeferredRegister<EntityDataSerializer<?>> registry, String namespace) {
        super(namespace);
        this.registry = registry;
    }

    public static DataSerializerRegister create(String namespace) {
        return new DataSerializerRegisterImpl(DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, namespace), namespace);
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeRegistry(CommonRegistry<T> registry, T value, FriendlyByteBuf buf) {
        buf.writeRegistryId((IForgeRegistry<T>) registry, value);
    }

    public static <T> T readRegistry(CommonRegistry<T> registry, Class<T> clazz, FriendlyByteBuf buf) {
        return buf.readRegistryIdSafe(clazz);
    }

    @Override
    public <T> void register(String name, EntityDataSerializer<T> serializer) {
        this.registry.register(name, () -> serializer);
    }

    @Override
    public void register() {
        super.register();
        this.registry.register(FowlPlayForge.eventBus());
    }
}
*///?}