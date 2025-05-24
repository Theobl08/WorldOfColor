package net.theobl.worldofcolor.util;

import com.google.common.collect.Maps;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.block.ModBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    }

    public static void registerFlammable(Block block, int encouragement, int flammability) {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(block, encouragement, flammability);
    }

    public static void registerStrippable(Block log, Block strippedLog) {
        AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
        AxeItem.STRIPPABLES.put(log, strippedLog);
    }
}
