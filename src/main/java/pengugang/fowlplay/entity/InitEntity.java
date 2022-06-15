package pengugang.fowlplay.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pengugang.fowlplay.FowlPlay;

public class InitEntity {
    public static final EntityType<PenguinEntity> PENGUIN = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(FowlPlay.MODID, "penguin"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PenguinEntity::new)
                    .dimensions(EntityDimensions.changing(0.6f, 1.4f)).build());

    public static final EntityType<SeagullEntity> SEAGULL = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(FowlPlay.MODID, "seagull"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SeagullEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 0.8f)).build());
}
