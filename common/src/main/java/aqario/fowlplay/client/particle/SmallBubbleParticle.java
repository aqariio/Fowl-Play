package aqario.fowlplay.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;

public class SmallBubbleParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;
    protected int popAge;
    protected int maxPopAge;
    protected boolean shouldPop;

    public SmallBubbleParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.gravity = -0.05F;
        this.friction = 0.95F;
        this.setSize(0.02F, 0.02F);
        this.quadSize = this.quadSize * (this.random.nextFloat() * 0.6F + 0.2F);
        this.xd = velocityX * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.yd = velocityY * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.zd = velocityZ * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.lifetime = (int) (30.0 / (Math.random() * 0.8 + 0.2));
        this.maxPopAge = 4;
        this.setSpriteFromAge(spriteProvider);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (!this.removed && !this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER)) {
            this.shouldPop = true;
        }
        if (this.age++ >= this.lifetime) {
            this.shouldPop = true;
        }
        else {
            this.yd = this.yd - 0.04 * (double) this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
                this.xd *= 1.1;
                this.zd *= 1.1;
            }

            this.xd = this.xd * (double) this.friction;
            this.yd = this.yd * (double) this.friction;
            this.zd = this.zd * (double) this.friction;
            if (this.onGround) {
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }
        }
        if (this.shouldPop && this.popAge++ >= this.maxPopAge) {
            this.remove();
        }
        if (!this.removed) {
            this.setSprite(this.spriteProvider.get(this.popAge, this.maxPopAge));
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SmallBubbleParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
