package net.theobl.worldofcolor.datagen;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.theobl.worldofcolor.block.ModBlocks;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class ModBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();
    private static final String RECIPE_GROUP_PREFIX_WOODEN = "wooden";
    private static final String RECIPE_UNLOCKED_BY_HAS_PLANKS = "has_planks";
    public static final ColorCollection<BlockFamily> COLORED_PLANKS = createFamily(color ->
            familyBuilder(ModBlocks.COLORED_PLANKS.pick(color).get())
                    .log(ModBlocks.COLORED_LOGS.pick(color).get())
                    .strippedLog(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get())
                    .button(ModBlocks.COLORED_BUTTONS.pick(color).get())
                    .fence(ModBlocks.COLORED_FENCES.pick(color).get())
                    .fenceGate(ModBlocks.COLORED_FENCE_GATES.pick(color).get())
                    .hangingSign(ModBlocks.COLORED_HANGING_SIGNS.pick(color).get(), ModBlocks.COLORED_WALL_HANGING_SIGNS.pick(color).get())
                    .pressurePlate(ModBlocks.COLORED_PRESSURE_PLATES.pick(color).get())
                    .sign(ModBlocks.COLORED_SIGNS.pick(color).get(), ModBlocks.COLORED_WALL_SIGNS.pick(color).get())
                    .slab(ModBlocks.COLORED_SLABS.pick(color).get())
                    .stairs(ModBlocks.COLORED_STAIRS.pick(color).get())
                    .door(ModBlocks.COLORED_DOORS.pick(color).get())
                    .trapdoor(ModBlocks.COLORED_TRAPDOORS.pick(color).get())
                    .recipeGroupPrefix(RECIPE_GROUP_PREFIX_WOODEN)
                    .recipeUnlockedBy(RECIPE_UNLOCKED_BY_HAS_PLANKS)
                    .getFamily()
    );
    public static final ColorCollection<BlockFamily> COLORED_BRICKS = createFamily(color ->
            familyBuilder(ModBlocks.COLORED_BRICKS.pick(color).get())
                    .wall(ModBlocks.COLORED_BRICK_WALLS.pick(color).get())
                    .stairs(ModBlocks.COLORED_BRICK_STAIRS.pick(color).get())
                    .slab(ModBlocks.COLORED_BRICK_SLABS.pick(color).get())
                    .generateStonecutterRecipe()
                    .getFamily()
    );
    public static final ColorCollection<BlockFamily> COLORED_COPPER_BLOCK = createFamily(color ->
            familyBuilder(ModBlocks.COLORED_COPPER_BLOCKS.pick(color).get()).cut(ModBlocks.COLORED_CUT_COPPER.pick(color).get()).dontGenerateModel().getFamily()
    );
    public static final ColorCollection<BlockFamily> COLORED_CUT_COPPER = createFamily(color ->
            familyBuilder(ModBlocks.COLORED_CUT_COPPER.pick(color).get())
                    .slab(ModBlocks.COLORED_CUT_COPPER_SLABS.pick(color).get())
                    .stairs(ModBlocks.COLORED_CUT_COPPER_STAIRS.pick(color).get())
                    .chiseled(ModBlocks.COLORED_CHISELED_COPPER.pick(color).get())
                    .dontGenerateModel()
                    .generateStonecutterRecipe()
                    .getFamily()
    );
    public static final ColorCollection<BlockFamily> COLORED_WAXED_COPPER_BLOCK = createFamily(color ->
            familyBuilder(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color).get())
                    .cut(ModBlocks.COLORED_WAXED_CUT_COPPER.pick(color).get())
                    .recipeGroupPrefix("waxed_cut_copper")
                    .dontGenerateModel()
                    .getFamily()
    );
    public static final ColorCollection<BlockFamily> COLORED_WAXED_CUT_COPPER = createFamily(color ->
            familyBuilder(ModBlocks.COLORED_WAXED_CUT_COPPER.pick(color).get())
                    .slab(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.pick(color).get())
                    .stairs(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.pick(color).get())
                    .chiseled(ModBlocks.COLORED_WAXED_CHISELED_COPPER.pick(color).get())
                    .recipeGroupPrefix("waxed_cut_copper")
                    .dontGenerateModel()
                    .generateStonecutterRecipe()
                    .getFamily()
    );

    public static ColorCollection<BlockFamily> createFamily(Function<DyeColor, BlockFamily> colorProvider) {
        return ColorCollection.VALUES.map(colorProvider);
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
