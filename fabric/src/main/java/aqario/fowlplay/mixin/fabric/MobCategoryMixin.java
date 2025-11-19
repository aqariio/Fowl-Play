package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.core.platform.CustomMobCategory;
import aqario.fowlplay.core.platform.fabric.CustomMobCategoryImpl;
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
    private static void fowlplay$addCustomCategories(CallbackInfo ci) {
        ArrayList<MobCategory> category = new ArrayList<>(Arrays.asList($VALUES));
        int vanillaLength = category.get(category.size() - 1).ordinal();

        MobCategory ambientBirdsCategory = newMobCategory(
            CustomMobCategory.AMBIENT_BIRDS_INTERNAL_NAME,
            vanillaLength + 1,
            CustomMobCategory.AMBIENT_BIRDS_NAME,
            CustomMobCategory.AMBIENT_BIRDS_SPAWN_CAP,
            CustomMobCategory.AMBIENT_BIRDS_PEACEFUL,
            CustomMobCategory.AMBIENT_BIRDS_RARE,
            CustomMobCategory.AMBIENT_BIRDS_IMMEDIATE_DESPAWN_RANGE
        );
        CustomMobCategoryImpl.AMBIENT_BIRDS = ambientBirdsCategory;
        category.add(ambientBirdsCategory);

        MobCategory birdsCategory = newMobCategory(
            CustomMobCategory.BIRDS_INTERNAL_NAME,
            vanillaLength + 2,
            CustomMobCategory.BIRDS_NAME,
            CustomMobCategory.BIRDS_SPAWN_CAP,
            CustomMobCategory.BIRDS_PEACEFUL,
            CustomMobCategory.BIRDS_RARE,
            CustomMobCategory.BIRDS_IMMEDIATE_DESPAWN_RANGE
        );
        CustomMobCategoryImpl.BIRDS = birdsCategory;
        category.add(birdsCategory);

        $VALUES = category.toArray(new MobCategory[0]);
    }
}