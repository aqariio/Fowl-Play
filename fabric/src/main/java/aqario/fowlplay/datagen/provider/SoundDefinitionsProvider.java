package aqario.fowlplay.datagen.provider;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

// Reimplementation of NeoForge's SoundDefinitionsProvider
public abstract class SoundDefinitionsProvider implements DataProvider {
    private final FabricDataOutput output;
    private final String namespace;
    private final Map<String, SoundDefinition> soundDefinitions = new LinkedHashMap<>();

    protected SoundDefinitionsProvider(FabricDataOutput output, String namespace, CompletableFuture<HolderLookup.Provider> registryLookup) {
        this.output = output;
        this.namespace = namespace;
    }

    public abstract void generateSounds();

    @Override
    public CompletableFuture<?> run(CachedOutput writer) {
        this.soundDefinitions.clear();
        this.generateSounds();
        if (this.soundDefinitions.isEmpty()) {
            return CompletableFuture.allOf();
        }
        return this.writeJson(writer, this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(this.namespace).resolve("sounds.json"));
    }

    @Override
    public String getName() {
        return "Sound Definitions";
    }

    protected static SoundDefinition.Sound sound(ResourceLocation name, SoundDefinition.SoundType type) {
        return SoundDefinition.Sound.sound(name, type);
    }

    protected static SoundDefinition.Sound sound(ResourceLocation name) {
        return sound(name, SoundDefinition.SoundType.SOUND);
    }

    protected static SoundDefinition.Sound sound(String name, SoundDefinition.SoundType type) {
        return sound(ResourceLocation.tryParse(name), type);
    }

    protected static SoundDefinition.Sound sound(String name) {
        return sound(ResourceLocation.tryParse(name));
    }

    protected void add(Supplier<SoundEvent> soundEvent, SoundDefinition definition) {
        this.add(soundEvent.get(), definition);
    }

    protected void add(SoundEvent soundEvent, SoundDefinition definition) {
        this.add(soundEvent.getLocation(), definition);
    }

    protected void add(ResourceLocation soundEvent, SoundDefinition definition) {
        this.addSounds(soundEvent, definition);
    }

    protected void add(String soundEvent, SoundDefinition definition) {
        this.add(ResourceLocation.tryParse(soundEvent), definition);
    }

    private void addSounds(ResourceLocation soundEvent, SoundDefinition definition) {
        if (this.soundDefinitions.put(soundEvent.getPath(), definition) != null) {
            throw new IllegalStateException("Sound event '" + soundEvent + "' already exists");
        }
    }

    private CompletableFuture<?> writeJson(CachedOutput cache, Path path) {
        return DataProvider.saveStable(cache, this.mapToJson(this.soundDefinitions), path);
    }

    private JsonObject mapToJson(Map<String, SoundDefinition> map) {
        JsonObject json = new JsonObject();
        map.forEach((sound, definition) -> json.add(sound, definition.serialize()));
        return json;
    }
}
