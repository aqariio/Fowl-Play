//~ expect_platform

package aqario.fowlplay.common.registry;

import aqario.fowlplay.fabric.common.registry.AttributeRegistryImpl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

public class AttributeRegistry {
    public static <T extends LivingEntity> void register(Supplier<EntityType<T>> type, AttributeSupplier.Builder attributeSupplier) {
        AttributeRegistryImpl.register(type, attributeSupplier);
    }
}
