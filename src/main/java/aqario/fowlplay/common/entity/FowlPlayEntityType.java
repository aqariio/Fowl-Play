package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.FowlPlay;
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
        FabricEntityTypeBuilder.createMob()
            .entityFactory(BlueJayEntity::new)
            .spawnGroup(SpawnGroup.AMBIENT)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
            .defaultAttributes(BlueJayEntity::createAttributes)
    );

    public static final EntityType<CardinalEntity> CARDINAL = register("cardinal",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(CardinalEntity::new)
            .spawnGroup(SpawnGroup.AMBIENT)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
            .defaultAttributes(CardinalEntity::createAttributes)
    );

    public static final EntityType<ChickadeeEntity> CHICKADEE = register("chickadee",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(ChickadeeEntity::new)
            .spawnGroup(SpawnGroup.AMBIENT)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
            .defaultAttributes(ChickadeeEntity::createAttributes)
    );

    public static final EntityType<DuckEntity> DUCK = register("duck",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(DuckEntity::new)
            .spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
            .defaultAttributes(DuckEntity::createAttributes)
    );

    public static final EntityType<GullEntity> GULL = register("gull",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(GullEntity::new)
            .spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
            .defaultAttributes(GullEntity::createAttributes)
    );

    public static final EntityType<PenguinEntity> PENGUIN = register("penguin",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(PenguinEntity::new)
            .spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.changing(0.5f, 1.4f))
            .defaultAttributes(PenguinEntity::createAttributes)
    );

    public static final EntityType<PigeonEntity> PIGEON = register("pigeon",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(PigeonEntity::new)
            .spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.changing(0.5f, 0.6f))
            .defaultAttributes(PigeonEntity::createAttributes)
    );

    public static final EntityType<RavenEntity> RAVEN = register("raven",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(RavenEntity::new)
            .spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
            .defaultAttributes(RavenEntity::createAttributes)
    );

    public static final EntityType<RobinEntity> ROBIN = register("robin",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(RobinEntity::new)
            .spawnGroup(SpawnGroup.AMBIENT)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
            .defaultAttributes(RobinEntity::createAttributes)
    );

    public static final EntityType<SparrowEntity> SPARROW = register("sparrow",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(SparrowEntity::new)
            .spawnGroup(SpawnGroup.AMBIENT)
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
            .defaultAttributes(SparrowEntity::createAttributes)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(FowlPlay.ID, id), builder.build());
    }

    public static void init() {
    }
}
