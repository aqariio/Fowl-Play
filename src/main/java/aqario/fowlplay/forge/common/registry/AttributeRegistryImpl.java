//? if forge {
/*package aqario.fowlplay.forge.common.registry;

import aqario.fowlplay.forge.core.FowlPlayForge;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import java.util.function.Supplier;

public class AttributeRegistryImpl {
    public static <T extends LivingEntity> void register(Supplier<EntityType<T>> type, AttributeSupplier.Builder attributeSupplier) {
        FowlPlayForge.eventBus().<EntityAttributeCreationEvent>addListener(event -> event.put(type.get(), attributeSupplier.build()));
    }
}
*///?}