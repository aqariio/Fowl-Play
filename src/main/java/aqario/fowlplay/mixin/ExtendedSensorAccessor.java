package aqario.fowlplay.mixin;

import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExtendedSensor.class)
public interface ExtendedSensorAccessor {
    @Accessor("nextTickTime")
    void fowlplay$setNextTickTime(long value);
}
