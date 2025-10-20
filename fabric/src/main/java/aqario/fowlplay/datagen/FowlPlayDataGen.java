package aqario.fowlplay.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FowlPlayDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(FowlPlayAdvancementGen::new);
        pack.addProvider(FowlPlayBiomeTagGen::new);
        pack.addProvider(FowlPlayBlockTagGen::new);
        pack.addProvider(FowlPlayEntityLootTableGen::new);
        pack.addProvider(FowlPlayEntityTypeTagGen::new);
        pack.addProvider(FowlPlayItemTagGen::new);
        pack.addProvider(FowlPlayModelGen::new);
        pack.addProvider(FowlPlayRecipeGen::new);
        pack.addProvider(FowlPlaySoundDefinitionsGen::new);
    }
}
