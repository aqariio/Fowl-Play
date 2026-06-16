package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FPSoundEvents;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.datagen.provider.SoundDefinition;
import aqario.fowlplay.datagen.provider.SoundDefinitionsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FowlPlaySoundDefinitionsGen extends SoundDefinitionsProvider {
    protected FowlPlaySoundDefinitionsGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, FowlPlay.ID, registryLookup);
    }

    @Override
    public void generateSounds() {
        this.addVariousVanilla(FPSoundEvents.BIRD_EAT.get(), "mob/parrot/eat", 3);
        this.addVariousVanilla(FPSoundEvents.BIRD_FLAP.get(), "mob/parrot/fly", 8);

        this.addBird(FPSoundEvents.BLUE_JAY_CALL, "call", 6);
        this.addBird(FPSoundEvents.BLUE_JAY_HURT, "call", 6);

        this.addBird(FPSoundEvents.CARDINAL_CALL, "call", 2);
        this.addBird(FPSoundEvents.CARDINAL_SONG, "song", 8);
        this.addBird(FPSoundEvents.CARDINAL_HURT, "call", 2);

        this.addBird(FPSoundEvents.CHICKADEE_CALL, "call", 9);
        this.addBird(FPSoundEvents.CHICKADEE_SONG, "song", 6);
        this.addBird(FPSoundEvents.CHICKADEE_HURT, "call", 2);

        this.addBird(FPSoundEvents.CROW_CALL, "call", 4);
        this.addBird(FPSoundEvents.CROW_HURT, "hurt", 2);

        this.addBird(FPSoundEvents.DUCK_CALL, "call", 2);
        this.addBird(FPSoundEvents.DUCK_HURT, "call", 2);

        this.addBird(FPSoundEvents.CANADA_GOOSE_CALL, "call", 8);
        this.addBird(FPSoundEvents.CANADA_GOOSE_HURT, "call", 8);
        this.addBird(FPSoundEvents.GREYLAG_GOOSE_CALL, "call", 4);
        this.addBird(FPSoundEvents.GREYLAG_GOOSE_HURT, "call", 4);
        this.addBird(FPSoundEvents.SWAN_GOOSE_CALL, "call", 4);
        this.addBird(FPSoundEvents.SWAN_GOOSE_HURT, "call", 4);

        this.addBird(FPSoundEvents.GULL_CALL, "call", 4);
        this.addBird(FPSoundEvents.GULL_LONG_CALL, "long_call", 3);
        this.addBird(FPSoundEvents.GULL_HURT, "call", 2);

        this.addBird(FPSoundEvents.HAWK_CALL, "call", 5);
        this.addBird(FPSoundEvents.HAWK_HURT, "call", 2);

        this.addBird(FPSoundEvents.PENGUIN_CALL, "call", 3);
        this.addBird(FPSoundEvents.PENGUIN_BABY_CALL, "call", 1);
        this.addBird(FPSoundEvents.PENGUIN_SWIM, "swim/swim", 5);
        this.addBird(FPSoundEvents.PENGUIN_HURT, "hurt", 1);

        this.addBird(FPSoundEvents.PIGEON_CALL, "call", 3);
        this.addBird(FPSoundEvents.PIGEON_SONG, "song", 1);
        this.addBird(FPSoundEvents.PIGEON_HURT, "call", 3);

        this.addBird(FPSoundEvents.RAVEN_CALL, "call", 4);
        this.addBird(FPSoundEvents.RAVEN_HURT, "hurt", 2);

        this.addBird(FPSoundEvents.ROBIN_CALL, "call", 6);
        this.addBird(FPSoundEvents.ROBIN_SONG, "song", 4);
        this.addBird(FPSoundEvents.ROBIN_HURT, "hurt", 2);

        this.addBird(FPSoundEvents.SPARROW_CALL, "call", 5);
        this.addBird(FPSoundEvents.SPARROW_SONG, "song", 4);
        this.addBird(FPSoundEvents.SPARROW_HURT, "call", 3, 5);
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

    private void addBird(Supplier<SoundEvent> soundEvent, String name, int variations) {
        this.addBird(soundEvent, name, 1, variations);
    }

    private void addBird(Supplier<SoundEvent> soundEvent, String name, int rangeStart, int rangeEnd) {
        ResourceLocation sound = soundEvent.get().getLocation();
        String location = sound.getPath()
            .replace("entity", "mob")
            .replace(".", "/");
        location = location.substring(0, location.lastIndexOf('/') + 1) + name;
        SoundDefinition definition = SoundDefinition.builder();
        for(int i = rangeStart; i <= rangeEnd; i++) {
            definition.with(sound(FowlPlay.id(location + i))
                .attenuationDistance((int) soundEvent.get().getRange(1)));
        }
        definition.subtitle("subtitles." + sound.getPath());
        this.add(soundEvent, definition);
    }
}
