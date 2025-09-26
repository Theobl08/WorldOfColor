package net.theobl.worldofcolor.util;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModUtil {
    public static final List<DyeColor> COLORS = new ArrayList<>(Arrays.asList(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
            DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
            DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
            DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK));

    public static final List<String> CLASSIC_COLORS =
            List.of("vibrant_red", "dull_orange", "bright_yellow", "chartreuse",
                    "vibrant_green", "spring_green", "bright_cyan", "capri",
                    "ultramarine", "violet", "vibrant_purple", "bright_magenta",
                    "rose", "dark_gray", "silver", "classic_white");

    public static final List<Item> CONCRETES =
            List.of(Items.WHITE_CONCRETE, Items.LIGHT_GRAY_CONCRETE, Items.GRAY_CONCRETE, Items.BLACK_CONCRETE,
                    Items.BROWN_CONCRETE, Items.RED_CONCRETE, Items.ORANGE_CONCRETE, Items.YELLOW_CONCRETE,
                    Items.LIME_CONCRETE, Items.GREEN_CONCRETE, Items.CYAN_CONCRETE, Items.LIGHT_BLUE_CONCRETE,
                    Items.BLUE_CONCRETE, Items.PURPLE_CONCRETE, Items.MAGENTA_CONCRETE, Items.PINK_CONCRETE);

    public static final List<Block> SHULKER_BOXES =
            List.of(Blocks.WHITE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX,
                    Blocks.BROWN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX,
                    Blocks.LIME_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX,
                    Blocks.BLUE_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.PINK_SHULKER_BOX);

    public static final List<Item> DYES =
            List.of(Items.WHITE_DYE, Items.LIGHT_GRAY_DYE, Items.GRAY_DYE, Items.BLACK_DYE,
                    Items.BROWN_DYE, Items.RED_DYE, Items.ORANGE_DYE, Items.YELLOW_DYE,
                    Items.LIME_DYE, Items.GREEN_DYE, Items.CYAN_DYE, Items.LIGHT_BLUE_DYE,
                    Items.BLUE_DYE, Items.PURPLE_DYE, Items.MAGENTA_DYE, Items.PINK_DYE);

    public static final List<Block> POTTABLE_PLANTS = new ArrayList<>();

    public static void setup() {
        ModBlocks.COLORED_PLANKS.forEach(block -> registerFlammable(block.get(), 5, 20));
        ModBlocks.COLORED_SLABS.forEach(block -> registerFlammable(block.get(), 5, 20));
        ModBlocks.COLORED_FENCE_GATES.forEach(block -> registerFlammable(block.get(), 5, 20));
        ModBlocks.COLORED_FENCES.forEach(block -> registerFlammable(block.get(), 5, 20));
        ModBlocks.COLORED_STAIRS.forEach(block -> registerFlammable(block.get(), 5, 20));
        ModBlocks.COLORED_LOGS.forEach(block -> registerFlammable(block.get(), 5, 5));
        ModBlocks.COLORED_STRIPPED_LOGS.forEach(block -> registerFlammable(block.get(), 5, 5));
        ModBlocks.COLORED_STRIPPED_WOODS.forEach(block -> registerFlammable(block.get(), 5, 5));
        ModBlocks.COLORED_WOODS.forEach(block -> registerFlammable(block.get(), 5, 5));
        ModBlocks.COLORED_LEAVES.forEach(block -> registerFlammable(block.get(), 30, 60));
        ModBlocks.CLASSIC_WOOLS.forEach(block -> registerFlammable(block.get(), 30, 60));
        ModBlocks.CLASSIC_CARPETS.forEach(block -> registerFlammable(block.get(), 60, 20));

        for (DeferredBlock<Block> log : ModBlocks.COLORED_LOGS) {
            int index = ModBlocks.COLORED_LOGS.indexOf(log);
            registerStrippable(log.get(), ModBlocks.COLORED_STRIPPED_LOGS.get(index).get());
        }

        for (DeferredBlock<Block> wood : ModBlocks.COLORED_WOODS) {
            int index = ModBlocks.COLORED_WOODS.indexOf(wood);
            registerStrippable(wood.get(), ModBlocks.COLORED_STRIPPED_WOODS.get(index).get());
        }

        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            //ModBlocks.COLORED_FLOWER_POTS.get(index).get().addPlant(BuiltInRegistries.BLOCK.getKey(Blocks.TORCHFLOWER), ModBlocks.COLORED_POTTED_COLORED_SAPLINGS.get(index));
            ModBlocks.COLORED_POTTED_PLANTS.forEach((block, deferredBlocks) ->
                    ModBlocks.COLORED_FLOWER_POTS.get(index).get().addPlant(BuiltInRegistries.BLOCK.getKey(block), deferredBlocks.get(index)));
        }
    }

    public static void registerFlammable(Block block, int encouragement, int flammability) {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(block, encouragement, flammability);
    }

    public static void registerStrippable(Block log, Block strippedLog) {
        AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
        AxeItem.STRIPPABLES.put(log, strippedLog);
    }

    public static boolean isColoredBlock(BlockState state, List<DeferredBlock<Block>> coloredBlocks) {
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            if (state.is(coloredBlocks.get(index)))
                return true;
        }
        return false;
    }

    public static String name(Supplier<Block> block) {
        if(block instanceof DeferredBlock<Block> deferredBlock) {
            return deferredBlock.getId().getPath();
        }
        else {
            return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
        }
    }

    static {
//        for(Field field : Blocks.class.getDeclaredFields()) {
//            try {
//                if(field.getType() != Block.class)
//                    continue;
//                Block block = (Block) field.get(null);
//                if(block instanceof FlowerBlock || block instanceof SaplingBlock) {
//                    POTTABLE_PLANTS.add(block);
//                }
//            } catch (IllegalAccessException e) {
//                throw new IllegalStateException(Blocks.class.getName() + "#" + field.getName() + " is not a static field of type Block");
//            }
//        }
        for (Block block : BuiltInRegistries.BLOCK) {
            if(block instanceof FlowerBlock || block instanceof SaplingBlock)
                POTTABLE_PLANTS.add(block);
        }
        POTTABLE_PLANTS.addAll(List.of(
                Blocks.FERN,
                Blocks.RED_MUSHROOM,
                Blocks.BROWN_MUSHROOM,
                Blocks.DEAD_BUSH,
                Blocks.CACTUS,
                Blocks.BAMBOO,
                Blocks.CRIMSON_FUNGUS,
                Blocks.WARPED_FUNGUS,
                Blocks.CRIMSON_ROOTS,
                Blocks.WARPED_ROOTS,
                Blocks.AZALEA,
                Blocks.FLOWERING_AZALEA
        ));
    }
}
