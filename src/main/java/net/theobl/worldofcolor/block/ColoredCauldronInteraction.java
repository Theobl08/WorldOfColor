package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.Map;
import java.util.function.Predicate;

public interface ColoredCauldronInteraction extends CauldronInteraction {
    CauldronInteraction.InteractionMap DYED_WATER = CauldronInteraction.newInteractionMap("dyed_water");
    static void bootStrap() {
        WorldOfColor.LOGGER.info("ColoredCauldronInteraction bootstrap");
        Map<Item, CauldronInteraction> empty = EMPTY.map();
        empty.put(Items.POTION, (state, level, pos, player, hand, stack) -> {
            PotionContents potioncontents = stack.get(DataComponents.POTION_CONTENTS);
            if (potioncontents != null && potioncontents.is(Potions.WATER)) {
                if (!level.isClientSide()) {
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
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
        });
        Map<Item, CauldronInteraction> water = WATER.map();
        water.put(
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
        ModUtil.DYES.forEach(item -> water.put(item, ColoredCauldronInteraction::dyeWaterInteraction));
        Map<Item, CauldronInteraction> lava = LAVA.map();
        lava.put(
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
        Map<Item, CauldronInteraction> powderSnow = POWDER_SNOW.map();
        powderSnow.put(
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

        Map<Item, CauldronInteraction> dyedWater = DYED_WATER.map();
        CauldronInteraction.addDefaultInteractions(dyedWater);
        dyedWater.put(
                Items.BUCKET,
                (state, level, pos, player, hand, itemInHand) -> fillBucket(
                        state,
                        level,
                        pos,
                        player,
                        hand,
                        itemInHand,
                        new ItemStack(Items.WATER_BUCKET),
                        s -> s.getValue(LayeredCauldronBlock.LEVEL) == 3,
                        SoundEvents.BUCKET_FILL
                )
        );
        dyedWater.put(
                Items.GLASS_BOTTLE,
                (state, level, pos, player, hand, itemInHand) -> {
                    if (!level.isClientSide()) {
                        Item usedItem = itemInHand.getItem();
                        player.setItemInHand(
                                hand, ItemUtils.createFilledResult(itemInHand, player, PotionContents.createItemStack(Items.POTION, Potions.WATER))
                        );
                        player.awardStat(Stats.USE_CAULDRON);
                        player.awardStat(Stats.ITEM_USED.get(usedItem));
                        LayeredCauldronBlock.lowerFillLevel(state, level, pos);
                        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
                    }

                    return InteractionResult.SUCCESS;
                }
        );
        dyedWater.put(Items.POTION, (state, level, pos, player, hand, itemInHand) -> {
            if (state.getValue(LayeredCauldronBlock.LEVEL) == 3) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            } else {
                PotionContents potion = itemInHand.get(DataComponents.POTION_CONTENTS);
                if (potion != null && potion.is(Potions.WATER)) {
                    if (!level.isClientSide()) {
                        player.setItemInHand(hand, ItemUtils.createFilledResult(itemInHand, player, new ItemStack(Items.GLASS_BOTTLE)));
                        player.awardStat(Stats.USE_CAULDRON);
                        player.awardStat(Stats.ITEM_USED.get(itemInHand.getItem()));
                        level.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));
                        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                    }

                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.TRY_WITH_EMPTY_HAND;
                }
            }
        });
        dyedWater.put(Items.LEATHER_BOOTS, ColoredCauldronInteraction::dyeableItemIteration);
        dyedWater.put(Items.LEATHER_LEGGINGS, ColoredCauldronInteraction::dyeableItemIteration);
        dyedWater.put(Items.LEATHER_CHESTPLATE, ColoredCauldronInteraction::dyeableItemIteration);
        dyedWater.put(Items.LEATHER_HELMET, ColoredCauldronInteraction::dyeableItemIteration);
        dyedWater.put(Items.LEATHER_HORSE_ARMOR, ColoredCauldronInteraction::dyeableItemIteration);
        dyedWater.put(Items.WOLF_ARMOR, ColoredCauldronInteraction::dyeableItemIteration);
        ModUtil.DYES.forEach(item -> dyedWater.put(item, ColoredCauldronInteraction::dyeInteraction));
    }

    static InteractionResult fillBucket(
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
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        } else {
            if (!level.isClientSide()) {
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

            return InteractionResult.SUCCESS;
        }
    }

    static InteractionResult dyeWaterInteraction(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack itemInHand) {
        DyeColor color = DyeColor.getColor(itemInHand);
        if(color != null) {
            if (!level.isClientSide()) {
                Item itemUsed = itemInHand.getItem();
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(itemUsed));
                level.setBlockAndUpdate(pos, ModBlocks.DYED_WATER_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, state.getValue(LayeredCauldronBlock.LEVEL)));
                DyedWaterCauldronBlockEntity cauldronBlockEntity = (DyedWaterCauldronBlockEntity) level.getBlockEntity(pos);
                if (cauldronBlockEntity != null) {
                    cauldronBlockEntity.setWaterColor(color);
                    itemInHand.consume(1, player);
                    level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
    }

    static InteractionResult dyeInteraction(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack itemInHand) {
        DyeColor color = DyeColor.getColor(itemInHand);
        if(color != null) {
            if (!level.isClientSide()) {
                Item itemUsed = itemInHand.getItem();
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(itemUsed));
                DyedWaterCauldronBlockEntity cauldronBlockEntity = (DyedWaterCauldronBlockEntity) level.getBlockEntity(pos);
                if (cauldronBlockEntity != null) {
                    cauldronBlockEntity.applyDye(color);
                    itemInHand.consume(1, player);
                    level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
    }

    private static InteractionResult dyeableItemIteration(
            BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack itemInHand
    ) {
        if (!itemInHand.is(ItemTags.DYEABLE)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        } else if((level.getBlockEntity(pos) instanceof DyedWaterCauldronBlockEntity blockEntity)) {
            if (!level.isClientSide()) {
                itemInHand.set(DataComponents.DYED_COLOR, new DyedItemColor(ARGB.transparent(blockEntity.getWaterColor())));
                player.awardStat(Stats.CLEAN_ARMOR);
                LayeredCauldronBlock.lowerFillLevel(state, level, pos);
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
    }
}
