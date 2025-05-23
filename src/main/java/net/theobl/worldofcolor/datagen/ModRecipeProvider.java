package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.tags.ModTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        generateForEnabledBlockFamilies(recipeOutput, FeatureFlagSet.of(FeatureFlags.VANILLA));
        waxRecipes(recipeOutput, ModBlocks.COLORED_COPPER_BLOCKS, ModBlocks.COLORED_WAXED_COPPER_BLOCKS);
        waxRecipes(recipeOutput, ModBlocks.COLORED_CHISELED_COPPER, ModBlocks.COLORED_WAXED_CHISELED_COPPER);
        waxRecipes(recipeOutput, ModBlocks.COLORED_COPPER_GRATES, ModBlocks.COLORED_WAXED_COPPER_GRATES);
        waxRecipes(recipeOutput, ModBlocks.COLORED_CUT_COPPER, ModBlocks.COLORED_WAXED_CUT_COPPER);
        waxRecipes(recipeOutput, ModBlocks.COLORED_CUT_COPPER_STAIRS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS);
        waxRecipes(recipeOutput, ModBlocks.COLORED_CUT_COPPER_SLABS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS);
        waxRecipes(recipeOutput, ModBlocks.COLORED_COPPER_DOORS, ModBlocks.COLORED_WAXED_COPPER_DOORS);
        waxRecipes(recipeOutput, ModBlocks.COLORED_COPPER_TRAPDOORS, ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS);
        waxRecipes(recipeOutput, ModBlocks.COLORED_COPPER_BULBS, ModBlocks.COLORED_WAXED_COPPER_BULBS);

        for (DeferredBlock<Block> quiltedConcrete : ModBlocks.QUILTED_CONCRETES) {
            int index = ModBlocks.QUILTED_CONCRETES.indexOf(quiltedConcrete);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, quiltedConcrete.get(), CONCRETES.get(index));
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, CONCRETES.get(index), quiltedConcrete.get());
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIMPLE_COLORED_BLOCKS.get(index), quiltedConcrete.get());
        }
        for (DeferredBlock<Block> block : ModBlocks.SIMPLE_COLORED_BLOCKS) {
            int index = ModBlocks.SIMPLE_COLORED_BLOCKS.indexOf(block);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, block.get(), CONCRETES.get(index));
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, CONCRETES.get(index), block.get());
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUILTED_CONCRETES.get(index), block.get());
        }
        for (DeferredBlock<Block> glazedConcrete : ModBlocks.GLAZED_CONCRETES) {
            int index = ModBlocks.GLAZED_CONCRETES.indexOf(glazedConcrete);
            smeltingResultFromBase(recipeOutput, glazedConcrete.get(), CONCRETES.get(index));
        }
        for (DeferredBlock<Block> carpet : ModBlocks.CLASSIC_CARPETS) {
            int index = ModBlocks.CLASSIC_CARPETS.indexOf(carpet);
            carpet(recipeOutput, carpet.get(), ModBlocks.CLASSIC_WOOLS.get(index));
        }

        for (DeferredBlock<Block> planks : ModBlocks.COLORED_PLANKS) {
            int index = ModBlocks.COLORED_PLANKS.indexOf(planks);
            planksFromLogs(recipeOutput, planks, ModTags.Items.COLORED_LOGS.get(index), 4);
            woodFromLogs(recipeOutput, ModBlocks.COLORED_WOODS.get(index), ModBlocks.COLORED_LOGS.get(index));
            woodFromLogs(recipeOutput, ModBlocks.COLORED_STRIPPED_WOODS.get(index), ModBlocks.COLORED_STRIPPED_LOGS.get(index));
            hangingSign(recipeOutput, ModBlocks.COLORED_HANGING_SIGNS.get(index), ModBlocks.COLORED_STRIPPED_LOGS.get(index));
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BLOCKS) {
            int index = ModBlocks.COLORED_COPPER_BLOCKS.indexOf(block);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER.get(index), block, 4);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index), block, 4);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_SLABS.get(index), block, 8);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CHISELED_COPPER.get(index), block, 4);
            grate(recipeOutput, ModBlocks.COLORED_COPPER_GRATES.get(index).get(), block.get());
            copperBulb(recipeOutput, ModBlocks.COLORED_COPPER_BULBS.get(index).get(), block.get());
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_COPPER_GRATES.get(index), block, 4);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_WAXED_COPPER_BLOCKS) {
            int index = ModBlocks.COLORED_WAXED_COPPER_BLOCKS.indexOf(block);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER.get(index), block, 4);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index), block, 4);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index), block, 8);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index), block, 4);
            grate(recipeOutput, ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index).get(), block.get());
            copperBulb(recipeOutput, ModBlocks.COLORED_WAXED_COPPER_BULBS.get(index).get(), block.get());
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index), block, 4);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER) {
            int index = ModBlocks.COLORED_CUT_COPPER.indexOf(block);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_SLABS.get(index), block, 2);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index), block);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CHISELED_COPPER.get(index), block);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_WAXED_CUT_COPPER) {
            int index = ModBlocks.COLORED_WAXED_CUT_COPPER.indexOf(block);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index), block, 2);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index), block);
            stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index), block);
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_COPPER_BLOCKS.getFirst())
                .requires(Blocks.COPPER_BLOCK)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Blocks.COPPER_BLOCK), has(Items.WHITE_DYE))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.COLORED_COPPER_DOORS.getFirst())
                .requires(Blocks.COPPER_DOOR)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Blocks.COPPER_DOOR), has(Items.WHITE_DYE))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.COLORED_COPPER_TRAPDOORS.getFirst())
                .requires(Blocks.COPPER_TRAPDOOR)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Blocks.COPPER_TRAPDOOR), has(Items.WHITE_DYE))
                .save(recipeOutput);
    }

    protected void generateForEnabledBlockFamilies(RecipeOutput enabledFeatures, FeatureFlagSet set) {
        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(family -> generateRecipes(enabledFeatures, family, set));
    }

    protected static void stonecutterResultFromBase(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material) {
        stonecutterResultFromBase(recipeOutput, category, result, material, 1);
    }

    protected static void stonecutterResultFromBase(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, WorldOfColor.MODID + ":" + getConversionRecipeName(result, material) + "_stonecutting");
    }

    protected static void waxRecipes(RecipeOutput recipeOutput, List<DeferredBlock<Block>> block, List<DeferredBlock<Block>> waxedBlock) {
        COLORS.forEach(color ->
        {
            int index = COLORS.indexOf(color);
             ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, waxedBlock.get(index))
                     .requires(block.get(index))
                     .requires(Items.HONEYCOMB)
                     .group(getItemName(waxedBlock.get(index)))
                     .unlockedBy(getHasName(block.get(index)), has(block.get(index)))
                     .save(recipeOutput, WorldOfColor.MODID + ":" + getConversionRecipeName(waxedBlock.get(index), Items.HONEYCOMB));
        });
    }
}
