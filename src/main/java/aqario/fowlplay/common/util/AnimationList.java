package aqario.fowlplay.common.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class AnimationList implements Iterable<RawAnimation> {
    private final List<Entry> entries;
    private final RandomSource random = RandomSource.createNewThreadLocalInstance();

    public AnimationList() {
        this.entries = new ObjectArrayList<>();
    }

    public AnimationList with(RawAnimation animation, int weight) {
        Objects.requireNonNull(animation);
        this.entries.add(new Entry(animation, weight));
        return this;
    }

    public void randomize() {
        this.entries.forEach(entry -> entry.randomizeWeight(this.random.nextFloat()));
        this.entries.sort(Comparator.comparingDouble(Entry::getRandomizedWeight));
    }

    @NotNull
    public RawAnimation getRandom() {
        if(this.entries.isEmpty()) {
            throw new IllegalStateException("No animations in animation list");
        }
        this.randomize();
        return this.entries.getFirst().getAnimation();
    }

    public int size() {
        return this.entries.size();
    }

    @Override
    public void forEach(Consumer<? super RawAnimation> action) {
        this.entries.forEach(entry -> {
            if(entry.getAnimation() != null) {
                action.accept(entry.getAnimation());
            }
        });
    }

    @NotNull
    @Override
    public Iterator<RawAnimation> iterator() {
        return new ObjectIterators.AbstractIndexBasedIterator<>(0, 0) {
            @Override
            protected RawAnimation get(int location) {
                return AnimationList.this.entries.get(location).getAnimation();
            }

            @Override
            protected void remove(int location) {
                AnimationList.this.entries.remove(location);
            }

            @Override
            protected int getMaxPos() {
                return AnimationList.this.entries.size();
            }
        };
    }

    public static class Entry {
        private final RawAnimation animation;
        private final int weight;
        private double randomizedWeight;

        protected Entry(RawAnimation animation, int weight) {
            this.animation = animation;
            this.weight = weight;
        }

        protected double getRandomizedWeight() {
            return this.randomizedWeight;
        }

        protected RawAnimation getAnimation() {
            return this.animation;
        }

        protected int getWeight() {
            return this.weight;
        }

        protected void randomizeWeight(float mod) {
            this.randomizedWeight = -Math.pow(mod, 1f / this.weight);
        }

        @Override
        public String toString() {
            return this.animation + ":" + this.weight;
        }
    }
}
