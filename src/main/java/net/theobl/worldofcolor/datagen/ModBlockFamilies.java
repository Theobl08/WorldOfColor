package net.theobl.worldofcolor.datagen;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ModBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();
    private static final String RECIPE_GROUP_PREFIX_WOODEN = "wooden";
    private static final String RECIPE_UNLOCKED_BY_HAS_PLANKS = "has_planks";
    public static final List<BlockFamily> COLORED_PLANKS = coloredPlanksFamilyBuilder();
    public static final List<BlockFamily> COLORED_BRICKS = coloredBricksFamilyBuilder();
    public static final List<BlockFamily> COLORED_COPPER_BLOCK = coloredCopperBlockFamilyBuilder();
    public static final List<BlockFamily> COLORED_CUT_COPPER = coloredCutCopperFamilyBuilder();
    public static final List<BlockFamily> COLORED_WAXED_COPPER_BLOCK = coloredWaxedCopperBlockFamilyBuilder();
    public static final List<BlockFamily> COLORED_WAXED_CUT_COPPER = coloredWaxedCutCopperFamilyBuilder();

    private static List<BlockFamily> coloredPlanksFamilyBuilder() {
        List<BlockFamily> families = new ArrayList<>();
        for (DeferredBlock<Block> planks : ModBlocks.COLORED_PLANKS.values()) {
            DyeColor index = ModBlocks.COLORED_PLANKS.inverse().get(planks);
            BlockFamily family = familyBuilder(planks.get())
                    .button(ModBlocks.COLORED_BUTTONS.get(index).get())
                    .fence(ModBlocks.COLORED_FENCES.get(index).get())
                    .fenceGate(ModBlocks.COLORED_FENCE_GATES.get(index).get())
                    .pressurePlate(ModBlocks.COLORED_PRESSURE_PLATES.get(index).get())
                    .sign(ModBlocks.COLORED_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_SIGNS.get(index).get())
                    .slab(ModBlocks.COLORED_SLABS.get(index).get())
                    .stairs(ModBlocks.COLORED_STAIRS.get(index).get())
                    .door(ModBlocks.COLORED_DOORS.get(index).get())
                    .trapdoor(ModBlocks.COLORED_TRAPDOORS.get(index).get())
                    .recipeGroupPrefix(RECIPE_GROUP_PREFIX_WOODEN)
                    .recipeUnlockedBy(RECIPE_UNLOCKED_BY_HAS_PLANKS)
                    .getFamily();
            families.add(family);
        }
        return families;
    }

    private static List<BlockFamily> coloredBricksFamilyBuilder() {
        List<BlockFamily> families = new ArrayList<>();
        for (DeferredBlock<Block> block : ModBlocks.COLORED_BRICKS.values()) {
            DyeColor index = ModBlocks.COLORED_BRICKS.inverse().get(block);
            BlockFamily family = familyBuilder(block.get())
                    .wall(ModBlocks.COLORED_BRICK_WALLS.get(index).get())
                    .stairs(ModBlocks.COLORED_BRICK_STAIRS.get(index).get())
                    .slab(ModBlocks.COLORED_BRICK_SLABS.get(index).get())
                    .getFamily();
            families.add(family);
        }
        return families;
    }

    private static List<BlockFamily> coloredCopperBlockFamilyBuilder() {
        List<BlockFamily> families = new ArrayList<>();
        for (DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BLOCKS.values()) {
            DyeColor index = ModBlocks.COLORED_COPPER_BLOCKS.inverse().get(block);
            BlockFamily family = familyBuilder(block.get()).cut(ModBlocks.COLORED_CUT_COPPER.get(index).get()).dontGenerateModel().getFamily();
            families.add(family);
        }
        return families;
    }

    private static List<BlockFamily> coloredCutCopperFamilyBuilder() {
        List<BlockFamily> families = new ArrayList<>();
        for (DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER.values()) {
            DyeColor index = ModBlocks.COLORED_CUT_COPPER.inverse().get(block);
            BlockFamily family = familyBuilder(block.get())
                    .slab(ModBlocks.COLORED_CUT_COPPER_SLABS.get(index).get())
                    .stairs(ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index).get())
                    .chiseled(ModBlocks.COLORED_CHISELED_COPPER.get(index).get())
                    .dontGenerateModel()
                    .getFamily();
            families.add(family);
        }
        return families;
    }

    private static List<BlockFamily> coloredWaxedCopperBlockFamilyBuilder() {
        List<BlockFamily> families = new ArrayList<>();
        for (DeferredBlock<Block> block : ModBlocks.COLORED_WAXED_COPPER_BLOCKS.values()) {
            DyeColor index = ModBlocks.COLORED_WAXED_COPPER_BLOCKS.inverse().get(block);
            BlockFamily family = familyBuilder(block.get())
                    .cut(ModBlocks.COLORED_WAXED_CUT_COPPER.get(index).get())
                    .recipeGroupPrefix("waxed_cut_copper")
                    .dontGenerateModel()
                    .getFamily();
            families.add(family);
        }
        return families;
    }

    private static List<BlockFamily> coloredWaxedCutCopperFamilyBuilder() {
        List<BlockFamily> families = new ArrayList<>();
        for (DeferredBlock<Block> block : ModBlocks.COLORED_WAXED_CUT_COPPER.values()) {
            DyeColor index = ModBlocks.COLORED_WAXED_CUT_COPPER.inverse().get(block);
            BlockFamily family = familyBuilder(block.get())
                    .slab(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index).get())
                    .stairs(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index).get())
                    .chiseled(ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index).get())
                    .recipeGroupPrefix("waxed_cut_copper")
                    .dontGenerateModel()
                    .getFamily();
            families.add(family);
        }
        return families;
    }

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
