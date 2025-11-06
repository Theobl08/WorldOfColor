package net.theobl.worldofcolor.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.block.ModBlocks;

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

    public static final List<MapColor> CLASSIC_COLORS_MAP_COLOR =
            List.of(MapColor.FIRE, MapColor.TERRACOTTA_YELLOW, MapColor.GOLD, MapColor.COLOR_LIGHT_GREEN,
                    MapColor.EMERALD, MapColor.EMERALD, MapColor.DIAMOND, MapColor.DIAMOND,
                    MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_PURPLE, MapColor.COLOR_PURPLE, MapColor.COLOR_MAGENTA,
                    MapColor.COLOR_PINK, MapColor.STONE, MapColor.METAL, MapColor.SNOW);

    public static final List<Item> CONCRETES =
            List.of(Items.WHITE_CONCRETE, Items.LIGHT_GRAY_CONCRETE, Items.GRAY_CONCRETE, Items.BLACK_CONCRETE,
                    Items.BROWN_CONCRETE, Items.RED_CONCRETE, Items.ORANGE_CONCRETE, Items.YELLOW_CONCRETE,
                    Items.LIME_CONCRETE, Items.GREEN_CONCRETE, Items.CYAN_CONCRETE, Items.LIGHT_BLUE_CONCRETE,
                    Items.BLUE_CONCRETE, Items.PURPLE_CONCRETE, Items.MAGENTA_CONCRETE, Items.PINK_CONCRETE);

    public static final List<Block> TERRACOTTAS =
            List.of(Blocks.WHITE_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.BLACK_TERRACOTTA,
                    Blocks.BROWN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA,
                    Blocks.LIME_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA,
                    Blocks.BLUE_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.PINK_TERRACOTTA);

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

    public static final Supplier<Block> FERN = supplier(Blocks.FERN);
    public static final Supplier<Block> OPEN_EYEBLOSSOM = supplier(Blocks.OPEN_EYEBLOSSOM);
    public static final Supplier<Block> CLOSED_EYEBLOSSOM = supplier(Blocks.CLOSED_EYEBLOSSOM);
    public static final List<Supplier<Block>> POTTABLE_PLANTS = new ArrayList<>();

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

        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            ModBlocks.COLORED_POTTED_PLANTS.forEach((block, deferredBlocks) ->
                    ModBlocks.COLORED_FLOWER_POTS.get(index).get().addPlant(BuiltInRegistries.BLOCK.getKey(block.get()), deferredBlocks.get(index)));
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

    public static <T extends Block> String name(Supplier<T> block) {
        if(block instanceof DeferredBlock<T> deferredBlock) {
            return deferredBlock.getId().getPath();
        }
        else {
            return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
        }
    }

    public static Supplier<Block> supplier(Block block) {
        return () -> block;
    }

    @SafeVarargs
    public static <T extends Block> Block[] asVarArgs(List<DeferredBlock<T>> deferredBlocks, List<DeferredBlock<T>>... optionals) {
        // https://stackoverflow.com/questions/9863742/how-to-pass-an-arraylist-to-a-varargs-method-parameter
        if(optionals.length == 0)
            return deferredBlocks.stream().map(DeferredHolder::get).toArray(Block[]::new);
        else {
            List<DeferredBlock<T>> copy = new ArrayList<>(deferredBlocks);
            for (List<DeferredBlock<T>> list : optionals) {
                copy.addAll(list);
            }
            return copy.stream().map(DeferredHolder::get).toArray(Block[]::new);
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
            if(block instanceof FlowerBlock || block instanceof SaplingBlock) {
                if (block == Blocks.OPEN_EYEBLOSSOM) {
                    POTTABLE_PLANTS.add(OPEN_EYEBLOSSOM);
                }
                else if (block == Blocks.CLOSED_EYEBLOSSOM) {
                    POTTABLE_PLANTS.add(CLOSED_EYEBLOSSOM);
                }
                else {
                    POTTABLE_PLANTS.add(supplier(block));
                }
            }
        }
        POTTABLE_PLANTS.addAll(List.of(
                FERN,
                supplier(Blocks.RED_MUSHROOM),
                supplier(Blocks.BROWN_MUSHROOM),
                supplier(Blocks.DEAD_BUSH),
                supplier(Blocks.CACTUS),
                supplier(Blocks.BAMBOO),
                supplier(Blocks.CRIMSON_FUNGUS),
                supplier(Blocks.WARPED_FUNGUS),
                supplier(Blocks.CRIMSON_ROOTS),
                supplier(Blocks.WARPED_ROOTS),
                supplier(Blocks.AZALEA),
                supplier(Blocks.FLOWERING_AZALEA)
        ));
    }
}
