package aqario.fowlplay.common.entity.bird.fowl.landfowl;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class ChickenEntity extends FlyingBirdEntity {
    public ChickenEntity(EntityType<? extends BirdEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public float getFlapVolume() {
        return 0;
    }

    @Override
    public float getFlapPitch() {
        return 0;
    }

    @Override
    public Ingredient getFood() {
        return null;
    }
}
