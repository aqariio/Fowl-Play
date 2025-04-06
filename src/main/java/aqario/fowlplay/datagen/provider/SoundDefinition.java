package aqario.fowlplay.datagen.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SoundDefinition {
    private final List<Sound> sounds = new ArrayList<>();
    private boolean replace = false;
    private String subtitle = null;

    public static SoundDefinition builder() {
        return new SoundDefinition();
    }

    public SoundDefinition replace(boolean replace) {
        this.replace = replace;
        return this;
    }

    public SoundDefinition subtitle(@Nullable String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public SoundDefinition with(Sound sound) {
        this.sounds.add(sound);
        return this;
    }

    public SoundDefinition with(Sound... sounds) {
        this.sounds.addAll(Arrays.asList(sounds));
        return this;
    }

    List<Sound> sounds() {
        return this.sounds;
    }

    JsonObject serialize() {
        if (this.sounds.isEmpty()) {
            throw new IllegalStateException("Unable to serialize a sound definition that has no sounds!");
        }
        JsonObject json = new JsonObject();
        if (this.replace) {
            json.addProperty("replace", true);
        }

        if (this.subtitle != null) {
            json.addProperty("subtitle", this.subtitle);
        }

        JsonArray sounds = new JsonArray();
        this.sounds.stream().map(Sound::serialize).forEach(sounds::add);
        json.add("sounds", sounds);
        return json;
    }

    public static final class Sound {
        private static final SoundType DEFAULT_TYPE = SoundDefinition.SoundType.SOUND;
        private static final float DEFAULT_VOLUME = 1.0F;
        private static final float DEFAULT_PITCH = 1.0F;
        private static final int DEFAULT_WEIGHT = 1;
        private static final boolean DEFAULT_STREAM = false;
        private static final int DEFAULT_ATTENUATION_DISTANCE = 16;
        private static final boolean DEFAULT_PRELOAD = false;
        private final Identifier name;
        private final SoundType type;
        private float volume = DEFAULT_VOLUME;
        private float pitch = DEFAULT_PITCH;
        private int weight = DEFAULT_WEIGHT;
        private boolean stream = DEFAULT_STREAM;
        private int attenuationDistance = DEFAULT_ATTENUATION_DISTANCE;
        private boolean preload = DEFAULT_PRELOAD;

        private Sound(Identifier name, SoundType type) {
            this.name = name;
            this.type = type;
        }

        public static Sound sound(Identifier name, SoundType type) {
            return new Sound(name, type);
        }

        public Sound volume(double volume) {
            return this.volume((float) volume);
        }

        public Sound volume(float volume) {
            if (volume <= 0.0F) {
                throw new IllegalArgumentException("Volume must be positive for sound " + this.name + ", but instead got " + volume);
            }
            this.volume = volume;
            return this;
        }

        public Sound pitch(double pitch) {
            return this.pitch((float) pitch);
        }

        public Sound pitch(float pitch) {
            if (pitch <= 0.0F) {
                throw new IllegalArgumentException("Pitch must be positive for sound " + this.name + ", but instead got " + pitch);
            }
            this.pitch = pitch;
            return this;
        }

        public Sound weight(int weight) {
            if (weight <= 0) {
                throw new IllegalArgumentException("Weight has to be a positive number in sound " + this.name + ", but instead got " + weight);
            }
            this.weight = weight;
            return this;
        }

        public Sound stream() {
            return this.stream(true);
        }

        public Sound stream(boolean stream) {
            this.stream = stream;
            return this;
        }

        public Sound attenuationDistance(int attenuationDistance) {
            this.attenuationDistance = attenuationDistance;
            return this;
        }

        public Sound preload() {
            return this.preload(true);
        }

        public Sound preload(boolean preload) {
            this.preload = preload;
            return this;
        }

        Identifier name() {
            return this.name;
        }

        SoundType type() {
            return this.type;
        }

        JsonElement serialize() {
            if (this.doesNotNeedObject()) {
                return new JsonPrimitive(this.stripVanillaNamespace(this.name));
            }
            JsonObject json = new JsonObject();
            json.addProperty("name", this.stripVanillaNamespace(this.name));
            if (this.type != DEFAULT_TYPE) {
                json.addProperty("type", this.type.jsonString);
            }

            if (this.volume != DEFAULT_VOLUME) {
                json.addProperty("volume", this.volume);
            }

            if (this.pitch != DEFAULT_PITCH) {
                json.addProperty("pitch", this.pitch);
            }

            if (this.weight != DEFAULT_WEIGHT) {
                json.addProperty("weight", this.weight);
            }

            if (this.stream != DEFAULT_STREAM) {
                json.addProperty("stream", this.stream);
            }

            if (this.preload != DEFAULT_PRELOAD) {
                json.addProperty("preload", this.preload);
            }

            if (this.attenuationDistance != DEFAULT_ATTENUATION_DISTANCE) {
                json.addProperty("attenuation_distance", this.attenuationDistance);
            }

            return json;
        }

        private boolean doesNotNeedObject() {
            return this.type == DEFAULT_TYPE
                && this.volume == DEFAULT_VOLUME
                && this.pitch == DEFAULT_PITCH
                && this.weight == DEFAULT_WEIGHT
                && this.stream == DEFAULT_STREAM
                && this.attenuationDistance == DEFAULT_ATTENUATION_DISTANCE
                && this.preload == DEFAULT_PRELOAD;
        }

        private String stripVanillaNamespace(Identifier name) {
            return "minecraft".equals(name.getNamespace()) ? name.getPath() : name.toString();
        }
    }

    public enum SoundType {
        SOUND("sound"),
        EVENT("event");

        private final String jsonString;

        SoundType(final String jsonString) {
            this.jsonString = jsonString;
        }
    }
}
