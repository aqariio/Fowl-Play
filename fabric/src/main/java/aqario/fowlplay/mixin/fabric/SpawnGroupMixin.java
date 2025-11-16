package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.core.platform.CustomSpawnGroup;
import aqario.fowlplay.core.platform.fabric.CustomSpawnGroupImpl;
import net.minecraft.world.entity.MobCategory;
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

@Mixin(MobCategory.class)
public class SpawnGroupMixin {
    // Vanilla Spawn Groups array
    @Shadow
    @Mutable
    @Final
    private static MobCategory[] $VALUES;

    @Invoker("<init>")
    public static MobCategory newSpawnGroup(
        String enumname,
        int ordinal,
        String name,
        int spawnCap,
        boolean peaceful,
        boolean rare,
        int immediateDespawnRange
    ) {
        throw new AssertionError();
    }

    @Inject(
        method = "<clinit>",
        at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/world/entity/MobCategory;$VALUES:[Lnet/minecraft/world/entity/MobCategory;",
            shift = At.Shift.AFTER
        )
    )
    private static void fowlplay$addCustomGroups(CallbackInfo ci) {
        ArrayList<MobCategory> spawnGroups = new ArrayList<>(Arrays.asList($VALUES));
        int vanillaLength = spawnGroups.get(spawnGroups.size() - 1).ordinal();

        MobCategory glaresSpawnGroup = newSpawnGroup(
            CustomSpawnGroup.AMBIENT_BIRDS_INTERNAL_NAME,
            vanillaLength + 1,
            CustomSpawnGroup.AMBIENT_BIRDS_NAME,
            CustomSpawnGroup.AMBIENT_BIRDS_SPAWN_CAP,
            CustomSpawnGroup.AMBIENT_BIRDS_PEACEFUL,
            CustomSpawnGroup.AMBIENT_BIRDS_RARE,
            CustomSpawnGroup.AMBIENT_BIRDS_IMMEDIATE_DESPAWN_RANGE
        );
        CustomSpawnGroupImpl.AMBIENT_BIRDS = glaresSpawnGroup;
        spawnGroups.add(glaresSpawnGroup);

        MobCategory rascalsSpawnGroup = newSpawnGroup(
            CustomSpawnGroup.BIRDS_INTERNAL_NAME,
            vanillaLength + 2,
            CustomSpawnGroup.BIRDS_NAME,
            CustomSpawnGroup.BIRDS_SPAWN_CAP,
            CustomSpawnGroup.BIRDS_PEACEFUL,
            CustomSpawnGroup.BIRDS_RARE,
            CustomSpawnGroup.BIRDS_IMMEDIATE_DESPAWN_RANGE
        );
        CustomSpawnGroupImpl.BIRDS = rascalsSpawnGroup;
        spawnGroups.add(rascalsSpawnGroup);

        $VALUES = spawnGroups.toArray(new MobCategory[0]);
    }
}