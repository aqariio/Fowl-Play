package aqario.fowlplay.common.item;

import aqario.fowlplay.common.entity.ScarecrowEntity;
import aqario.fowlplay.core.FowlPlayEntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ScarecrowItem extends Item {
    public ScarecrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext usageContext) {
        Direction direction = usageContext.getSide();
        if(direction == Direction.DOWN) {
            return ActionResult.FAIL;
        }
        World world = usageContext.getWorld();
        ItemPlacementContext placementContext = new ItemPlacementContext(usageContext);
        BlockPos blockPos = placementContext.getBlockPos();
        ItemStack itemStack = usageContext.getStack();
        Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
        Box box = FowlPlayEntityType.SCARECROW.get().getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
        if(!world.isSpaceEmpty(null, box) || !world.getOtherEntities(null, box).isEmpty()) {
            return ActionResult.FAIL;
        }

        if(world instanceof ServerWorld serverWorld) {
            ScarecrowEntity scarecrow = FowlPlayEntityType.SCARECROW.get()
                .create(serverWorld, itemStack.getNbt(), null, blockPos, SpawnReason.SPAWN_EGG, true, true);
            if(scarecrow == null) {
                return ActionResult.FAIL;
            }

            float yaw = (float) MathHelper.floor((MathHelper.wrapDegrees(usageContext.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
            scarecrow.refreshPositionAndAngles(scarecrow.getX(), scarecrow.getY(), scarecrow.getZ(), yaw, 0.0F);
            serverWorld.spawnEntityAndPassengers(scarecrow);
            world.playSound(
                null, scarecrow.getX(), scarecrow.getY(), scarecrow.getZ(), scarecrow.getPlaceSound(), SoundCategory.BLOCKS, 0.75F, 0.8F
            );
            scarecrow.emitGameEvent(GameEvent.ENTITY_PLACE, usageContext.getPlayer());
        }

        itemStack.decrement(1);
        return ActionResult.success(world.isClient());
    }
}
