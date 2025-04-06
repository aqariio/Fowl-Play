package aqario.fowlplay.datagen.provider;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

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

    protected SoundDefinitionsProvider(FabricDataOutput output, String namespace, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this.output = output;
        this.namespace = namespace;
    }

    public abstract void generateSounds();

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        this.soundDefinitions.clear();
        this.generateSounds();
        if (this.soundDefinitions.isEmpty()) {
            return CompletableFuture.allOf();
        }
        return this.writeJson(writer, this.output.resolvePath(DataOutput.OutputType.RESOURCE_PACK).resolve(this.namespace).resolve("sounds.json"));
    }

    @Override
    public String getName() {
        return "Sound Definitions";
    }

    protected static SoundDefinition.Sound sound(Identifier name, SoundDefinition.SoundType type) {
        return SoundDefinition.Sound.sound(name, type);
    }

    protected static SoundDefinition.Sound sound(Identifier name) {
        return sound(name, SoundDefinition.SoundType.SOUND);
    }

    protected static SoundDefinition.Sound sound(String name, SoundDefinition.SoundType type) {
        return sound(Identifier.tryParse(name), type);
    }

    protected static SoundDefinition.Sound sound(String name) {
        return sound(Identifier.tryParse(name));
    }

    protected void add(Supplier<SoundEvent> soundEvent, SoundDefinition definition) {
        this.add(soundEvent.get(), definition);
    }

    protected void add(SoundEvent soundEvent, SoundDefinition definition) {
        this.add(soundEvent.getId(), definition);
    }

    protected void add(Identifier soundEvent, SoundDefinition definition) {
        this.addSounds(soundEvent, definition);
    }

    protected void add(String soundEvent, SoundDefinition definition) {
        this.add(Identifier.tryParse(soundEvent), definition);
    }

    private void addSounds(Identifier soundEvent, SoundDefinition definition) {
        if (this.soundDefinitions.put(soundEvent.getPath(), definition) != null) {
            throw new IllegalStateException("Sound event '" + soundEvent + "' already exists");
        }
    }

    private CompletableFuture<?> writeJson(DataWriter cache, Path path) {
        return DataProvider.writeToPath(cache, this.mapToJson(this.soundDefinitions), path);
    }

    private JsonObject mapToJson(Map<String, SoundDefinition> map) {
        JsonObject json = new JsonObject();
        map.forEach((sound, definition) -> json.add(sound, definition.serialize()));
        return json;
    }
}
