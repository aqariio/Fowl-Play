package aqario.fowlplay.common.entity.variant;

import net.minecraft.world.entity.EntityType;

public class EntityVariant {
    private final EntityType<?> entityType;
    private final String name;

    public EntityVariant(EntityType<?> entityType, String name) {
        this.entityType = entityType;
        this.name = name;
    }

    public EntityType<?> entityType() {
        return this.entityType;
    }

    public String name() {
        return this.name;
    }
}
