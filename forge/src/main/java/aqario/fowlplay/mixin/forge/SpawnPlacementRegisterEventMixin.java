package aqario.fowlplay.mixin.forge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SpawnPlacementRegisterEvent.class)
public class SpawnPlacementRegisterEventMixin {
    @Shadow
    @Final
    private Map<EntityType<?>, SpawnPlacementRegisterEvent.MergedSpawnPredicate<?>> map;

    @Inject(method = "register(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/Heightmap$Type;Lnet/minecraft/entity/SpawnRestriction$SpawnPredicate;Lnet/minecraftforge/event/entity/SpawnPlacementRegisterEvent$Operation;)V", at = @At("HEAD"), cancellable = true)
    private <T extends Entity> void fowlplay$removeNullCheck(EntityType<T> entityType, @Nullable SpawnRestriction.Location placementType, @Nullable Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> predicate, SpawnPlacementRegisterEvent.Operation operation, CallbackInfo ci) {
        if(!map.containsKey(entityType)) {
            map.put(entityType, new SpawnPlacementRegisterEvent.MergedSpawnPredicate<>(predicate, placementType, heightmap));
        }
        ci.cancel();
    }
}
