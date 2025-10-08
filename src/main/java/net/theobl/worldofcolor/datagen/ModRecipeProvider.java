package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.tags.ModTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        generateForEnabledBlockFamilies(FeatureFlagSet.of(FeatureFlags.VANILLA));
        waxRecipes(output, ModBlocks.COLORED_COPPER_BLOCKS, ModBlocks.COLORED_WAXED_COPPER_BLOCKS);
        waxRecipes(output, ModBlocks.COLORED_CHISELED_COPPER, ModBlocks.COLORED_WAXED_CHISELED_COPPER);
        waxRecipes(output, ModBlocks.COLORED_COPPER_GRATES, ModBlocks.COLORED_WAXED_COPPER_GRATES);
        waxRecipes(output, ModBlocks.COLORED_CUT_COPPER, ModBlocks.COLORED_WAXED_CUT_COPPER);
        waxRecipes(output, ModBlocks.COLORED_CUT_COPPER_STAIRS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS);
        waxRecipes(output, ModBlocks.COLORED_CUT_COPPER_SLABS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS);
        waxRecipes(output, ModBlocks.COLORED_COPPER_DOORS, ModBlocks.COLORED_WAXED_COPPER_DOORS);
        waxRecipes(output, ModBlocks.COLORED_COPPER_TRAPDOORS, ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS);
        waxRecipes(output, ModBlocks.COLORED_COPPER_BULBS, ModBlocks.COLORED_WAXED_COPPER_BULBS);
        waxRecipes(output, ModBlocks.COLORED_COPPER_BARS, ModBlocks.COLORED_WAXED_COPPER_BARS);
        waxRecipes(output, ModBlocks.COLORED_COPPER_CHAINS, ModBlocks.COLORED_WAXED_COPPER_CHAINS);
        waxRecipes(output, ModBlocks.COLORED_COPPER_LANTERNS, ModBlocks.COLORED_WAXED_COPPER_LANTERNS);
        waxRecipes(output, ModBlocks.COLORED_COPPER_CHESTS, ModBlocks.COLORED_WAXED_COPPER_CHESTS);
        waxRecipes(output, ModBlocks.COLORED_COPPER_GOLEM_STATUES, ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES);
        waxRecipes(output, ModBlocks.COLORED_LIGHTNING_RODS, ModBlocks.COLORED_WAXED_LIGHTNING_RODS);

        colorBlockWithDye(output, ModBlocks.COLORED_SAPLINGS, DYES, ItemTags.SAPLINGS, "sapling");
        colorBlockWithDye(output, ModBlocks.COLORED_CAULDRONS, DYES, ModTags.Items.CAULDRONS, "dyed_cauldron");
        colorBlockWithDye(output, ModBlocks.COLORED_SLIME_BLOCKS, DYES, Tags.Items.STORAGE_BLOCKS_SLIME, "dyed_slime_blocks");

        for (DeferredBlock<Block> block : ModBlocks.COLORED_BRICKS) {
            int index = ModBlocks.COLORED_BRICKS.indexOf(block);
            coloredBricksFromBricksAndDye(output, block, DYES.get(index));
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_BRICK_SLABS.get(index), block, 2);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_BRICK_STAIRS.get(index), block);
            stonecutterResultFromBase(output, RecipeCategory.DECORATIONS, ModBlocks.COLORED_BRICK_WALLS.get(index), block);
        }
        for (DeferredBlock<Block> quiltedConcrete : ModBlocks.QUILTED_CONCRETES) {
            int index = ModBlocks.QUILTED_CONCRETES.indexOf(quiltedConcrete);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, quiltedConcrete.get(), CONCRETES.get(index));
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, CONCRETES.get(index), quiltedConcrete.get());
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIMPLE_COLORED_BLOCKS.get(index), quiltedConcrete.get());
        }
        for (DeferredBlock<Block> block : ModBlocks.SIMPLE_COLORED_BLOCKS) {
            int index = ModBlocks.SIMPLE_COLORED_BLOCKS.indexOf(block);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, block.get(), CONCRETES.get(index));
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, CONCRETES.get(index), block.get());
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUILTED_CONCRETES.get(index), block.get());
        }
        for (DeferredBlock<Block> glazedConcrete : ModBlocks.GLAZED_CONCRETES) {
            int index = ModBlocks.GLAZED_CONCRETES.indexOf(glazedConcrete);
            smeltingResultFromBase(glazedConcrete.get(), CONCRETES.get(index));
        }
        for (DeferredBlock<Block> carpet : ModBlocks.CLASSIC_CARPETS) {
            int index = ModBlocks.CLASSIC_CARPETS.indexOf(carpet);
            carpet(carpet.get(), ModBlocks.CLASSIC_WOOLS.get(index));
        }

        for (DeferredBlock<Block> planks : ModBlocks.COLORED_PLANKS) {
            int index = ModBlocks.COLORED_PLANKS.indexOf(planks);
            planksFromLogs(planks, ModTags.Items.COLORED_LOGS.get(index), 4);
            woodFromLogs(ModBlocks.COLORED_WOODS.get(index), ModBlocks.COLORED_LOGS.get(index));
            woodFromLogs(ModBlocks.COLORED_STRIPPED_WOODS.get(index), ModBlocks.COLORED_STRIPPED_LOGS.get(index));
            hangingSign(ModBlocks.COLORED_HANGING_SIGNS.get(index), ModBlocks.COLORED_STRIPPED_LOGS.get(index));
            woodenBoat(ModItems.COLORED_BOATS.get(index).get(), planks);
            chestBoat(ModItems.COLORED_CHEST_BOATS.get(index).get(), ModItems.COLORED_BOATS.get(index).get());
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BLOCKS) {
            int index = ModBlocks.COLORED_COPPER_BLOCKS.indexOf(block);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER.get(index), block, 4);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index), block, 4);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_SLABS.get(index), block, 8);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CHISELED_COPPER.get(index), block, 4);
            grate(ModBlocks.COLORED_COPPER_GRATES.get(index).get(), block.get());
            copperBulb(ModBlocks.COLORED_COPPER_BULBS.get(index).get(), block.get());
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_COPPER_GRATES.get(index), block, 4);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_WAXED_COPPER_BLOCKS) {
            int index = ModBlocks.COLORED_WAXED_COPPER_BLOCKS.indexOf(block);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER.get(index), block, 4);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index), block, 4);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index), block, 8);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index), block, 4);
            grate(ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index).get(), block.get());
            copperBulb(ModBlocks.COLORED_WAXED_COPPER_BULBS.get(index).get(), block.get());
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index), block, 4);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER) {
            int index = ModBlocks.COLORED_CUT_COPPER.indexOf(block);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_SLABS.get(index), block, 2);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index), block);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CHISELED_COPPER.get(index), block);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_WAXED_CUT_COPPER) {
            int index = ModBlocks.COLORED_WAXED_CUT_COPPER.indexOf(block);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index), block, 2);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index), block);
            stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index), block);
        }

        shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_COPPER_BLOCKS.getFirst())
                .requires(Blocks.COPPER_BLOCK)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
        shapeless(RecipeCategory.REDSTONE, ModBlocks.COLORED_COPPER_DOORS.getFirst())
                .requires(Blocks.COPPER_DOOR)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
        shapeless(RecipeCategory.REDSTONE, ModBlocks.COLORED_COPPER_TRAPDOORS.getFirst())
                .requires(Blocks.COPPER_TRAPDOOR)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
        shapeless(RecipeCategory.REDSTONE, ModBlocks.COLORED_LIGHTNING_RODS.getFirst())
                .requires(Blocks.LIGHTNING_ROD)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
        shapeless(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_BARS.getFirst())
                .requires(Blocks.COPPER_BARS.unaffected())
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
        shapeless(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_CHAINS.getFirst())
                .requires(Blocks.COPPER_CHAIN.unaffected())
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
        shapeless(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_LANTERNS.getFirst())
                .requires(Blocks.COPPER_LANTERN.unaffected())
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
        shapeless(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_CHESTS.getFirst())
                .requires(Blocks.COPPER_CHEST)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
        shapeless(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_GOLEM_STATUES.getFirst())
                .requires(Blocks.COPPER_GOLEM_STATUE)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
    }

    protected void generateForEnabledBlockFamilies(FeatureFlagSet set) {
        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(family -> generateRecipes(family, set));
    }

    protected void stonecutterResultFromBase(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material) {
        stonecutterResultFromBase(recipeOutput, category, result, material, 1);
    }

    protected void stonecutterResultFromBase(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, WorldOfColor.MODID + ":" + getConversionRecipeName(result, material) + "_stonecutting");
    }

    protected void colorBlockWithDye(RecipeOutput recipeOutput, List<DeferredBlock<Block>> results, List<Item> dyes, TagKey<Item> dyeableItems, String group) {
        for (int i = 0; i < dyes.size(); i++) {
            Item item = dyes.get(i);
            Item item1 = results.get(i).asItem();
            shapeless(RecipeCategory.BUILDING_BLOCKS, item1)
                    .requires(item)
                    .requires(dyeableItems)
                    .group(group)
                    .unlockedBy("has_needed_dye", has(item))
                    .save(recipeOutput, WorldOfColor.MODID + ":dye_" + getItemName(item1));
        }
    }

    protected void coloredBricksFromBricksAndDye(RecipeOutput recipeOutput, ItemLike bricks, ItemLike dye) {
        shaped(RecipeCategory.BUILDING_BLOCKS, bricks, 8)
                .define('#', Blocks.BRICKS)
                .define('X', dye)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .group("stained_bricks")
                .unlockedBy("has_bricks", has(Blocks.BRICKS))
                .save(recipeOutput);
    }

    protected void waxRecipes(RecipeOutput recipeOutput, List<DeferredBlock<Block>> block, List<DeferredBlock<Block>> waxedBlock) {
        COLORS.forEach(color ->
        {
            int index = COLORS.indexOf(color);
             shapeless(RecipeCategory.BUILDING_BLOCKS, waxedBlock.get(index))
                     .requires(block.get(index))
                     .requires(Items.HONEYCOMB)
                     .group(getItemName(waxedBlock.get(index)))
                     .unlockedBy(getHasName(block.get(index)), has(block.get(index)))
                     .save(recipeOutput, WorldOfColor.MODID + ":" + getConversionRecipeName(waxedBlock.get(index), Items.HONEYCOMB));
        });
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "World of Color Recipes";
        }
    }
}
