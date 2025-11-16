package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.core.platform.CustomSpawnLocation;
import aqario.fowlplay.core.platform.fabric.CustomSpawnLocationImpl;
import net.minecraft.world.entity.SpawnPlacements;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

// this is probably a bad idea
@Mixin(SpawnPlacements.Type.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public class SpawnRestrictionLocationMixin {
    // Vanilla Spawn Location array
    @Shadow
    @Mutable
    @Final
    private static SpawnPlacements.Type[] $VALUES;

    @Invoker("<init>")
    public static SpawnPlacements.Type newLocation(String enumname, int ordinal) {
        throw new AssertionError();
    }

    @Inject(
        method = "<clinit>",
        at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/world/entity/SpawnPlacements$Type;$VALUES:[Lnet/minecraft/world/entity/SpawnPlacements$Type;",
            shift = At.Shift.AFTER
        )
    )
    private static void fowlplay$addCustomLocations(CallbackInfo ci) {
        ArrayList<SpawnPlacements.Type> locations = new ArrayList<>(Arrays.asList($VALUES));
        int vanillaLength = locations.get(locations.size() - 1).ordinal();

        SpawnPlacements.Type ground = newLocation(
            CustomSpawnLocation.GROUND_INTERNAL_NAME,
            vanillaLength + 1
        );
        CustomSpawnLocationImpl.GROUND = ground;
        locations.add(ground);

        SpawnPlacements.Type semiaquatic = newLocation(
            CustomSpawnLocation.SEMIAQUATIC_INTERNAL_NAME,
            vanillaLength + 2
        );
        CustomSpawnLocationImpl.SEMIAQUATIC = semiaquatic;
        locations.add(semiaquatic);

        SpawnPlacements.Type aquatic = newLocation(
            CustomSpawnLocation.AQUATIC_INTERNAL_NAME,
            vanillaLength + 3
        );
        CustomSpawnLocationImpl.AQUATIC = aquatic;
        locations.add(aquatic);

        $VALUES = locations.toArray(new SpawnPlacements.Type[0]);
    }
}