package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.item.crafting.ColoredDecoratedPotRecipe;
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
        waxRecipes(ModBlocks.COLORED_COPPER_BLOCKS, ModBlocks.COLORED_WAXED_COPPER_BLOCKS);
        waxRecipes(ModBlocks.COLORED_CHISELED_COPPER, ModBlocks.COLORED_WAXED_CHISELED_COPPER);
        waxRecipes(ModBlocks.COLORED_COPPER_GRATES, ModBlocks.COLORED_WAXED_COPPER_GRATES);
        waxRecipes(ModBlocks.COLORED_CUT_COPPER, ModBlocks.COLORED_WAXED_CUT_COPPER);
        waxRecipes(ModBlocks.COLORED_CUT_COPPER_STAIRS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS);
        waxRecipes(ModBlocks.COLORED_CUT_COPPER_SLABS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS);
        waxRecipes(ModBlocks.COLORED_COPPER_DOORS, ModBlocks.COLORED_WAXED_COPPER_DOORS);
        waxRecipes(ModBlocks.COLORED_COPPER_TRAPDOORS, ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS);
        waxRecipes(ModBlocks.COLORED_COPPER_BULBS, ModBlocks.COLORED_WAXED_COPPER_BULBS);
        waxRecipes(ModBlocks.COLORED_COPPER_BARS, ModBlocks.COLORED_WAXED_COPPER_BARS);
        waxRecipes(ModBlocks.COLORED_COPPER_CHAINS, ModBlocks.COLORED_WAXED_COPPER_CHAINS);
        waxRecipes(ModBlocks.COLORED_COPPER_LANTERNS, ModBlocks.COLORED_WAXED_COPPER_LANTERNS);
        waxRecipes(ModBlocks.COLORED_COPPER_CHESTS, ModBlocks.COLORED_WAXED_COPPER_CHESTS);
        waxRecipes(ModBlocks.COLORED_COPPER_GOLEM_STATUES, ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES);
        waxRecipes(ModBlocks.COLORED_LIGHTNING_RODS, ModBlocks.COLORED_WAXED_LIGHTNING_RODS);

        colorBlockWithDye(ModBlocks.COLORED_SAPLINGS, DYES, ItemTags.SAPLINGS, "sapling");
        colorBlockWithDye(ModBlocks.COLORED_CAULDRONS, DYES, ModTags.Items.CAULDRONS, "dyed_cauldron");
        colorBlockWithDye(ModBlocks.COLORED_SLIME_BLOCKS, DYES, Tags.Items.STORAGE_BLOCKS_SLIME, "dyed_slime_blocks");

        for (DeferredBlock<Block> block : ModBlocks.COLORED_BRICKS) {
            int index = ModBlocks.COLORED_BRICKS.indexOf(block);
            coloredBricksFromBricksAndDye(block, DYES.get(index));
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_BRICK_SLABS.get(index), block, 2);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_BRICK_STAIRS.get(index), block);
            stonecutterResultFromBase(RecipeCategory.DECORATIONS, ModBlocks.COLORED_BRICK_WALLS.get(index), block);
        }
        for (DeferredBlock<Block> quiltedConcrete : ModBlocks.QUILTED_CONCRETES) {
            int index = ModBlocks.QUILTED_CONCRETES.indexOf(quiltedConcrete);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, quiltedConcrete.get(), CONCRETES.get(index));
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CONCRETES.get(index), quiltedConcrete.get());
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIMPLE_COLORED_BLOCKS.get(index), quiltedConcrete.get());
        }
        for (DeferredBlock<Block> block : ModBlocks.SIMPLE_COLORED_BLOCKS) {
            int index = ModBlocks.SIMPLE_COLORED_BLOCKS.indexOf(block);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block.get(), CONCRETES.get(index));
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CONCRETES.get(index), block.get());
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUILTED_CONCRETES.get(index), block.get());
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
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER.get(index), block, 4);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index), block, 4);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_SLABS.get(index), block, 8);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CHISELED_COPPER.get(index), block, 4);
            grate(ModBlocks.COLORED_COPPER_GRATES.get(index).get(), block.get());
            copperBulb(ModBlocks.COLORED_COPPER_BULBS.get(index).get(), block.get());
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_COPPER_GRATES.get(index), block, 4);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_WAXED_COPPER_BLOCKS) {
            int index = ModBlocks.COLORED_WAXED_COPPER_BLOCKS.indexOf(block);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER.get(index), block, 4);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index), block, 4);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index), block, 8);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index), block, 4);
            grate(ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index).get(), block.get());
            copperBulb(ModBlocks.COLORED_WAXED_COPPER_BULBS.get(index).get(), block.get());
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index), block, 4);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER) {
            int index = ModBlocks.COLORED_CUT_COPPER.indexOf(block);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_SLABS.get(index), block, 2);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index), block);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CHISELED_COPPER.get(index), block);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_WAXED_CUT_COPPER) {
            int index = ModBlocks.COLORED_WAXED_CUT_COPPER.indexOf(block);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index), block, 2);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index), block);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index), block);
        }

        ModBlocks.COLORED_DECORATED_POTS.forEach(block -> this.shaped(RecipeCategory.DECORATIONS, block.get().asItem())
                .define('#', Items.BRICK)
                .define('D', DyeItem.byColor(block.get().getColor()))
                .pattern(" # ")
                .pattern("#D#")
                .pattern(" # ")
                .unlockedBy("has_brick", this.has(ItemTags.DECORATED_POT_INGREDIENTS))
                .save(this.output, WorldOfColor.MODID + ":" + name(block) + "_simple"));

        colorCopper(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_COPPER_BLOCKS.getFirst(), Blocks.COPPER_BLOCK);
        colorCopper(RecipeCategory.REDSTONE, ModBlocks.COLORED_COPPER_DOORS.getFirst(), Blocks.COPPER_DOOR);
        colorCopper(RecipeCategory.REDSTONE, ModBlocks.COLORED_COPPER_TRAPDOORS.getFirst(), Blocks.COPPER_TRAPDOOR);
        colorCopper(RecipeCategory.REDSTONE, ModBlocks.COLORED_LIGHTNING_RODS.getFirst(), Blocks.LIGHTNING_ROD);
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_BARS.getFirst(), Blocks.COPPER_BARS.unaffected());
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_CHAINS.getFirst(), Blocks.COPPER_CHAIN.unaffected());
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_LANTERNS.getFirst(), Blocks.COPPER_LANTERN.unaffected());
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_CHESTS.getFirst(), Blocks.COPPER_CHEST);
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_GOLEM_STATUES.getFirst(), Blocks.COPPER_GOLEM_STATUE);
        SpecialRecipeBuilder.special(ColoredDecoratedPotRecipe::new).save(this.output, "colored_decorated_pot");

        shapeless(RecipeCategory.MISC, ModItems.RGB_DYE, 7)
                .requires(Items.BLACK_DYE)
                .requires(Items.BLUE_DYE)
                .requires(Items.BROWN_DYE)
                .requires(Items.GREEN_DYE)
                .requires(Items.RED_DYE)
                .requires(Items.WHITE_DYE)
                .requires(Items.YELLOW_DYE)
                .unlockedBy(getHasName(Items.BLACK_DYE), has(Items.BLACK_DYE))
                .unlockedBy(getHasName(Items.BLUE_DYE), has(Items.BLUE_DYE))
                .unlockedBy(getHasName(Items.BROWN_DYE), has(Items.BROWN_DYE))
                .unlockedBy(getHasName(Items.GREEN_DYE), has(Items.GREEN_DYE))
                .unlockedBy(getHasName(Items.RED_DYE), has(Items.RED_DYE))
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .unlockedBy(getHasName(Items.YELLOW_DYE), has(Items.YELLOW_DYE))
                .save(output);
        shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RGB_WOOL)
                .requires(ModItems.RGB_DYE)
                .requires(ItemTags.WOOL)
                .unlockedBy("has_needed_dye", has(ModItems.RGB_DYE))
                .save(output, WorldOfColor.MODID + ":dye_" + getItemName(ModBlocks.RGB_WOOL));
        shapeless(RecipeCategory.DECORATIONS, ModBlocks.RGB_CARPET)
                .requires(ModItems.RGB_DYE)
                .requires(ItemTags.WOOL_CARPETS)
                .group("carpet_dye")
                .unlockedBy("has_needed_dye", has(ModItems.RGB_DYE))
                .save(output, WorldOfColor.MODID + ":dye_" + getItemName(ModBlocks.RGB_CARPET));
        shapeless(RecipeCategory.DECORATIONS, ModBlocks.RGB_BED)
                .requires(ModItems.RGB_DYE)
                .requires(ItemTags.BEDS)
                .group("bed_dye")
                .unlockedBy("has_needed_dye", has(ModItems.RGB_DYE))
                .save(output, WorldOfColor.MODID + ":dye_" + getItemName(ModBlocks.RGB_BED));
        carpet(ModBlocks.RGB_CARPET, ModBlocks.RGB_WOOL);
        concretePowder(ModBlocks.RGB_CONCRETE_POWDER, ModItems.RGB_DYE);
        coloredTerracottaFromTerracottaAndDye(ModBlocks.RGB_TERRACOTTA, ModItems.RGB_DYE);
        smeltingResultFromBase(ModBlocks.RGB_GLAZED_TERRACOTTA, ModBlocks.RGB_TERRACOTTA);
        stainedGlassFromGlassAndDye(ModBlocks.RGB_STAINED_GLASS, ModItems.RGB_DYE);
        this.shaped(RecipeCategory.DECORATIONS, ModBlocks.RGB_STAINED_GLASS_PANE, 8)
                .define('#', Blocks.GLASS_PANE)
                .define('$', ModItems.RGB_DYE)
                .pattern("###")
                .pattern("#$#")
                .pattern("###")
                .group("stained_glass_pane")
                .unlockedBy("has_glass_pane", this.has(Blocks.GLASS_PANE))
                .unlockedBy(getHasName(ModItems.RGB_DYE), this.has(ModItems.RGB_DYE))
                .save(this.output, WorldOfColor.MODID + ":" + getConversionRecipeName(ModBlocks.RGB_STAINED_GLASS_PANE, Blocks.GLASS_PANE));
        stainedGlassPaneFromStainedGlass(ModBlocks.RGB_STAINED_GLASS_PANE, ModBlocks.RGB_STAINED_GLASS);
        candle(ModBlocks.RGB_CANDLE, ModItems.RGB_DYE);
        TransmuteRecipeBuilder.transmute(
                        RecipeCategory.DECORATIONS, tag(ItemTags.SHULKER_BOXES), Ingredient.of(ModItems.RGB_DYE), ModBlocks.RGB_SHULKER_BOX.asItem()
                )
                .group("shulker_box_dye")
                .unlockedBy("has_shulker_box", this.has(ItemTags.SHULKER_BOXES))
                .save(this.output);
        TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, tag(ItemTags.BUNDLES), Ingredient.of(ModItems.RGB_DYE), ModItems.RGB_BUNDLE.get())
                .group("bundle_dye")
                .unlockedBy(getHasName(ModItems.RGB_DYE), has(ModItems.RGB_DYE))
                .save(this.output);
        bedFromPlanksAndWool(ModBlocks.RGB_BED, ModBlocks.RGB_WOOL);
    }

    protected void generateForEnabledBlockFamilies(FeatureFlagSet set) {
        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(family -> generateRecipes(family, set));
    }

    protected void stonecutterResultFromBase(RecipeCategory category, ItemLike result, ItemLike material) {
        stonecutterResultFromBase(category, result, material, 1);
    }

    protected void stonecutterResultFromBase(RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
                .unlockedBy(getHasName(material), has(material))
                .save(output, WorldOfColor.MODID + ":" + getConversionRecipeName(result, material) + "_stonecutting");
    }

    protected void colorBlockWithDye(List<DeferredBlock<Block>> results, List<Item> dyes, TagKey<Item> dyeableItems, String group) {
        for (int i = 0; i < dyes.size(); i++) {
            Item dye = dyes.get(i);
            Item dyedItem = results.get(i).asItem();
            shapeless(RecipeCategory.BUILDING_BLOCKS, dyedItem)
                    .requires(dye)
                    .requires(dyeableItems)
                    .group(group)
                    .unlockedBy("has_needed_dye", has(dye))
                    .save(output, WorldOfColor.MODID + ":dye_" + getItemName(dyedItem));
        }
    }

    protected void coloredBricksFromBricksAndDye(ItemLike bricks, ItemLike dye) {
        shaped(RecipeCategory.BUILDING_BLOCKS, bricks, 8)
                .define('#', Blocks.BRICKS)
                .define('X', dye)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .group("stained_bricks")
                .unlockedBy("has_bricks", has(Blocks.BRICKS))
                .save(output);
    }

    protected void waxRecipes(List<DeferredBlock<Block>> block, List<DeferredBlock<Block>> waxedBlock) {
        COLORS.forEach(color ->
        {
            int index = COLORS.indexOf(color);
             shapeless(RecipeCategory.BUILDING_BLOCKS, waxedBlock.get(index))
                     .requires(block.get(index))
                     .requires(Items.HONEYCOMB)
                     .group(getItemName(waxedBlock.get(index)))
                     .unlockedBy(getHasName(block.get(index)), has(block.get(index)))
                     .save(output, WorldOfColor.MODID + ":" + getConversionRecipeName(waxedBlock.get(index), Items.HONEYCOMB));
        });
    }

    protected void colorCopper(RecipeCategory category, ItemLike result, ItemLike base) {
        shapeless(category, result)
                .requires(base)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(output);
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
