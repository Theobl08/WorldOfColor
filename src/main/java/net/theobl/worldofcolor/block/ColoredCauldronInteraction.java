package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCauldronInteractionEvent;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.function.Predicate;

@EventBusSubscriber
public class ColoredCauldronInteraction extends CauldronInteractions {
    public static CauldronInteraction.Dispatcher DYED_WATER = new CauldronInteraction.Dispatcher();

    @SubscribeEvent
    private static void registerCauldronInteractionDispatcher(RegisterCauldronInteractionEvent.Dispatcher event) {
        event.register(WorldOfColor.asResource("dyed_water"), DYED_WATER);
    }

    @SubscribeEvent
    private static void registerCauldronInteraction(RegisterCauldronInteractionEvent.Interaction event) {
        final Identifier dyedWater = WorldOfColor.asResource("dyed_water");
        WorldOfColor.LOGGER.info("ColoredCauldronInteraction bootstrap");
        event.register(Identifier.withDefaultNamespace("empty"), Items.POTION, (state, level, pos, player, hand, stack) -> {
            PotionContents potioncontents = stack.get(DataComponents.POTION_CONTENTS);
            if (potioncontents != null && potioncontents.is(Potions.WATER)) {
                if (!level.isClientSide()) {
                    Item item = stack.getItem();
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                    player.awardStat(Stats.USE_CAULDRON);
                    player.awardStat(Stats.ITEM_USED.get(item));
                    BlockState blockState = Blocks.WATER_CAULDRON.defaultBlockState();
                    for (DyeColor color : ModUtil.COLORS) {
                        if(state.is(ModBlocks.COLORED_CAULDRONS.pick(color))) {
                            blockState = ModBlocks.COLORED_WATER_CAULDRONS.pick(color).get().defaultBlockState();
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
        event.register(Identifier.withDefaultNamespace("water"),
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
        Items.DYE.forEach(item -> event.register(Identifier.withDefaultNamespace("water"), item, ColoredCauldronInteraction::dyeWaterInteraction));
        event.register(Identifier.withDefaultNamespace("lava"),
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
        event.register(Identifier.withDefaultNamespace("powder_snow"),
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

        event.register(dyedWater, Items.LAVA_BUCKET, CauldronInteractions::fillLavaInteraction);
        event.register(dyedWater, Items.WATER_BUCKET, CauldronInteractions::fillWaterInteraction);
        event.register(dyedWater, Items.POWDER_SNOW_BUCKET, CauldronInteractions::fillPowderSnowInteraction);
        event.register(dyedWater,
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
        event.register(dyedWater,
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
        event.register(dyedWater, Items.POTION, (state, level, pos, player, hand, itemInHand) -> {
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
        event.register(dyedWater, ItemTags.CAULDRON_CAN_REMOVE_DYE, ColoredCauldronInteraction::dyeableItemIteration);
        Items.DYE.forEach(item -> event.register(dyedWater, item, ColoredCauldronInteraction::dyeInteraction));
    }

    public static InteractionResult fillBucket(
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
                for (DyeColor color : ModUtil.COLORS) {
                    if(state.is(ModBlocks.COLORED_WATER_CAULDRONS.pick(color))) {
                        blockState = ModBlocks.COLORED_CAULDRONS.pick(color).get().defaultBlockState();
                    }

                    else if(state.is(ModBlocks.COLORED_LAVA_CAULDRONS.pick(color))) {
                        blockState = ModBlocks.COLORED_CAULDRONS.pick(color).get().defaultBlockState();
                    }

                    else if(state.is(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.pick(color))) {
                        blockState = ModBlocks.COLORED_CAULDRONS.pick(color).get().defaultBlockState();
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
                BlockState newState = ModBlocks.DYED_WATER_CAULDRON.get().defaultBlockState();
                for (DyeColor dyeColor : ModUtil.COLORS) {
                    if(state.is(ModBlocks.COLORED_WATER_CAULDRONS.pick(dyeColor))) {
                        newState = ModBlocks.COLORED_DYED_WATER_CAULDRONS.pick(dyeColor).get().defaultBlockState();
                    }
                }
                level.setBlockAndUpdate(pos, newState.setValue(LayeredCauldronBlock.LEVEL, state.getValue(LayeredCauldronBlock.LEVEL)));
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
        if((level.getBlockEntity(pos) instanceof DyedWaterCauldronBlockEntity blockEntity)) {
            if (!level.isClientSide()) {
                itemInHand.set(DataComponents.DYED_COLOR, new DyedItemColor(ARGB.transparent(blockEntity.getWaterColor())));
                player.awardStat(Stats.CLEAN_ARMOR);
                DyedWaterCauldronBlock.lowerFillLevel(state, level, pos);
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
    }
}
