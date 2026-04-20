package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;

public class Sleep<E extends BirdEntity> extends Idle<E> {
    @Override
    protected void start(E entity) {
        entity.goToSleep();
        entity.getNavigation().stop();
    }

    @Override
    protected void stop(E entity) {
        entity.wakeUp();
    }
}
