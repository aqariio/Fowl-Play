package aqario.fowlplay.neoforge.init;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public final class FowlPlayEntityType {
    public static final EntityType<BlueJayEntity> BLUE_JAY = register("blue_jay",
        EntityType.Builder.create(BlueJayEntity::new, SpawnGroup.AMBIENT)
            .setDimensions(0.4f, 0.55f)
            .setEyeHeight(0.475f)
    );

    public static final EntityType<CardinalEntity> CARDINAL = register("cardinal",
        EntityType.Builder.create(CardinalEntity::new, SpawnGroup.AMBIENT)
            .setDimensions(0.4f, 0.55f)
            .setEyeHeight(0.475f)
    );

    public static final EntityType<GullEntity> GULL = register("gull",
        EntityType.Builder.create(GullEntity::new, SpawnGroup.CREATURE)
            .setDimensions(0.6f, 0.8f)
            .setEyeHeight(0.7f)
    );

    public static final EntityType<PenguinEntity> PENGUIN = register("penguin",
        EntityType.Builder.create(PenguinEntity::new, SpawnGroup.CREATURE)
            .setDimensions(0.5f, 1.4f)
            .setEyeHeight(1.2f)
            .passengerAttachments(new Vec3d(0, 0.75, -0.1))
    );

    public static final EntityType<PigeonEntity> PIGEON = register("pigeon",
        EntityType.Builder.create(PigeonEntity::new, SpawnGroup.CREATURE)
            .setDimensions(0.5f, 0.6f)
            .setEyeHeight(0.5f)
    );

    public static final EntityType<RobinEntity> ROBIN = register("robin",
        EntityType.Builder.create(RobinEntity::new, SpawnGroup.AMBIENT)
            .setDimensions(0.4f, 0.55f)
            .setEyeHeight(0.475f)
    );

    public static final EntityType<SparrowEntity> SPARROW = register("sparrow",
        EntityType.Builder.create(SparrowEntity::new, SpawnGroup.AMBIENT)
            .setDimensions(0.4f, 0.55f)
            .setEyeHeight(0.475f)
    );

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id), builder.build(id));
    }

    public static void init() {
    }
}
