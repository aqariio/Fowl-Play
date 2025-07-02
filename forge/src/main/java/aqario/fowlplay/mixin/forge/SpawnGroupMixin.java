package aqario.fowlplay.mixin.forge;

import aqario.fowlplay.common.entity.CustomSpawnGroup;
import net.minecraft.entity.SpawnGroup;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// credit to hybrid aquatic for the code
@Mixin(SpawnGroup.class)
public class SpawnGroupMixin {
    @SuppressWarnings("unused")
    SpawnGroupMixin(String enumname, int ordinal, String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        throw new AssertionError();
    }

    // Vanilla Spawn Groups array
    @Shadow
    @Mutable
    @Final
    private static SpawnGroup[] field_6301;

    @Unique
    private static SpawnGroup fowlplay$createSpawnGroup(String enumname, int ordinal, CustomSpawnGroup spawnGroup) {
        return ((SpawnGroup) (Object) new SpawnGroupMixin(enumname, ordinal, spawnGroup.name, spawnGroup.spawnCap, spawnGroup.peaceful, spawnGroup.rare, spawnGroup.immediateDespawnRange));
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/SpawnGroup;field_6301:[Lnet/minecraft/entity/SpawnGroup;", shift = At.Shift.AFTER))
    private static void fowlplay$addCustomGroups(CallbackInfo ci) {
        int vanillaSpawnGroupsLength = field_6301.length;
        CustomSpawnGroup[] groups = CustomSpawnGroup.values();
        field_6301 = Arrays.copyOf(field_6301, vanillaSpawnGroupsLength + groups.length);

        for(int i = 0; i < groups.length; i++) {
            int pos = vanillaSpawnGroupsLength + i;
            CustomSpawnGroup spawnGroup = groups[i];
            spawnGroup.spawnGroup = field_6301[pos] = fowlplay$createSpawnGroup(spawnGroup.name(), pos, spawnGroup);
        }
    }
}