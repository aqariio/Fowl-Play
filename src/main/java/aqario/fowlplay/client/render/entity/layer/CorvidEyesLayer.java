package aqario.fowlplay.client.render.entity.layer;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import java.time.LocalDate;
import java.time.Month;

public class CorvidEyesLayer<T extends BirdEntity & GeoEntity> extends AutoGlowingGeoLayer<T> {
    public CorvidEyesLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    private boolean isHalloween() {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        Month month = today.getMonth();
        return month == Month.OCTOBER && day >= 20 || month == Month.NOVEMBER && day <= 3;
    }

    @Override
    protected @Nullable RenderType getRenderType(T bird, @Nullable MultiBufferSource bufferSource) {
        if(!this.isHalloween()) {
            return null;
        }
        return super.getRenderType(bird, bufferSource);
    }
}
