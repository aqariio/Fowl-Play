package aqario.fowlplay.loaders.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FPDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(FPAdvancementGen::new);
        pack.addProvider(FPBiomeTagGen::new);
        pack.addProvider(FPBlockTagGen::new);
        pack.addProvider(FPEntityLootTableGen::new);
        pack.addProvider(FPEntityTypeTagGen::new);
        pack.addProvider(FPItemTagGen::new);
        pack.addProvider(FPModelGen::new);
        pack.addProvider(FPRecipeGen::new);
        pack.addProvider(FPSoundDefinitionsGen::new);
    }
}
