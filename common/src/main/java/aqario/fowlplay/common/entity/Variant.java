package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.FowlPlayRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public record Variant(EntityType<?> entityType, Identifier texture) {
    public static final Variant EMPTY = register("empty", () -> EntityType.PIG);
    public static final Variant GREEN_HEADED = register("green_headed", FowlPlayEntityType.DUCK);
    public static final Variant GREYLAG = register("greylag", FowlPlayEntityType.GOOSE);
    public static final Variant CANADIAN = register("canadian", FowlPlayEntityType.GOOSE);
    public static final Variant EMDEN = register("emden", FowlPlayEntityType.GOOSE);
    public static final Variant CHINESE = register("chinese", FowlPlayEntityType.GOOSE);

    private static <T extends Entity> Variant register(String id, Supplier<EntityType<T>> entityType) {
        String entityId = Registries.ENTITY_TYPE.getId(entityType.get()).getPath();
        Identifier texture = Identifier.of(
            FowlPlay.ID,
            "textures/entity/" + entityId + "/" + id + "_" + entityId + ".png"
        );
        return Registry.register(FowlPlayRegistries.VARIANT, Identifier.of(FowlPlay.ID, id), new Variant(
            entityType.get(),
            texture
        ));
    }

    public static void init() {
    }
}
