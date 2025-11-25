package aqario.fowlplay.common.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AnimationState;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class AnimationStateList implements Iterable<AnimationState> {
    private final List<Entry> entries;
    private final RandomSource random = RandomSource.createNewThreadLocalInstance();

    public AnimationStateList() {
        this.entries = new ObjectArrayList<>();
    }

    public AnimationStateList with(AnimationState entry, int weight) {
        this.entries.add(new Entry(entry, weight));
        return this;
    }

    public AnimationStateList randomize() {
        this.entries.forEach(entry -> entry.randomizeWeight(this.random.nextFloat()));
        this.entries.sort(Comparator.comparingDouble(Entry::getRandomizedWeight));
        return this;
    }

    public void startRandom(int tickCount) {
        this.randomize().getFirst()
            .ifPresent(animState -> animState.start(tickCount));
    }

    @NotNull
    public Optional<AnimationState> getFirst() {
        return Optional.ofNullable(this.entries.isEmpty() ? null : this.entries.getFirst())
            .map(Entry::getState);
    }

    public int size() {
        return this.entries.size();
    }

    public boolean containsStarted() {
        for(AnimationState state : this) {
            if(state != null && state.isStarted()) {
                return true;
            }
        }
        return false;
    }

    public void stopAll() {
        this.forEach(AnimationState::stop);
    }

    @Override
    public void forEach(Consumer<? super AnimationState> action) {
        this.entries.forEach(entry -> {
            if(entry.getState() != null) {
                action.accept(entry.getState());
            }
        });
    }

    @NotNull
    @Override
    public Iterator<AnimationState> iterator() {
        return new ObjectIterators.AbstractIndexBasedIterator<>(0, 0) {
            @Override
            protected AnimationState get(int location) {
                return AnimationStateList.this.entries.get(location).getState();
            }

            @Override
            protected void remove(int location) {
                AnimationStateList.this.entries.remove(location);
            }

            @Override
            protected int getMaxPos() {
                return AnimationStateList.this.entries.size();
            }
        };
    }

    public static class Entry {
        private final AnimationState state;
        private final int weight;
        private double randomizedWeight;

        protected Entry(AnimationState state, int weight) {
            this.state = state;
            this.weight = weight;
        }

        protected double getRandomizedWeight() {
            return this.randomizedWeight;
        }

        protected AnimationState getState() {
            return this.state;
        }

        protected int getWeight() {
            return this.weight;
        }

        protected void randomizeWeight(float mod) {
            this.randomizedWeight = -Math.pow(mod, 1f / this.weight);
        }

        @Override
        public String toString() {
            return this.state + ":" + this.weight;
        }
    }
}
