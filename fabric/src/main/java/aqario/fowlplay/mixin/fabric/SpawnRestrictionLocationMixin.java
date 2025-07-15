package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.core.platform.CustomSpawnLocation;
import aqario.fowlplay.core.platform.fabric.CustomSpawnLocationImpl;
import net.minecraft.entity.SpawnRestriction;
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
@Mixin(SpawnRestriction.Location.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public class SpawnRestrictionLocationMixin {
    // Vanilla Spawn Location array
    @Shadow
    @Mutable
    @Final
    private static SpawnRestriction.Location[] field_6319;

    @Invoker("<init>")
    public static SpawnRestriction.Location newLocation(String enumname, int ordinal) {
        throw new AssertionError();
    }

    @Inject(
        method = "<clinit>",
        at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/entity/SpawnRestriction$Location;field_6319:[Lnet/minecraft/entity/SpawnRestriction$Location;",
            shift = At.Shift.AFTER
        )
    )
    private static void fowlplay$addCustomLocations(CallbackInfo ci) {
        ArrayList<SpawnRestriction.Location> locations = new ArrayList<>(Arrays.asList(field_6319));
        int vanillaLength = locations.get(locations.size() - 1).ordinal();

        SpawnRestriction.Location ground = newLocation(
            CustomSpawnLocation.GROUND_INTERNAL_NAME,
            vanillaLength + 1
        );
        CustomSpawnLocationImpl.GROUND = ground;
        locations.add(ground);

        SpawnRestriction.Location semiaquatic = newLocation(
            CustomSpawnLocation.SEMIAQUATIC_INTERNAL_NAME,
            vanillaLength + 2
        );
        CustomSpawnLocationImpl.SEMIAQUATIC = semiaquatic;
        locations.add(semiaquatic);

        SpawnRestriction.Location aquatic = newLocation(
            CustomSpawnLocation.AQUATIC_INTERNAL_NAME,
            vanillaLength + 3
        );
        CustomSpawnLocationImpl.AQUATIC = aquatic;
        locations.add(aquatic);

        field_6319 = locations.toArray(new SpawnRestriction.Location[0]);
    }
}