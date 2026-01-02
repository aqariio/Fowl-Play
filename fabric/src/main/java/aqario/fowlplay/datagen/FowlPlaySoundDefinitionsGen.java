package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.datagen.provider.SoundDefinition;
import aqario.fowlplay.datagen.provider.SoundDefinitionsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.concurrent.CompletableFuture;

public class FowlPlaySoundDefinitionsGen extends SoundDefinitionsProvider {
    protected FowlPlaySoundDefinitionsGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, FowlPlay.ID, registryLookup);
    }

    @Override
    public void generateSounds() {
        this.addVariousVanilla(FowlPlaySoundEvents.ENTITY_BIRD_EAT.get(), "mob/parrot/eat", 3);
        this.addVariousVanilla(FowlPlaySoundEvents.ENTITY_BIRD_FLAP.get(), "mob/parrot/fly", 8);

        this.addVarious(FowlPlaySoundEvents.ENTITY_BLUE_JAY_CALL.get(), "mob/blue_jay/call", 6);
        this.addVarious(FowlPlaySoundEvents.ENTITY_BLUE_JAY_HURT.get(), "mob/blue_jay/call", 6);

        this.addVarious(FowlPlaySoundEvents.ENTITY_CARDINAL_CALL.get(), "mob/cardinal/call", 2);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CARDINAL_SONG.get(), "mob/cardinal/song", 8);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CARDINAL_HURT.get(), "mob/cardinal/call", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_CHICKADEE_CALL.get(), "mob/chickadee/call", 9);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CHICKADEE_SONG.get(), "mob/chickadee/song", 6);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CHICKADEE_HURT.get(), "mob/chickadee/call", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_CROW_CALL.get(), "mob/crow/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CROW_HURT.get(), "mob/crow/hurt", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_DUCK_CALL.get(), "mob/duck/call", 2);
        this.addVarious(FowlPlaySoundEvents.ENTITY_DUCK_HURT.get(), "mob/duck/call", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_CANADA_GOOSE_CALL.get(), "mob/goose/canada/call", 8);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CANADA_GOOSE_HURT.get(), "mob/goose/canada/call", 8);
        this.addVarious(FowlPlaySoundEvents.ENTITY_GREYLAG_GOOSE_CALL.get(), "mob/goose/greylag/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_GREYLAG_GOOSE_HURT.get(), "mob/goose/greylag/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_SWAN_GOOSE_CALL.get(), "mob/goose/greylag/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_SWAN_GOOSE_HURT.get(), "mob/goose/greylag/call", 4);

        this.addVarious(FowlPlaySoundEvents.ENTITY_GULL_CALL.get(), "mob/gull/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_GULL_LONG_CALL.get(), "mob/gull/long_call", 3);
        this.addVarious(FowlPlaySoundEvents.ENTITY_GULL_HURT.get(), "mob/gull/hurt", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_HAWK_CALL.get(), "mob/hawk/call", 5);
        this.addVarious(FowlPlaySoundEvents.ENTITY_HAWK_HURT.get(), "mob/hawk/call", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_PENGUIN_CALL.get(), "mob/penguin/call", 3);
        this.addOne(FowlPlaySoundEvents.ENTITY_PENGUIN_BABY_CALL.get(), "mob/penguin/baby_call");
        this.addVarious(FowlPlaySoundEvents.ENTITY_PENGUIN_SWIM.get(), "mob/penguin/swim/swim", 5);
        this.addOne(FowlPlaySoundEvents.ENTITY_PENGUIN_HURT.get(), "mob/penguin/hurt");

        this.addVarious(FowlPlaySoundEvents.ENTITY_PIGEON_CALL.get(), "mob/pigeon/call", 3);
        this.addVarious(FowlPlaySoundEvents.ENTITY_PIGEON_SONG.get(), "mob/pigeon/song", 1);
        this.addVarious(FowlPlaySoundEvents.ENTITY_PIGEON_HURT.get(), "mob/pigeon/call", 3);

        this.addVarious(FowlPlaySoundEvents.ENTITY_RAVEN_CALL.get(), "mob/raven/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_RAVEN_HURT.get(), "mob/raven/hurt", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_ROBIN_CALL.get(), "mob/robin/call", 6);
        this.addVarious(FowlPlaySoundEvents.ENTITY_ROBIN_SONG.get(), "mob/robin/song", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_ROBIN_HURT.get(), "mob/robin/hurt", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_SPARROW_CALL.get(), "mob/sparrow/call", 5);
        this.addVarious(FowlPlaySoundEvents.ENTITY_SPARROW_SONG.get(), "mob/sparrow/song", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_SPARROW_HURT.get(), "mob/sparrow/call", 3, 5);
    }

    private void addVarious(SoundEvent soundEvent, String location, int start, int end) {
        SoundDefinition definition = SoundDefinition.builder();
        for(int i = start; i <= end; i++) {
            definition.with(sound(FowlPlay.id(location + i)));
        }
        definition.subtitle("subtitles." + soundEvent.getLocation().getPath());
        this.add(soundEvent, definition);
    }

    private void addVarious(SoundEvent soundEvent, String location, int variations) {
        this.addVarious(soundEvent, location, 1, variations);
    }

    private void addOne(SoundEvent soundEvent, String location) {
        SoundDefinition definition = SoundDefinition.builder()
            .with(sound(FowlPlay.id(location)))
            .subtitle("subtitles." + soundEvent.getLocation().getPath());
        this.add(soundEvent, definition);
    }

    private void addVariousVanilla(SoundEvent soundEvent, String location, int start, int end) {
        SoundDefinition definition = SoundDefinition.builder();
        for(int i = start; i <= end; i++) {
            definition.with(sound(ResourceLocation.withDefaultNamespace(location + i)));
        }
        definition.subtitle("subtitles." + soundEvent.getLocation().getPath());
        this.add(soundEvent, definition);
    }

    private void addVariousVanilla(SoundEvent soundEvent, String location, int variations) {
        this.addVariousVanilla(soundEvent, location, 1, variations);
    }

    private void addVanilla(SoundEvent soundEvent, String location) {
        SoundDefinition definition = SoundDefinition.builder()
            .with(sound(ResourceLocation.withDefaultNamespace(location)))
            .subtitle("subtitles." + soundEvent.getLocation().getPath());
        this.add(soundEvent, definition);
    }
}
