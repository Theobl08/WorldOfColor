package net.theobl.worldofcolor.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PostSpawnProcessor;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.item.CushionItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ModCushionItem extends CushionItem {
    private final EntityType<Cushion> entityType;
    public ModCushionItem(EntityType<Cushion> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        UseOnContext recalculatedContext = recalculateContextForSpecialCollisionShapes(context);
        Level level = context.getLevel();
        Direction clickedFace = recalculatedContext.getClickedFace();
        if (clickedFace != Direction.UP) {
            return InteractionResult.FAIL;
        }

        BlockPlaceContext placeContext = new BlockPlaceContext(recalculatedContext);
        BlockPos blockPos = placeContext.getClickedPos();
        Vec3 entityPos = Vec3.atCenterOfWithY(blockPos, recalculatedContext.getClickLocation().y);
        AABB spawnAABB = entityType.getSpawnAABB(entityPos);
        if (!Cushion.canBePlacedAt(level, spawnAABB)) {
            return InteractionResult.FAIL;
        }

        ItemStack itemStack = context.getItemInHand();
        if (level instanceof ServerLevel serverLevel) {
            if (!serverLevel.getEntitiesOfClass(Cushion.class, spawnAABB).isEmpty()) {
                return InteractionResult.FAIL;
            }

            PostSpawnProcessor<Cushion> entityConfig = EntityType.createDefaultStackConfig(serverLevel, itemStack, context.getPlayer());
            Cushion cushion = entityType.create(serverLevel, entityConfig, blockPos, EntitySpawnReason.SPAWN_ITEM_USE, true, true);
            if (cushion == null) {
                return InteractionResult.FAIL;
            }

            cushion.snapTo(entityPos, Direction.fromYRot(placeContext.getRotation()).toYRot(), 0.0F);
            serverLevel.addFreshEntity(cushion);
            cushion.destroyIfInFire(serverLevel);
            level.playSound(null, cushion.getX(), cushion.getY(), cushion.getZ(), SoundEvents.CUSHION_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
            cushion.gameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
            itemStack.consume(1, placeContext.getPlayer());
        }

        return InteractionResult.SUCCESS;
    }
}
