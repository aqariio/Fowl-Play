package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.core.platform.CustomSpawnPlacementType;
import aqario.fowlplay.core.platform.fabric.CustomSpawnPlacementTypeImpl;
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

@Mixin(SpawnPlacements.Type.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public class SpawnPlacementTypeMixin {
    // Vanilla Spawn Placement Type array
    @Shadow
    @Mutable
    @Final
    private static SpawnPlacements.Type[] $VALUES;

    @Invoker("<init>")
    public static SpawnPlacements.Type newSpawnPlacementType(String enumname, int ordinal) {
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
    private static void fowlplay$addCustomTypes(CallbackInfo ci) {
        ArrayList<SpawnPlacements.Type> types = new ArrayList<>(Arrays.asList($VALUES));
        int vanillaLength = types.get(types.size() - 1).ordinal();

        SpawnPlacements.Type ground = newSpawnPlacementType(
            CustomSpawnPlacementType.GROUND_INTERNAL_NAME,
            vanillaLength + 1
        );
        CustomSpawnPlacementTypeImpl.GROUND = ground;
        types.add(ground);

        SpawnPlacements.Type semiaquatic = newSpawnPlacementType(
            CustomSpawnPlacementType.SEMIAQUATIC_INTERNAL_NAME,
            vanillaLength + 2
        );
        CustomSpawnPlacementTypeImpl.SEMIAQUATIC = semiaquatic;
        types.add(semiaquatic);

        SpawnPlacements.Type aquatic = newSpawnPlacementType(
            CustomSpawnPlacementType.AQUATIC_INTERNAL_NAME,
            vanillaLength + 3
        );
        CustomSpawnPlacementTypeImpl.AQUATIC = aquatic;
        types.add(aquatic);

        $VALUES = types.toArray(new SpawnPlacements.Type[0]);
    }
}