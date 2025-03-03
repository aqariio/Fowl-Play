package aqario.fowlplay.mixin;

import aqario.fowlplay.common.world.gen.FowlPlaySpawnLocation;
import net.minecraft.entity.SpawnRestriction;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// this is definitely a bad idea
@Mixin(SpawnRestriction.Location.class)
public class SpawnRestrictionLocationMixin {
    @SuppressWarnings("unused")
    SpawnRestrictionLocationMixin(String enumname, int ordinal) {
        throw new AssertionError();
    }

    // Vanilla Spawn Location array
    @Shadow
    @Mutable
    @Final
    private static SpawnRestriction.Location[] field_6319;

    @Unique
    private static SpawnRestriction.Location createLocation(String enumname, int ordinal) {
        return ((SpawnRestriction.Location) (Object) new SpawnRestrictionLocationMixin(enumname, ordinal));
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/SpawnRestriction$Location;field_6319:[Lnet/minecraft/entity/SpawnRestriction$Location;", shift = At.Shift.AFTER))
    private static void addLocations(CallbackInfo ci) {
        int vanillaSpawnLocationsLength = field_6319.length;
        FowlPlaySpawnLocation[] locations = FowlPlaySpawnLocation.values();
        field_6319 = Arrays.copyOf(field_6319, vanillaSpawnLocationsLength + locations.length);

        for (int i = 0; i < locations.length; i++) {
            int pos = vanillaSpawnLocationsLength + i;
            FowlPlaySpawnLocation location = locations[i];
            location.location = field_6319[pos] = createLocation(location.name(), pos);
        }
    }
}