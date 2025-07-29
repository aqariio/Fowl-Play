package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class FowlPlayEntityTypeTagGen extends FabricTagProvider.EntityTypeTagProvider {
    private static final Identifier ANCIENTSCALE = Identifier.of("fishofthieves", "ancientscale");
    private static final Identifier BATTLEGILL = Identifier.of("fishofthieves", "battlegill");
    private static final Identifier DEVILFISH = Identifier.of("fishofthieves", "devilfish");
    private static final Identifier ISLEHOPPER = Identifier.of("fishofthieves", "islehopper");
    private static final Identifier PLENTIFIN = Identifier.of("fishofthieves", "plentifin");
    private static final Identifier PONDIE = Identifier.of("fishofthieves", "pondie");
    private static final Identifier SPLASHTAIL = Identifier.of("fishofthieves", "splashtail");
    private static final Identifier STORMFISH = Identifier.of("fishofthieves", "stormfish");
    private static final Identifier WILDSPLASH = Identifier.of("fishofthieves", "wildsplash");

    public FowlPlayEntityTypeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        // Birds
        FabricTagProvider<EntityType<?>>.FabricTagBuilder builder = this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.BIRDS)
            .add(EntityType.CHICKEN)
            .add(EntityType.PARROT);
        Registries.ENTITY_TYPE.forEach((type) -> {
            if(Registries.ENTITY_TYPE.getId(type).getNamespace().equals(FowlPlay.ID)) {
                builder.add(type);
            }
        });

        // Flightless
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.FLIGHTLESS)
            .add(FowlPlayEntityType.PENGUIN.get());

        // Passerines
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.PASSERINES)
            .add(FowlPlayEntityType.BLUE_JAY.get())
            .add(FowlPlayEntityType.CARDINAL.get())
            .add(FowlPlayEntityType.CHICKADEE.get())
            .add(FowlPlayEntityType.CROW.get())
            .add(FowlPlayEntityType.RAVEN.get())
            .add(FowlPlayEntityType.ROBIN.get())
            .add(FowlPlayEntityType.SPARROW.get());

        // Seabirds
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.SEABIRDS)
            .add(FowlPlayEntityType.GULL.get());

        // Waterbirds
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.WATERBIRDS)
            .add(FowlPlayEntityType.DUCK.get())
            .add(FowlPlayEntityType.GULL.get())
            .add(FowlPlayEntityType.PENGUIN.get());

        // Waterfowl
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.WATERFOWL)
            .add(FowlPlayEntityType.DUCK.get());

        // Entities to avoid
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.BLUE_JAY_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.CARDINAL_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.CHICKADEE_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.CROW_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.DUCK_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.GULL_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.HAWK_AVOIDS)
            .add(EntityType.PLAYER);
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.PENGUIN_AVOIDS)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.PIGEON_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.RAVEN_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.ROBIN_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.SPARROW_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());

        // Hunted when the target is an adult
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.GULL_HUNT_TARGETS)
            .add(EntityType.TROPICAL_FISH)
            .add(EntityType.SALMON)
            .add(EntityType.COD)
            .add(EntityType.TADPOLE)
            .addOptional(ANCIENTSCALE)
            .addOptional(BATTLEGILL)
            .addOptional(DEVILFISH)
            .addOptional(PLENTIFIN)
            .addOptional(PONDIE)
            .addOptional(SPLASHTAIL)
            .addOptional(STORMFISH)
            .addOptional(WILDSPLASH);
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.HAWK_HUNT_TARGETS)
            .add(EntityType.CHICKEN)
            .add(EntityType.FROG)
            .add(EntityType.RABBIT)
            .add(FowlPlayEntityType.PIGEON.get())
            .add(FowlPlayEntityType.SPARROW.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.PENGUIN_HUNT_TARGETS)
            .add(EntityType.TROPICAL_FISH)
            .add(EntityType.SALMON)
            .add(EntityType.COD)
            .add(EntityType.SQUID)
            .add(EntityType.GLOW_SQUID)
            .add(EntityType.TADPOLE)
            .addOptional(ANCIENTSCALE)
            .addOptional(BATTLEGILL)
            .addOptional(DEVILFISH)
            .addOptional(PLENTIFIN)
            .addOptional(PONDIE)
            .addOptional(SPLASHTAIL)
            .addOptional(STORMFISH)
            .addOptional(WILDSPLASH);
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.RAVEN_HUNT_TARGETS);

        // Hunted when the target is a baby
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.GULL_BABY_HUNT_TARGETS)
            .add(EntityType.CHICKEN)
            .add(EntityType.TURTLE);
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.HAWK_BABY_HUNT_TARGETS)
            .add(EntityType.CHICKEN)
            .add(EntityType.RABBIT)
            .add(FowlPlayEntityType.PIGEON.get())
            .add(FowlPlayEntityType.SPARROW.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.RAVEN_BABY_HUNT_TARGETS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityType.HAWK.get());

        // Entities to attack
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.CROW_ATTACK_TARGETS)
            .add(FowlPlayEntityType.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.RAVEN_ATTACK_TARGETS)
            .add(FowlPlayEntityType.HAWK.get());

        // Vanilla entity tags
        this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
            .setReplace(false)
            .add(FowlPlayEntityType.PENGUIN.get());
        this.getOrCreateTagBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)
            .setReplace(false)
            .add(FowlPlayEntityType.PENGUIN.get());
    }
}