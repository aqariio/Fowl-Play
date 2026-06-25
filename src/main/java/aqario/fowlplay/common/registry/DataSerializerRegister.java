//~ expect_platform

package aqario.fowlplay.common.registry;

import aqario.fowlplay.fabric.common.registry.DataSerializerRegisterImpl;
import net.minecraft.network.syncher.EntityDataSerializer;

public abstract class DataSerializerRegister {
    protected final String namespace;
    protected boolean registered = false;

    protected DataSerializerRegister(String namespace) {
        this.namespace = namespace;
    }

    public static DataSerializerRegister create(String namespace) {
        return DataSerializerRegisterImpl.create(namespace);
    }

    public abstract <T> void register(String name, EntityDataSerializer<T> serializer);

    public void register() {
        if(this.registered) {
            throw new IllegalArgumentException("Already registered!");
        }
        this.registered = true;
    }
}