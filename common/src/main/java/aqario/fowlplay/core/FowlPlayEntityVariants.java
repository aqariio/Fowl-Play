package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.variant.EntityVariant;
import aqario.fowlplay.common.entity.variant.GooseVariant;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class FowlPlayEntityVariants {
    public static final EntityVariant EMPTY = register(() -> EntityType.PIG, "empty", EntityVariant::new);
    public static final EntityVariant GREEN_HEADED = register(FowlPlayEntityTypes.DUCK, "green_headed", EntityVariant::new);
    public static final EntityVariant CANADA = registerWildGoose("canada");
    public static final EntityVariant GREYLAG = registerWildGoose("greylag", "emden");
    public static final EntityVariant SWAN = registerWildGoose("swan", "chinese");
    public static final EntityVariant EMDEN = registerDomesticGoose("emden");
    public static final EntityVariant CHINESE = registerDomesticGoose("chinese");

    private static EntityVariant registerWildGoose(String id) {
        return registerGoose(id, GooseVariant.ModelType.WILD, Optional.empty());
    }

    private static EntityVariant registerWildGoose(String id, String domesticId) {
        return registerGoose(id, GooseVariant.ModelType.WILD, Optional.of(domesticId));
    }

    private static EntityVariant registerDomesticGoose(String id) {
        return registerGoose(id, GooseVariant.ModelType.DOMESTIC, Optional.empty());
    }

    private static EntityVariant registerGoose(String id, GooseVariant.ModelType modelType, Optional<String> domesticId) {
        return register(FowlPlayEntityTypes.GOOSE, id, (entity, name) -> new GooseVariant(
            entity,
            name,
            modelType,
            domesticId.map(FowlPlay::id)
        ));
    }

    private static <T extends Entity> EntityVariant register(Supplier<EntityType<T>> entityType, String id, BiFunction<EntityType<T>, String, EntityVariant> factory) {
        return Registry.register(FowlPlayBuiltInRegistries.ENTITY_VARIANT, FowlPlay.id(id), factory.apply(
            entityType.get(),
            id
        ));
    }

    public static void init() {
    }
}
