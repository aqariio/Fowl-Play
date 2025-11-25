package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayEntityTypes;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class FowlPlayEntityTypeTagGen extends FabricTagProvider.EntityTypeTagProvider {
    private static final ResourceLocation ANCIENTSCALE = ResourceLocation.fromNamespaceAndPath("fishofthieves", "ancientscale");
    private static final ResourceLocation BATTLEGILL = ResourceLocation.fromNamespaceAndPath("fishofthieves", "battlegill");
    private static final ResourceLocation DEVILFISH = ResourceLocation.fromNamespaceAndPath("fishofthieves", "devilfish");
    private static final ResourceLocation ISLEHOPPER = ResourceLocation.fromNamespaceAndPath("fishofthieves", "islehopper");
    private static final ResourceLocation PLENTIFIN = ResourceLocation.fromNamespaceAndPath("fishofthieves", "plentifin");
    private static final ResourceLocation PONDIE = ResourceLocation.fromNamespaceAndPath("fishofthieves", "pondie");
    private static final ResourceLocation SPLASHTAIL = ResourceLocation.fromNamespaceAndPath("fishofthieves", "splashtail");
    private static final ResourceLocation STORMFISH = ResourceLocation.fromNamespaceAndPath("fishofthieves", "stormfish");
    private static final ResourceLocation WILDSPLASH = ResourceLocation.fromNamespaceAndPath("fishofthieves", "wildsplash");

    public FowlPlayEntityTypeTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        // Birds
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.BIRDS)
            .add(EntityType.CHICKEN)
            .add(EntityType.PARROT)
            .add(FowlPlayEntityTypes.BLUE_JAY.get())
            .add(FowlPlayEntityTypes.CARDINAL.get())
            .add(FowlPlayEntityTypes.CHICKADEE.get())
            .add(FowlPlayEntityTypes.CROW.get())
            .add(FowlPlayEntityTypes.DUCK.get())
            .add(FowlPlayEntityTypes.GOOSE.get())
            .add(FowlPlayEntityTypes.GULL.get())
            .add(FowlPlayEntityTypes.HAWK.get())
            .add(FowlPlayEntityTypes.PENGUIN.get())
            .add(FowlPlayEntityTypes.PIGEON.get())
            .add(FowlPlayEntityTypes.RAVEN.get())
            .add(FowlPlayEntityTypes.ROBIN.get())
            .add(FowlPlayEntityTypes.SPARROW.get());

        // Flightless
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.FLIGHTLESS)
            .add(FowlPlayEntityTypes.PENGUIN.get());

        // Passerines
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.PASSERINES)
            .add(FowlPlayEntityTypes.BLUE_JAY.get())
            .add(FowlPlayEntityTypes.CARDINAL.get())
            .add(FowlPlayEntityTypes.CHICKADEE.get())
            .add(FowlPlayEntityTypes.CROW.get())
            .add(FowlPlayEntityTypes.RAVEN.get())
            .add(FowlPlayEntityTypes.ROBIN.get())
            .add(FowlPlayEntityTypes.SPARROW.get());

        // Seabirds
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.SEABIRDS)
            .add(FowlPlayEntityTypes.GULL.get());

        // Waterbirds
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.WATERBIRDS)
            .add(FowlPlayEntityTypes.DUCK.get())
            .add(FowlPlayEntityTypes.GOOSE.get())
            .add(FowlPlayEntityTypes.GULL.get())
            .add(FowlPlayEntityTypes.PENGUIN.get());

        // Waterfowl
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.WATERFOWL)
            .add(FowlPlayEntityTypes.DUCK.get())
            .add(FowlPlayEntityTypes.GOOSE.get());

        // Entities to avoid
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.BLUE_JAY_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.CARDINAL_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.CHICKADEE_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.CROW_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.DUCK_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.GOOSE_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.GULL_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.HAWK_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.PENGUIN_AVOIDS)
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.PIGEON_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.RAVEN_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.ROBIN_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.SPARROW_AVOIDS)
            .add(EntityType.PLAYER)
            .add(FowlPlayEntityTypes.SCARECROW.get())
            .add(FowlPlayEntityTypes.HAWK.get());

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
            .add(FowlPlayEntityTypes.PIGEON.get())
            .add(FowlPlayEntityTypes.SPARROW.get());
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
            .add(FowlPlayEntityTypes.BLUE_JAY.get())
            .add(FowlPlayEntityTypes.CARDINAL.get())
            .add(FowlPlayEntityTypes.CHICKADEE.get())
            .add(FowlPlayEntityTypes.CROW.get())
            .add(FowlPlayEntityTypes.DUCK.get())
            .add(FowlPlayEntityTypes.GOOSE.get())
            .add(FowlPlayEntityTypes.GULL.get())
            .add(FowlPlayEntityTypes.PIGEON.get())
            .add(FowlPlayEntityTypes.RAVEN.get())
            .add(FowlPlayEntityTypes.ROBIN.get())
            .add(FowlPlayEntityTypes.SPARROW.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.RAVEN_BABY_HUNT_TARGETS)
            .add(EntityType.CHICKEN)
            .add(EntityType.RABBIT)
            .add(FowlPlayEntityTypes.BLUE_JAY.get())
            .add(FowlPlayEntityTypes.CARDINAL.get())
            .add(FowlPlayEntityTypes.CHICKADEE.get())
            .add(FowlPlayEntityTypes.CROW.get())
            .add(FowlPlayEntityTypes.DUCK.get())
            .add(FowlPlayEntityTypes.GOOSE.get())
            .add(FowlPlayEntityTypes.GULL.get())
            .add(FowlPlayEntityTypes.HAWK.get())
            .add(FowlPlayEntityTypes.PIGEON.get())
            .add(FowlPlayEntityTypes.ROBIN.get())
            .add(FowlPlayEntityTypes.SPARROW.get());

        // Entities to attack
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.CROW_ATTACK_TARGETS)
            .add(FowlPlayEntityTypes.HAWK.get());
        this.getOrCreateTagBuilder(FowlPlayEntityTypeTags.RAVEN_ATTACK_TARGETS)
            .add(FowlPlayEntityTypes.HAWK.get());

        // Vanilla entity tags
        this.getOrCreateTagBuilder(EntityTypeTags.AQUATIC)
            .setReplace(false)
            .add(FowlPlayEntityTypes.PENGUIN.get());
        this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
            .setReplace(false)
            .add(FowlPlayEntityTypes.PENGUIN.get());
        this.getOrCreateTagBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)
            .setReplace(false)
            .add(FowlPlayEntityTypes.PENGUIN.get());
    }
}
