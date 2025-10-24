package net.theobl.worldofcolor.block;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.theobl.worldofcolor.block.entity.ColoredDecoratedPotBlockEntity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ColoredDecoratedPotBlock extends DecoratedPotBlock {
    private final DyeColor color;
    public ColoredDecoratedPotBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (level.getBlockEntity(pos) instanceof ColoredDecoratedPotBlockEntity decoratedpotblockentity) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            } else {
                ItemStack itemstack1 = decoratedpotblockentity.getTheItem();
                if (!stack.isEmpty()
                        && (
                        itemstack1.isEmpty()
                                || ItemStack.isSameItemSameComponents(itemstack1, stack) && itemstack1.getCount() < itemstack1.getMaxStackSize()
                )) {
                    decoratedpotblockentity.wobble(DecoratedPotBlockEntity.WobbleStyle.POSITIVE);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    ItemStack itemstack = stack.consumeAndReturn(1, player);
                    float f;
                    if (decoratedpotblockentity.isEmpty()) {
                        decoratedpotblockentity.setTheItem(itemstack);
                        f = (float)itemstack.getCount() / itemstack.getMaxStackSize();
                    } else {
                        itemstack1.grow(1);
                        f = (float)itemstack1.getCount() / itemstack1.getMaxStackSize();
                    }

                    level.playSound(null, pos, SoundEvents.DECORATED_POT_INSERT, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * f);
                    if (level instanceof ServerLevel serverlevel) {
                        serverlevel.sendParticles(
                                ParticleTypes.DUST_PLUME, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0
                        );
                    }

                    decoratedpotblockentity.setChanged();
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.TRY_WITH_EMPTY_HAND;
                }
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof ColoredDecoratedPotBlockEntity decoratedpotblockentity) {
            level.playSound(null, pos, SoundEvents.DECORATED_POT_INSERT_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F);
            decoratedpotblockentity.wobble(DecoratedPotBlockEntity.WobbleStyle.NEGATIVE);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ColoredDecoratedPotBlockEntity(pos, state);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity blockentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof ColoredDecoratedPotBlockEntity decoratedpotblockentity) {
            builder.withDynamicDrop(SHERDS_DYNAMIC_DROP_ID, consumer -> {
                for (Item item : decoratedpotblockentity.getDecorations().ordered()) {
                    consumer.accept(item.getDefaultInstance());
                }
            });
        }

        return super.getDrops(state, builder);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        if (level.getBlockEntity(pos) instanceof ColoredDecoratedPotBlockEntity blockEntity) {
            PotDecorations potdecorations = blockEntity.getDecorations();
            return ColoredDecoratedPotBlockEntity.createDecoratedPotItem(potdecorations, this.color);
        } else {
            return super.getCloneItemStack(level, pos, state, includeData);
        }
    }
}
