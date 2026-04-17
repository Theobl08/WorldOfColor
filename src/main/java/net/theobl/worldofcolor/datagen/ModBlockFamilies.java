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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class ModBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();
    private static final String RECIPE_GROUP_PREFIX_WOODEN = "wooden";
    private static final String RECIPE_UNLOCKED_BY_HAS_PLANKS = "has_planks";
    public static final Map<DyeColor, BlockFamily> COLORED_PLANKS = familyBuilderColored(color ->
            familyBuilder(ModBlocks.COLORED_PLANKS.get(color).get())
                    .button(ModBlocks.COLORED_BUTTONS.get(color).get())
                    .fence(ModBlocks.COLORED_FENCES.get(color).get())
                    .fenceGate(ModBlocks.COLORED_FENCE_GATES.get(color).get())
                    .pressurePlate(ModBlocks.COLORED_PRESSURE_PLATES.get(color).get())
                    .sign(ModBlocks.COLORED_SIGNS.get(color).get(), ModBlocks.COLORED_WALL_SIGNS.get(color).get())
                    .slab(ModBlocks.COLORED_SLABS.get(color).get())
                    .stairs(ModBlocks.COLORED_STAIRS.get(color).get())
                    .door(ModBlocks.COLORED_DOORS.get(color).get())
                    .trapdoor(ModBlocks.COLORED_TRAPDOORS.get(color).get())
                    .recipeGroupPrefix(RECIPE_GROUP_PREFIX_WOODEN)
                    .recipeUnlockedBy(RECIPE_UNLOCKED_BY_HAS_PLANKS)
                    .getFamily()
    );
    public static final Map<DyeColor, BlockFamily> COLORED_BRICKS = familyBuilderColored(color ->
            familyBuilder(ModBlocks.COLORED_BRICKS.get(color).get())
                    .wall(ModBlocks.COLORED_BRICK_WALLS.get(color).get())
                    .stairs(ModBlocks.COLORED_BRICK_STAIRS.get(color).get())
                    .slab(ModBlocks.COLORED_BRICK_SLABS.get(color).get())
                    .getFamily()
    );
    public static final Map<DyeColor, BlockFamily> COLORED_COPPER_BLOCK = familyBuilderColored(color ->
            familyBuilder(ModBlocks.COLORED_COPPER_BLOCKS.get(color).get()).cut(ModBlocks.COLORED_CUT_COPPER.get(color).get()).dontGenerateModel().getFamily()
    );
    public static final Map<DyeColor, BlockFamily> COLORED_CUT_COPPER = familyBuilderColored(color ->
            familyBuilder(ModBlocks.COLORED_CUT_COPPER.get(color).get())
                    .slab(ModBlocks.COLORED_CUT_COPPER_SLABS.get(color).get())
                    .stairs(ModBlocks.COLORED_CUT_COPPER_STAIRS.get(color).get())
                    .chiseled(ModBlocks.COLORED_CHISELED_COPPER.get(color).get())
                    .dontGenerateModel()
                    .getFamily()
    );
    public static final Map<DyeColor, BlockFamily> COLORED_WAXED_COPPER_BLOCK = familyBuilderColored(color ->
            familyBuilder(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.get(color).get())
                    .cut(ModBlocks.COLORED_WAXED_CUT_COPPER.get(color).get())
                    .recipeGroupPrefix("waxed_cut_copper")
                    .dontGenerateModel()
                    .getFamily()
    );
    public static final Map<DyeColor, BlockFamily> COLORED_WAXED_CUT_COPPER = familyBuilderColored(color ->
            familyBuilder(ModBlocks.COLORED_WAXED_CUT_COPPER.get(color).get())
                    .slab(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(color).get())
                    .stairs(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(color).get())
                    .chiseled(ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(color).get())
                    .recipeGroupPrefix("waxed_cut_copper")
                    .dontGenerateModel()
                    .getFamily()
    );

    private static Map<DyeColor, BlockFamily> familyBuilderColored(Function<DyeColor, BlockFamily> factory) {
        Map<DyeColor, BlockFamily> families = new LinkedHashMap<>();
        for (DyeColor color : ModUtil.COLORS) {
            families.put(color, factory.apply(color));
        }
        return families;
    }

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
