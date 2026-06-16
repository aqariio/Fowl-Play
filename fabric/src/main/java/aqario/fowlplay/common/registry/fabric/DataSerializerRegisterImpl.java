package aqario.fowlplay.common.registry.fabric;

import aqario.fowlplay.common.registry.DataSerializerRegister;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class DataSerializerRegisterImpl extends DataSerializerRegister {
    public DataSerializerRegisterImpl(String namespace) {
        super(namespace);
    }

    public static DataSerializerRegister create(String namespace) {
        return new DataSerializerRegisterImpl(namespace);
    }

    public <T> void register(String name, EntityDataSerializer<T> serializer) {
        EntityDataSerializers.registerSerializer(serializer);
    }
}
