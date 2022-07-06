package pengugang.fowlplay.entity.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.AxolotlBrain;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.recipe.Ingredient;
import pengugang.fowlplay.FowlPlayTags;
import pengugang.fowlplay.entity.SeagullEntity;

/**
 * Represents the definition of a {@linkplain SeagullEntity seagull entity} brain.
 *
 * <div class="fabric">
 * <table border=1>
 * <caption>Activities associated to the {@linkplain SeagullEntity seagull entity} brain</caption>
 * <tr>
 *   <th>Activity</th><th>Tasks</th>
 * </tr>
 * <tr>
 *   <td>{@link net.minecraft.entity.ai.brain.Activity#CORE}</td>
 *   <td><ul>
 *     <li>{@link net.minecraft.entity.ai.brain.task.LookAroundTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.WanderAroundTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.PlayDeadTimerTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.TemptationCooldownTask}</li>
 *   </ul></td>
 * </tr>
 * <tr>
 *   <td>{@link net.minecraft.entity.ai.brain.Activity#IDLE}</td>
 *   <td><ul>
 *     <li>{@link net.minecraft.entity.ai.brain.task.FollowMobTask FollowMobTask(PLAYER)} (time limited)</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.BreedTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.TemptTask} (random)</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.WalkTowardClosestAdultTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.SeekWaterTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.CompositeTask}</li>
 *   </ul></td>
 * </tr>
 * <tr>
 *   <td>{@link net.minecraft.entity.ai.brain.Activity#FIGHT}</td>
 *   <td><ul>
 *     <li>{@link net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.RangedApproachTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.MeleeAttackTask}</li>
 *     <li>{@link net.minecraft.entity.ai.brain.task.ForgetTask}</li>
 *   </ul></td>
 * </tr>
 * </table>
 * </div>
 */

public class SeagullBrain {
    private static final float ON_LAND_SPEED = 0.2f;
    private static final float IDLE_SPEED = 0.5f;
    private static final float TARGET_APPROACHING_SPEED = 0.6f;

    public static Brain<?> create(Brain<SeagullEntity> brain) {
        SeagullBrain.addCoreActivities(brain);
        SeagullBrain.addIdleActivities(brain);
        SeagullBrain.addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<SeagullEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new LookAroundTask(45, 90), new WanderAroundTask(), new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
    }

    private static void addIdleActivities(Brain<SeagullEntity> brain) {
    }

    private static void addFightActivities(Brain<SeagullEntity> brain) {
    }

/*    public static void updateActivities(SeagullEntity seagull) {
        Brain<SeagullEntity> brain = seagull.getBrain();
        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
        if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
                brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
    }

    private static float getTemptedSpeed(SeagullEntity entity) {
        return entity.isFlying() ? 0.5f : 0.2f;
    }

    public static Ingredient getTemptItems() {
        return Ingredient.fromTag(FowlPlayTags.SEAGULL_TEMPT_ITEMS);
    }*/
}
