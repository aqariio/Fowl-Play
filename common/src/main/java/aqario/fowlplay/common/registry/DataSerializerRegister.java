package aqario.fowlplay.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.syncher.EntityDataSerializer;

public abstract class DataSerializerRegister {
    protected final String namespace;
    protected boolean registered = false;

    protected DataSerializerRegister(String namespace) {
        this.namespace = namespace;
    }

    @ExpectPlatform
    public static DataSerializerRegister create(String namespace) {
        throw new AssertionError();
    }

    public abstract <T> void register(String name, EntityDataSerializer<T> serializer);

    public void register() {
        if(this.registered) {
            throw new IllegalArgumentException("Already registered!");
        }
        this.registered = true;
    }
}