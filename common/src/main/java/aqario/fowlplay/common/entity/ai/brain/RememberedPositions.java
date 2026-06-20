package aqario.fowlplay.common.entity.ai.brain;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

public record RememberedPositions(
    BlockPos nest,
    BlockPos home
) {
    public static final Codec<RememberedPositions> CODEC = RecordCodecBuilder.create(i ->
        i.group(
            BlockPos.CODEC.fieldOf("nest").forGetter(RememberedPositions::nest),
            BlockPos.CODEC.fieldOf("home").forGetter(RememberedPositions::home)
        ).apply(i, RememberedPositions::new)
    );
}
