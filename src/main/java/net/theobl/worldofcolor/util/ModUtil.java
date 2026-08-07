package net.theobl.worldofcolor.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
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

    public static final List<MapColor> CLASSIC_COLORS_MAP_COLOR =
            List.of(MapColor.FIRE, MapColor.TERRACOTTA_YELLOW, MapColor.GOLD, MapColor.COLOR_LIGHT_GREEN,
                    MapColor.EMERALD, MapColor.EMERALD, MapColor.DIAMOND, MapColor.DIAMOND,
                    MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_PURPLE, MapColor.COLOR_PURPLE, MapColor.COLOR_MAGENTA,
                    MapColor.COLOR_PINK, MapColor.STONE, MapColor.METAL, MapColor.SNOW);

    public static final List<ResourceKey<Block>> POTTABLE_PLANTS = new ArrayList<>();

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
        ModBlocks.COLORED_SHELVES.forEach(block -> registerFlammable(block.get(), 30, 20));

        registerFlammable(ModBlocks.LIGHT_GRAY_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.GRAY_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.BLACK_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.BROWN_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.YELLOW_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.LIME_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.GREEN_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.CYAN_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.LIGHT_BLUE_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.BLUE_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.PURPLE_TULIP.get(), 60, 100);
        registerFlammable(ModBlocks.MAGENTA_TULIP.get(), 60, 100);

        registerFlammable(ModBlocks.RGB_WOOL.get(), 30, 60);
        registerFlammable(ModBlocks.RGB_CARPET.get(), 60, 20);

        for (DyeColor color : COLORS) {
            ModBlocks.COLORED_POTTED_PLANTS.forEach((block, deferredBlocks) ->
                    ModBlocks.COLORED_FLOWER_POTS.pick(color).get().addPlant(block.identifier(), deferredBlocks.pick(color)));
        }
    }

    public static void registerFlammable(Block block, int encouragement, int flammability) {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(block, encouragement, flammability);
    }

    public static boolean isColoredBlock(BlockState state, List<DeferredBlock<Block>> coloredBlocks) {
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            if (state.is(coloredBlocks.get(index)))
                return true;
        }
        return false;
    }

    public static <T> String name(DeferredHolder<T, ? extends T> deferredHolder) {
        return deferredHolder.getId().getPath();
    }

    public static Block getBlock(ResourceKey<Block> id) {
        return BuiltInRegistries.BLOCK.getValue(id);
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
            if(block instanceof FlowerBlock || block instanceof SaplingBlock) {
                if (block == Blocks.OPEN_EYEBLOSSOM) {
                    POTTABLE_PLANTS.add(BlockItemIds.OPEN_EYEBLOSSOM.block());
                }
                else if (block == Blocks.CLOSED_EYEBLOSSOM) {
                    POTTABLE_PLANTS.add(BlockItemIds.CLOSED_EYEBLOSSOM.block());
                }
                else {
                    POTTABLE_PLANTS.add(block.builtInRegistryHolder().getKey());
                }
            }
        }
        POTTABLE_PLANTS.addAll(List.of(
                BlockItemIds.FERN.block(),
                BlockItemIds.RED_MUSHROOM.block(),
                BlockItemIds.BROWN_MUSHROOM.block(),
                BlockItemIds.DEAD_BUSH.block(),
                BlockItemIds.CACTUS.block(),
                BlockItemIds.BAMBOO.block(),
                BlockItemIds.CRIMSON_FUNGUS.block(),
                BlockItemIds.WARPED_FUNGUS.block(),
                BlockItemIds.CRIMSON_ROOTS.block(),
                BlockItemIds.WARPED_ROOTS.block(),
                BlockItemIds.AZALEA.block(),
                BlockItemIds.FLOWERING_AZALEA.block()
        ));
    }
}
