//? if fabric {
package aqario.fowlplay.fabric.common.registry;

import aqario.fowlplay.common.registry.CommonRegistry;
import aqario.fowlplay.common.registry.DataSerializerRegister;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class DataSerializerRegisterImpl extends DataSerializerRegister {
    public DataSerializerRegisterImpl(String namespace) {
        super(namespace);
    }

    public static DataSerializerRegister create(String namespace) {
        return new DataSerializerRegisterImpl(namespace);
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeRegistry(CommonRegistry<T> registry, T value, FriendlyByteBuf buf) {
        buf.writeId((Registry<T>) registry, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T readRegistry(CommonRegistry<T> registry, Class<T> clazz, FriendlyByteBuf buf) {
        return buf.readById((Registry<T>) registry);
    }

    public <T> void register(String name, EntityDataSerializer<T> serializer) {
        EntityDataSerializers.registerSerializer(serializer);
    }
}
//?}