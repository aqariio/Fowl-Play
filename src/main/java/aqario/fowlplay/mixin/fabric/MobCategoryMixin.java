//? if fabric {
package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.common.entity.FPMobCategory;
import aqario.fowlplay.fabric.common.entity.FPMobCategoryImpl;
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

// credit to hybrid aquatic for the code
@Mixin(MobCategory.class)
public class MobCategoryMixin {
    // Vanilla Mob Category array
    @Shadow
    @Mutable
    @Final
    private static MobCategory[] $VALUES;

    @Invoker("<init>")
    public static MobCategory newMobCategory(
        String enumname,
        int ordinal,
        String name,
        int max,
        boolean isFriendly,
        boolean isPersistent,
        int despawnDistance
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
    private static void fowlplay$addCustomCategories(CallbackInfo ci) {
        ArrayList<MobCategory> category = new ArrayList<>(Arrays.asList($VALUES));
        int vanillaLength = category.get(category.size() - 1).ordinal();

        MobCategory ambientBirdsCategory = newMobCategory(
            FPMobCategory.AMBIENT_BIRDS_INTERNAL_NAME,
            vanillaLength + 1,
            FPMobCategory.AMBIENT_BIRDS_NAME,
            FPMobCategory.AMBIENT_BIRDS_MAX,
            FPMobCategory.AMBIENT_BIRDS_IS_FRIENDLY,
            FPMobCategory.AMBIENT_BIRDS_IS_PERSISTENT,
            FPMobCategory.AMBIENT_BIRDS_DESPAWN_DISTANCE
        );
        FPMobCategoryImpl.AMBIENT_BIRDS = ambientBirdsCategory;
        category.add(ambientBirdsCategory);

        MobCategory birdsCategory = newMobCategory(
            FPMobCategory.BIRDS_INTERNAL_NAME,
            vanillaLength + 2,
            FPMobCategory.BIRDS_NAME,
            FPMobCategory.BIRDS_MAX,
            FPMobCategory.BIRDS_IS_FRIENDLY,
            FPMobCategory.BIRDS_IS_PERSISTENT,
            FPMobCategory.BIRDS_DESPAWN_DISTANCE
        );
        FPMobCategoryImpl.BIRDS = birdsCategory;
        category.add(birdsCategory);

        $VALUES = category.toArray(new MobCategory[0]);
    }
}
//?}