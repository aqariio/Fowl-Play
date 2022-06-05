package pengugang.foulplay.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pengugang.foulplay.FoulPlay;

public class InitSound {





    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(FoulPlay.MODID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
