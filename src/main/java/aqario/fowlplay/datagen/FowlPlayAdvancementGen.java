package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.PlayerHurtEntityCriterion;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class FowlPlayAdvancementGen extends FabricAdvancementProvider {
    protected FowlPlayAdvancementGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> exporter) {
        NbtCompound flying = new NbtCompound();
        flying.putBoolean("flying", true);

        Advancement.Builder.createUntelemetered()
            .parent(advancement("adventure/shoot_arrow"))
            .display(
                Items.SPECTRAL_ARROW,
                Text.translatable("advancements.adventure.damage_flying_bird.title"),
                Text.translatable("advancements.adventure.damage_flying_bird.description"),
                null,
                AdvancementFrame.CHALLENGE,
                true,
                true,
                false
            )
            .rewards(AdvancementRewards.Builder.experience(50))
            .criterion(
                "hit_flying_bird",
                PlayerHurtEntityCriterion.Conditions.create(
                    DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.create().tag(TagPredicate.expected(DamageTypeTags.IS_PROJECTILE))),
                    EntityPredicate.Builder.create().type(FowlPlayEntityTypeTags.BIRDS).nbt(new NbtPredicate(flying)).build()
                )
            )
            .build(exporter, new Identifier(FowlPlay.ID, "adventure/damage_flying_bird").toString());

        Advancement.Builder.createUntelemetered()
            .parent(advancement("adventure/root"))
            .display(
                Items.FEATHER,
                Text.translatable("advancements.adventure.fly_penguin.title"),
                Text.translatable("advancements.adventure.fly_penguin.description"),
                null,
                AdvancementFrame.TASK,
                true,
                true,
                false
            )
            .criterion(
                "fall_with_penguin",
                new ImpossibleCriterion.Conditions()
            )
            .build(exporter, new Identifier(FowlPlay.ID, "adventure/fly_penguin").toString());
    }

    private static Advancement advancement(String id) {
        return advancement(new Identifier(id));
    }

    private static Advancement advancement(Identifier id) {
        return Advancement.Builder.create().build(id);
    }
}
