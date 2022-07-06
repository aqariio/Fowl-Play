package pengugang.fowlplay.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import pengugang.fowlplay.FowlPlay;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PenguinEntity extends AnimalEntity implements IAnimatable, ItemSteerable, Saddleable {
    private AnimationFactory factory = new AnimationFactory(this);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.COD, Items.SALMON, Items.TROPICAL_FISH);
    public int fleeTime = 0;

    public PenguinEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return InitEntity.PENGUIN.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public static DefaultAttributeContainer.Builder createPenguinAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 10.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    public boolean canBreatheInWater() {
        return false;
    }

    public boolean isPushedByFluids() {
        return false;
    }

    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(1));
        } else {
            super.travel(movementInput);
        }

    }

    protected void initGoals() {

        this.goalSelector.add(0, new BreatheAirGoal(this));
        this.goalSelector.add(1, new ExtinguishFire());
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, PolarBearEntity.class, 10.0f, 1.4, 1.4));
        this.goalSelector.add(4, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(7, new MoveIntoWaterGoal(this));
        this.goalSelector.add(8, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 15.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));
    }

    @Override
    public int getMaxAir() {
        return 2400;
    }

    @Override
    protected int getNextAirOnLand(int air) {
        return this.getMaxAir();
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? 0.6f : 1.3f;
    }

    @Override
    public boolean consumeOnAStickItem() {
        return false;
    }

    @Override
    public void setMovementInput(Vec3d movementInput) {

    }

    @Override
    public float getSaddledSpeed() {
        return 0;
    }

    @Override
    public boolean canBeSaddled() {
        return false;
    }

    @Override
    public void saddle(@Nullable SoundCategory sound) {

    }

    @Override
    public boolean isSaddled() {
        return false;
    }

    private class ExtinguishFire extends EscapeDangerGoal {
        ExtinguishFire() {
            super(PenguinEntity.this, 2.0D);
        }

        public boolean canUse() {
            return (PenguinEntity.this.isBaby() || PenguinEntity.this.isOnFire());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isBaby() ? FowlPlay.ENTITY_PENGUIN_BABY_IDLE : FowlPlay.ENTITY_PENGUIN_IDLE;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return FowlPlay.ENTITY_PENGUIN_SWIM;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlay.ENTITY_PENGUIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FowlPlay.ENTITY_PENGUIN_DEATH;
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.penguin.waddle", true));
            return PlayState.CONTINUE;
        } else if (this.isTouchingWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.penguin.swim", true));
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.penguin.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
