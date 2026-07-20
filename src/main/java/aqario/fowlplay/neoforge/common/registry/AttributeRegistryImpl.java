//? if neoforge {
/*package aqario.fowlplay.neoforge.common.registry;

import aqario.fowlplay.neoforge.core.FowlPlayNeoForge;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.function.Supplier;

public class AttributeRegistryImpl {
    public static <T extends LivingEntity> void register(Supplier<EntityType<T>> type, AttributeSupplier.Builder attributeSupplier) {
        FowlPlayNeoForge.eventBus().<EntityAttributeCreationEvent>addListener(event -> event.put(type.get(), attributeSupplier.build()));
    }
}
*///?}