package net.theobl.worldofcolor.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.theobl.worldofcolor.block.ModBlocks;
import org.apache.commons.lang3.function.TriFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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

    public static final DeferredBlock<Block> FERN = deferredBlock(Blocks.FERN);
    public static final DeferredBlock<Block> OPEN_EYEBLOSSOM = deferredBlock(Blocks.OPEN_EYEBLOSSOM);
    public static final DeferredBlock<Block> CLOSED_EYEBLOSSOM = deferredBlock(Blocks.CLOSED_EYEBLOSSOM);
    public static final List<DeferredBlock<Block>> POTTABLE_PLANTS = new ArrayList<>();

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

        registerFlammable(ModBlocks.RGB_WOOL.get(), 30, 60);
        registerFlammable(ModBlocks.RGB_CARPET.get(), 60, 20);

        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            ModBlocks.COLORED_POTTED_PLANTS.forEach((block, deferredBlocks) ->
                    ModBlocks.COLORED_FLOWER_POTS.pick(color).get().addPlant(block.getId(), deferredBlocks.get(color)));
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

    private static <T extends Block> DeferredBlock<T> deferredBlock(T block) {
        return DeferredBlock.createBlock(BuiltInRegistries.BLOCK.getKey(block));
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
                    POTTABLE_PLANTS.add(deferredBlock(block));
                }
            }
        }
        POTTABLE_PLANTS.addAll(List.of(
                FERN,
                deferredBlock(Blocks.RED_MUSHROOM),
                deferredBlock(Blocks.BROWN_MUSHROOM),
                deferredBlock(Blocks.DEAD_BUSH),
                deferredBlock(Blocks.CACTUS),
                deferredBlock(Blocks.BAMBOO),
                deferredBlock(Blocks.CRIMSON_FUNGUS),
                deferredBlock(Blocks.WARPED_FUNGUS),
                deferredBlock(Blocks.CRIMSON_ROOTS),
                deferredBlock(Blocks.WARPED_ROOTS),
                deferredBlock(Blocks.AZALEA),
                deferredBlock(Blocks.FLOWERING_AZALEA)
        ));
    }
}
