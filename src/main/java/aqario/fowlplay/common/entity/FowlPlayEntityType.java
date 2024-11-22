package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.FowlPlay;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class FowlPlayEntityType {
    public static final EntityType<BlueJayEntity> BLUE_JAY = register("blue_jay",
        FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, BlueJayEntity::new)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    public static final EntityType<CardinalEntity> CARDINAL = register("cardinal",
        FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, CardinalEntity::new)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    public static final EntityType<ChickadeeEntity> CHICKADEE = register("chickadee",
        FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, ChickadeeEntity::new)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    public static final EntityType<DuckEntity> DUCK = register("duck",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DuckEntity::new)
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
    );

    public static final EntityType<GullEntity> GULL = register("gull",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GullEntity::new)
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
    );

    public static final EntityType<PenguinEntity> PENGUIN = register("penguin",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PenguinEntity::new)
            .dimensions(EntityDimensions.changing(0.5f, 1.4f))
    );

    public static final EntityType<PigeonEntity> PIGEON = register("pigeon",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PigeonEntity::new)
            .dimensions(EntityDimensions.changing(0.5f, 0.6f))
    );

    public static final EntityType<RavenEntity> RAVEN = register("raven",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RavenEntity::new)
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
    );

    public static final EntityType<RobinEntity> ROBIN = register("robin",
        FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, RobinEntity::new)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    public static final EntityType<SparrowEntity> SPARROW = register("sparrow",
        FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, SparrowEntity::new)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(FowlPlay.ID, id), builder.build());
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(BLUE_JAY, BlueJayEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CARDINAL, CardinalEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CHICKADEE, ChickadeeEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(DUCK, DuckEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(GULL, GullEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(PENGUIN, PenguinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(PIGEON, PigeonEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(RAVEN, RavenEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ROBIN, RobinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SPARROW, SparrowEntity.createAttributes());
    }
}
