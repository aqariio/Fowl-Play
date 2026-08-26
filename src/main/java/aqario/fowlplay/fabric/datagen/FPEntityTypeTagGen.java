//? if fabric {
package aqario.fowlplay.fabric.datagen;

import aqario.fowlplay.core.FPEntityTypes;
import aqario.fowlplay.core.tags.FPEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class FPEntityTypeTagGen extends FabricTagProvider.EntityTypeTagProvider {
    private static final Identifier ANCIENTSCALE = Identifier.fromNamespaceAndPath("fishofthieves", "ancientscale");
    private static final Identifier BATTLEGILL = Identifier.fromNamespaceAndPath("fishofthieves", "battlegill");
    private static final Identifier DEVILFISH = Identifier.fromNamespaceAndPath("fishofthieves", "devilfish");
    private static final Identifier ISLEHOPPER = Identifier.fromNamespaceAndPath("fishofthieves", "islehopper");
    private static final Identifier PLENTIFIN = Identifier.fromNamespaceAndPath("fishofthieves", "plentifin");
    private static final Identifier PONDIE = Identifier.fromNamespaceAndPath("fishofthieves", "pondie");
    private static final Identifier SPLASHTAIL = Identifier.fromNamespaceAndPath("fishofthieves", "splashtail");
    private static final Identifier STORMFISH = Identifier.fromNamespaceAndPath("fishofthieves", "stormfish");
    private static final Identifier WILDSPLASH = Identifier.fromNamespaceAndPath("fishofthieves", "wildsplash");

    public FPEntityTypeTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        // Birds
        this.getOrCreateTagBuilder(FPEntityTypeTags.BIRDS)
            .add(EntityType.CHICKEN)
            .add(EntityType.PARROT)
            .add(FPEntityTypes.BLUE_JAY.get())
            .add(FPEntityTypes.CARDINAL.get())
            .add(FPEntityTypes.CHICKADEE.get())
            .add(FPEntityTypes.CROW.get())
            .add(FPEntityTypes.DUCK.get())
            .add(FPEntityTypes.GOOSE.get())
            .add(FPEntityTypes.GULL.get())
            .add(FPEntityTypes.HAWK.get())
            .add(FPEntityTypes.PENGUIN.get())
            .add(FPEntityTypes.PIGEON.get())
            .add(FPEntityTypes.RAVEN.get())
            .add(FPEntityTypes.ROBIN.get())
            .add(FPEntityTypes.SPARROW.get());

        // Flightless
        this.getOrCreateTagBuilder(FPEntityTypeTags.FLIGHTLESS)
            .add(FPEntityTypes.PENGUIN.get());

        // Perching Birds
        this.getOrCreateTagBuilder(FPEntityTypeTags.PERCHING_BIRDS)
            .add(EntityType.CHICKEN)
            .add(EntityType.PARROT)
            .add(FPEntityTypes.BLUE_JAY.get())
            .add(FPEntityTypes.CARDINAL.get())
            .add(FPEntityTypes.CHICKADEE.get())
            .add(FPEntityTypes.CROW.get())
            .add(FPEntityTypes.HAWK.get())
            .add(FPEntityTypes.PIGEON.get())
            .add(FPEntityTypes.RAVEN.get())
            .add(FPEntityTypes.ROBIN.get())
            .add(FPEntityTypes.SPARROW.get());

        // Passerines
        this.getOrCreateTagBuilder(FPEntityTypeTags.PASSERINES)
            .add(FPEntityTypes.BLUE_JAY.get())
            .add(FPEntityTypes.CARDINAL.get())
            .add(FPEntityTypes.CHICKADEE.get())
            .add(FPEntityTypes.CROW.get())
            .add(FPEntityTypes.RAVEN.get())
            .add(FPEntityTypes.ROBIN.get())
            .add(FPEntityTypes.SPARROW.get());

        // Seabirds
        this.getOrCreateTagBuilder(FPEntityTypeTags.SEABIRDS)
            .add(FPEntityTypes.GULL.get());

        // Waterbirds
        this.getOrCreateTagBuilder(FPEntityTypeTags.WATERBIRDS)
            .add(FPEntityTypes.DUCK.get())
            .add(FPEntityTypes.GOOSE.get())
            .add(FPEntityTypes.GULL.get())
            .add(FPEntityTypes.PENGUIN.get());

        // Waterfowl
        this.getOrCreateTagBuilder(FPEntityTypeTags.WATERFOWL)
            .add(FPEntityTypes.DUCK.get())
            .add(FPEntityTypes.GOOSE.get());

        // Entities to avoid
        this.getOrCreateTagBuilder(FPEntityTypeTags.BLUE_JAY_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.CARDINAL_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.CHICKADEE_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.CROW_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.DUCK_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.GOOSE_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.GULL_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.HAWK_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.PENGUIN_AVOIDS)
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.PIGEON_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.RAVEN_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.ROBIN_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.SPARROW_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FPEntityTypes.SCARECROW.get())
            .add(FPEntityTypes.HAWK.get());

        // Hunted when the target is an adult
        this.getOrCreateTagBuilder(FPEntityTypeTags.GULL_HUNT_TARGETS)
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
        this.getOrCreateTagBuilder(FPEntityTypeTags.HAWK_HUNT_TARGETS)
            .add(EntityType.CHICKEN)
            .add(EntityType.FROG)
            .add(EntityType.RABBIT)
            .add(FPEntityTypes.PIGEON.get())
            .add(FPEntityTypes.SPARROW.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.PENGUIN_HUNT_TARGETS)
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
        this.getOrCreateTagBuilder(FPEntityTypeTags.RAVEN_HUNT_TARGETS);

        // Hunted when the target is a baby
        this.getOrCreateTagBuilder(FPEntityTypeTags.GULL_BABY_HUNT_TARGETS)
            .add(EntityType.CHICKEN)
            .add(EntityType.TURTLE);
        this.getOrCreateTagBuilder(FPEntityTypeTags.HAWK_BABY_HUNT_TARGETS)
            .add(EntityType.CHICKEN)
            .add(EntityType.RABBIT)
            .add(FPEntityTypes.BLUE_JAY.get())
            .add(FPEntityTypes.CARDINAL.get())
            .add(FPEntityTypes.CHICKADEE.get())
            .add(FPEntityTypes.CROW.get())
            .add(FPEntityTypes.DUCK.get())
            .add(FPEntityTypes.GOOSE.get())
            .add(FPEntityTypes.GULL.get())
            .add(FPEntityTypes.PIGEON.get())
            .add(FPEntityTypes.RAVEN.get())
            .add(FPEntityTypes.ROBIN.get())
            .add(FPEntityTypes.SPARROW.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.RAVEN_BABY_HUNT_TARGETS)
            .add(EntityType.CHICKEN)
            .add(EntityType.RABBIT)
            .add(FPEntityTypes.BLUE_JAY.get())
            .add(FPEntityTypes.CARDINAL.get())
            .add(FPEntityTypes.CHICKADEE.get())
            .add(FPEntityTypes.CROW.get())
            .add(FPEntityTypes.DUCK.get())
            .add(FPEntityTypes.GOOSE.get())
            .add(FPEntityTypes.GULL.get())
            .add(FPEntityTypes.HAWK.get())
            .add(FPEntityTypes.PIGEON.get())
            .add(FPEntityTypes.ROBIN.get())
            .add(FPEntityTypes.SPARROW.get());

        // Entities to attack
        this.getOrCreateTagBuilder(FPEntityTypeTags.CROW_ATTACK_TARGETS)
            .add(FPEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FPEntityTypeTags.RAVEN_ATTACK_TARGETS)
            .add(FPEntityTypes.HAWK.get());

        // Vanilla entity tags
        this.getOrCreateTagBuilder(EntityTypeTags.AQUATIC)
            .setReplace(false)
            .add(FPEntityTypes.PENGUIN.get());
        this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
            .setReplace(false)
            .add(FPEntityTypes.PENGUIN.get());
        this.getOrCreateTagBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)
            .setReplace(false)
            .add(FPEntityTypes.PENGUIN.get());
    }
}
//?}