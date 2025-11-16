package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class FowlPlayAdvancementGen extends FabricAdvancementProvider {
    protected FowlPlayAdvancementGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> exporter) {
        CompoundTag flying = new CompoundTag();
        flying.putBoolean("flying", true);

        Advancement.Builder.recipeAdvancement()
            .parent(advancement("adventure/shoot_arrow"))
            .display(
                Items.SPECTRAL_ARROW,
                Component.translatable("advancements.adventure.damage_flying_bird.title"),
                Component.translatable("advancements.adventure.damage_flying_bird.description"),
                null,
                FrameType.CHALLENGE,
                true,
                true,
                false
            )
            .rewards(AdvancementRewards.Builder.experience(50))
            .addCriterion(
                "hit_flying_bird",
                PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
                    DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))),
                    EntityPredicate.Builder.entity().of(FowlPlayEntityTypeTags.BIRDS).nbt(new NbtPredicate(flying)).build()
                )
            )
            .save(exporter, FowlPlay.id("adventure/damage_flying_bird").toString());

        Advancement.Builder.recipeAdvancement()
            .parent(advancement("adventure/root"))
            .display(
                Items.FEATHER,
                Component.translatable("advancements.adventure.fly_penguin.title"),
                Component.translatable("advancements.adventure.fly_penguin.description"),
                null,
                FrameType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "fall_with_penguin",
                new ImpossibleTrigger.TriggerInstance()
            )
            .save(exporter, FowlPlay.id("adventure/fly_penguin").toString());
    }

    private static Advancement advancement(String id) {
        return advancement(new ResourceLocation(id));
    }

    private static Advancement advancement(ResourceLocation id) {
        return Advancement.Builder.advancement().build(id);
    }
}