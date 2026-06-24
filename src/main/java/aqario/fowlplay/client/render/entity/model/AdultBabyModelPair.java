package aqario.fowlplay.client.render.entity.model;

import net.minecraft.client.model.Model;

public record AdultBabyModelPair<T extends Model>(T adultModel, T babyModel) {
    public T getModel(boolean isBaby) {
        return isBaby ? this.babyModel : this.adultModel;
    }
}
