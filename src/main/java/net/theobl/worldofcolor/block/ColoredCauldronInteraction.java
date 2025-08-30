package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.Map;
import java.util.function.Predicate;

public interface ColoredCauldronInteraction extends CauldronInteraction {
    static void bootStrap() {
        WorldOfColor.LOGGER.info("ColoredCauldronInteraction bootstrap");
        Map<Item, CauldronInteraction> map = EMPTY.map();
        map.put(Items.POTION, (state, level, pos, player, hand, stack) -> {
            PotionContents potioncontents = stack.get(DataComponents.POTION_CONTENTS);
            if (potioncontents != null && potioncontents.is(Potions.WATER)) {
                if (!level.isClientSide) {
                    Item item = stack.getItem();
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                    player.awardStat(Stats.USE_CAULDRON);
                    player.awardStat(Stats.ITEM_USED.get(item));
                    BlockState blockState = Blocks.WATER_CAULDRON.defaultBlockState();
                    for (DeferredBlock<Block> block : ModBlocks.COLORED_CAULDRONS) {
                        if(state.is(block)) {
                            int index = ModBlocks.COLORED_CAULDRONS.indexOf(block);
                            blockState = ModBlocks.COLORED_WATER_CAULDRONS.get(index).get().defaultBlockState();
                        }
                    }
                    level.setBlockAndUpdate(pos, blockState);
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        });
        Map<Item, CauldronInteraction> map1 = WATER.map();
        map1.put(
                Items.BUCKET,
                (state, level, pos, player, hand, stack) -> fillBucket(
                        state,
                        level,
                        pos,
                        player,
                        hand,
                        stack,
                        new ItemStack(Items.WATER_BUCKET),
                        blockState -> blockState.getValue(LayeredCauldronBlock.LEVEL) == 3,
                        SoundEvents.BUCKET_FILL
                )
        );
        Map<Item, CauldronInteraction> map2 = LAVA.map();
        map2.put(
                Items.BUCKET,
                (state, level, pos, player, hand, stack) -> fillBucket(
                        state,
                        level,
                        pos,
                        player,
                        hand,
                        stack,
                        new ItemStack(Items.LAVA_BUCKET),
                        blockState -> true,
                        SoundEvents.BUCKET_FILL_LAVA
                )
        );
        Map<Item, CauldronInteraction> map3 = POWDER_SNOW.map();
        map3.put(
                Items.BUCKET,
                (state, level, pos, player, hand, stack) -> fillBucket(
                        state,
                        level,
                        pos,
                        player,
                        hand,
                        stack,
                        new ItemStack(Items.POWDER_SNOW_BUCKET),
                        blockState -> blockState.getValue(LayeredCauldronBlock.LEVEL) == 3,
                        SoundEvents.BUCKET_FILL_POWDER_SNOW
                )
        );
    }

    static ItemInteractionResult fillBucket(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack emptyStack,
            ItemStack filledStack,
            Predicate<BlockState> statePredicate,
            SoundEvent fillSound
    ) {
        if (!statePredicate.test(state)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            if (!level.isClientSide) {
                BlockState blockState = Blocks.CAULDRON.defaultBlockState();
                for (DeferredBlock<Block> block : ModBlocks.COLORED_WATER_CAULDRONS) {
                    if(state.is(block)) {
                        int index = ModBlocks.COLORED_WATER_CAULDRONS.indexOf(block);
                        blockState = ModBlocks.COLORED_CAULDRONS.get(index).get().defaultBlockState();
                    }
                }
                for (DeferredBlock<Block> block : ModBlocks.COLORED_LAVA_CAULDRONS) {
                    if(state.is(block)) {
                        int index = ModBlocks.COLORED_LAVA_CAULDRONS.indexOf(block);
                        blockState = ModBlocks.COLORED_CAULDRONS.get(index).get().defaultBlockState();
                    }
                }
                for (DeferredBlock<Block> block : ModBlocks.COLORED_POWDER_SNOW_CAULDRONS) {
                    if(state.is(block)) {
                        int index = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.indexOf(block);
                        blockState = ModBlocks.COLORED_CAULDRONS.get(index).get().defaultBlockState();
                    }
                }
                Item item = emptyStack.getItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(emptyStack, player, filledStack));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(item));
                level.setBlockAndUpdate(pos, blockState);
                level.playSound(null, pos, fillSound, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
    }
}
