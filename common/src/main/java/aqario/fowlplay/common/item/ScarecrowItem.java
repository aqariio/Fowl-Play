package aqario.fowlplay.common.item;

import aqario.fowlplay.common.entity.ScarecrowEntity;
import aqario.fowlplay.core.FowlPlayEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ScarecrowItem extends Item {
    public ScarecrowItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext usageContext) {
        Direction direction = usageContext.getClickedFace();
        if(direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        }
        Level world = usageContext.getLevel();
        BlockPlaceContext placementContext = new BlockPlaceContext(usageContext);
        BlockPos blockPos = placementContext.getClickedPos();
        ItemStack itemStack = usageContext.getItemInHand();
        Vec3 vec3d = Vec3.atBottomCenterOf(blockPos);
        AABB box = FowlPlayEntityTypes.SCARECROW.get().getDimensions().makeBoundingBox(vec3d.x(), vec3d.y(), vec3d.z());
        if(!world.noCollision(null, box) || !world.getEntities(null, box).isEmpty()) {
            return InteractionResult.FAIL;
        }

        if(world instanceof ServerLevel serverWorld) {
            ScarecrowEntity scarecrow = FowlPlayEntityTypes.SCARECROW.get()
                .create(serverWorld, null, blockPos, MobSpawnType.SPAWN_EGG, true, true);
            if(scarecrow == null) {
                return InteractionResult.FAIL;
            }

            float yaw = (float) Mth.floor((Mth.wrapDegrees(usageContext.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
            scarecrow.moveTo(scarecrow.getX(), scarecrow.getY(), scarecrow.getZ(), yaw, 0.0F);
            serverWorld.addFreshEntityWithPassengers(scarecrow);
            world.playSound(
                null, scarecrow.getX(), scarecrow.getY(), scarecrow.getZ(), scarecrow.getPlaceSound(), SoundSource.BLOCKS, 0.75F, 0.8F
            );
            scarecrow.gameEvent(GameEvent.ENTITY_PLACE, usageContext.getPlayer());
        }

        itemStack.shrink(1);
        return InteractionResult.sidedSuccess(world.isClientSide());
    }
}
