package aqario.fowlplay.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;

public class SmallBubbleParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    protected int popAge;
    protected int maxPopAge;
    protected boolean shouldPop;

    public SmallBubbleParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.gravityStrength = -0.05F;
        this.velocityMultiplier = 0.95F;
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        this.scale = this.scale * (this.random.nextFloat() * 0.6F + 0.2F);
        this.velocityX = velocityX * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.velocityY = velocityY * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.velocityZ = velocityZ * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.maxAge = (int) (30.0 / (Math.random() * 0.8 + 0.2));
        this.maxPopAge = 4;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if(!this.dead && !this.world.getFluidState(BlockPos.ofFloored(this.x, this.y, this.z)).isIn(FluidTags.WATER)) {
            this.shouldPop = true;
        }
        if(this.age++ >= this.maxAge) {
            this.shouldPop = true;
        }
        else {
            this.velocityY = this.velocityY - 0.04 * (double) this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if(this.ascending && this.y == this.prevPosY) {
                this.velocityX *= 1.1;
                this.velocityZ *= 1.1;
            }

            this.velocityX = this.velocityX * (double) this.velocityMultiplier;
            this.velocityY = this.velocityY * (double) this.velocityMultiplier;
            this.velocityZ = this.velocityZ * (double) this.velocityMultiplier;
            if(this.onGround) {
                this.velocityX *= 0.7F;
                this.velocityZ *= 0.7F;
            }
        }
        if(this.shouldPop && this.popAge++ >= this.maxPopAge) {
            this.markDead();
        }
        if(!this.dead) {
            this.setSprite(spriteProvider.getSprite(this.popAge, this.maxPopAge));
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SmallBubbleParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
