//? if fabric {
package aqario.fowlplay.fabric.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

public class AttributeRegistryImpl {
    public static <T extends LivingEntity> void register(Supplier<EntityType<T>> type, AttributeSupplier.Builder attributeSupplier) {
        FabricDefaultAttributeRegistry.register(type.get(), attributeSupplier);
    }
}
//?}