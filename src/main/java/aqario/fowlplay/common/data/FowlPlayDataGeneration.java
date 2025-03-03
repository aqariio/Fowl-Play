package aqario.fowlplay.common.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FowlPlayDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
//        pack.addProvider(FowlPlayLootTableGenerator::new);
//        pack.addProvider(FowlPlayBiomeTagGenerator::new);
    }
}
