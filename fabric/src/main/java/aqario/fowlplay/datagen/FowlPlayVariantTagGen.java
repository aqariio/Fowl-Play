package aqario.fowlplay.datagen;

import aqario.fowlplay.common.entity.DuckVariant;
import aqario.fowlplay.common.entity.GooseVariant;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.tags.FowlPlayVariantTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;

public abstract class FowlPlayVariantTagGen<T> extends FabricTagProvider<T> {
    public FowlPlayVariantTagGen(FabricDataOutput output, ResourceKey<? extends Registry<T>> registry, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, registry, completableFuture);
    }

    public static class Duck extends FowlPlayVariantTagGen<DuckVariant> {
        public Duck(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, FowlPlayRegistries.DUCK_VARIANT, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
            this.getOrCreateTagBuilder(FowlPlayVariantTags.Duck.NATURAL)
                .add(DuckVariant.GREEN_HEADED)
                .add(DuckVariant.BROWN);
            this.getOrCreateTagBuilder(FowlPlayVariantTags.Duck.DOMESTIC)
                .add(DuckVariant.PEKIN);
        }
    }

    public static class Goose extends FowlPlayVariantTagGen<GooseVariant> {
        public Goose(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, FowlPlayRegistries.GOOSE_VARIANT, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
            this.getOrCreateTagBuilder(FowlPlayVariantTags.Goose.NATURAL)
                .add(GooseVariant.GREYLAG)
                .add(GooseVariant.CANADA)
                .add(GooseVariant.SWAN);
            this.getOrCreateTagBuilder(FowlPlayVariantTags.Goose.DOMESTIC)
                .add(GooseVariant.EMDEN)
                .add(GooseVariant.CHINESE);
        }
    }
}
