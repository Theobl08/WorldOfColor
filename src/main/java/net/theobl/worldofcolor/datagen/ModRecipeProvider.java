package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColorCollection;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.item.crafting.ColoredDecoratedPotRecipe;
import net.theobl.worldofcolor.tags.ModTags;
import net.theobl.worldofcolor.util.ModUtil;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

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

        colorBlockWithDye(ModBlocks.COLORED_SAPLINGS, Items.DYE, ItemTags.SAPLINGS, "sapling");
        colorBlockWithDye(ModBlocks.COLORED_CAULDRONS, Items.DYE, ModTags.Items.CAULDRONS, "dyed_cauldron");
        colorBlockWithDye(ModBlocks.COLORED_SLIME_BLOCKS, Items.DYE, Tags.Items.STORAGE_BLOCKS_SLIME, "dyed_slime_blocks");

        ColorCollection.zipApply(ModBlocks.COLORED_BRICKS, Items.DYE, this::coloredBricksFromBricksAndDye);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUILTED_CONCRETES, Blocks.CONCRETE);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Blocks.CONCRETE, ModBlocks.QUILTED_CONCRETES);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIMPLE_COLORED_BLOCKS, ModBlocks.QUILTED_CONCRETES);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIMPLE_COLORED_BLOCKS, Blocks.CONCRETE);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Blocks.CONCRETE, ModBlocks.SIMPLE_COLORED_BLOCKS);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUILTED_CONCRETES, ModBlocks.SIMPLE_COLORED_BLOCKS);

        ColorCollection.zipApply(ModBlocks.GLAZED_CONCRETES, Blocks.CONCRETE, this::smeltingResultFromBase);
        for (DeferredBlock<Block> carpet : ModBlocks.CLASSIC_CARPETS) {
            int index = ModBlocks.CLASSIC_CARPETS.indexOf(carpet);
            carpet(carpet.get(), ModBlocks.CLASSIC_WOOLS.get(index));
        }

        for (DyeColor color : COLORS) {
            planksFromLogs(ModBlocks.COLORED_PLANKS.pick(color), ModTags.Items.COLORED_LOGS.pick(color), 4);
            woodFromLogs(ModBlocks.COLORED_WOODS.pick(color), ModBlocks.COLORED_LOGS.pick(color));
            woodFromLogs(ModBlocks.COLORED_STRIPPED_WOODS.pick(color), ModBlocks.COLORED_STRIPPED_LOGS.pick(color));
            woodenBoat(ModItems.COLORED_BOATS.pick(color).get(), ModBlocks.COLORED_PLANKS.pick(color));
            chestBoat(ModItems.COLORED_CHEST_BOATS.pick(color).get(), ModItems.COLORED_BOATS.pick(color).get());
            shelf(ModBlocks.COLORED_SHELVES.pick(color), ModBlocks.COLORED_STRIPPED_LOGS.pick(color));
        }

        for (DyeColor color : COLORS) {
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER.pick(color), ModBlocks.COLORED_COPPER_BLOCKS.pick(color), 4);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_STAIRS.pick(color), ModBlocks.COLORED_COPPER_BLOCKS.pick(color), 4);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CUT_COPPER_SLABS.pick(color), ModBlocks.COLORED_COPPER_BLOCKS.pick(color), 8);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_CHISELED_COPPER.pick(color), ModBlocks.COLORED_COPPER_BLOCKS.pick(color), 4);
            grate(ModBlocks.COLORED_COPPER_GRATES.pick(color).get(), ModBlocks.COLORED_COPPER_BLOCKS.pick(color).get());
            copperBulb(ModBlocks.COLORED_COPPER_BULBS.pick(color).get(), ModBlocks.COLORED_COPPER_BLOCKS.pick(color).get());
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_COPPER_GRATES.pick(color), ModBlocks.COLORED_COPPER_BLOCKS.pick(color), 4);
        }

        for (DyeColor color : COLORS) {
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER.pick(color), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color), 4);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.pick(color), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color), 4);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.pick(color), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color), 8);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_CHISELED_COPPER.pick(color), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color), 4);
            grate(ModBlocks.COLORED_WAXED_COPPER_GRATES.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color).get());
            copperBulb(ModBlocks.COLORED_WAXED_COPPER_BULBS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color).get());
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_WAXED_COPPER_GRATES.pick(color), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color), 4);
        }

        ModBlocks.COLORED_DECORATED_POTS.forEach(block -> this.shaped(RecipeCategory.DECORATIONS, block.get().asItem())
                .define('#', Items.BRICK)
                .define('D', Items.DYE.pick(block.get().getColor()))
                .pattern(" # ")
                .pattern("#D#")
                .pattern(" # ")
                .unlockedBy("has_brick", this.has(ItemTags.DECORATED_POT_INGREDIENTS))
                .save(this.output, WorldOfColor.MODID + ":" + name(block) + "_simple"));

        colorCopper(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COLORED_COPPER_BLOCKS.white(), Blocks.COPPER_BLOCK.weathering().unaffected());
        colorCopper(RecipeCategory.REDSTONE, ModBlocks.COLORED_COPPER_DOORS.white(), Blocks.COPPER_DOOR.weathering().unaffected());
        colorCopper(RecipeCategory.REDSTONE, ModBlocks.COLORED_COPPER_TRAPDOORS.white(), Blocks.COPPER_TRAPDOOR.weathering().unaffected());
        colorCopper(RecipeCategory.REDSTONE, ModBlocks.COLORED_LIGHTNING_RODS.white(), Blocks.LIGHTNING_ROD.weathering().unaffected());
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_BARS.white(), Blocks.COPPER_BARS.weathering().unaffected());
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_CHAINS.white(), Blocks.COPPER_CHAIN.weathering().unaffected());
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_LANTERNS.white(), Blocks.COPPER_LANTERN.weathering().unaffected());
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_CHESTS.white(), Blocks.COPPER_CHEST.weathering().unaffected());
        colorCopper(RecipeCategory.DECORATIONS, ModBlocks.COLORED_COPPER_GOLEM_STATUES.white(), Blocks.COPPER_GOLEM_STATUE.weathering().unaffected());
        SpecialRecipeBuilder.special(() -> new ColoredDecoratedPotRecipe(this.tag(ItemTags.DECORATED_POT_INGREDIENTS), tag(ItemTags.DYES)))
                .save(this.output, "colored_decorated_pot");

        colorWithDye(Items.DYE, ModBlocks.COLORED_FLOWER_POTS.map(DeferredBlock::asItem), Items.FLOWER_POT,
                "flower_pot_dye", RecipeCategory.DECORATIONS);
        colorWithDye(Items.DYE, ModBlocks.COLORED_REDSTONE_LAMPS.map(DeferredBlock::asItem), Items.REDSTONE_LAMP,
                "redstone_lamp_dye", RecipeCategory.REDSTONE);
        colorWithDye(Items.DYE, ModItems.COLORED_ITEM_FRAMES.map(DeferredItem::asItem), Items.ITEM_FRAME,
                "item_frame_dye", RecipeCategory.DECORATIONS);
        colorWithDye(Items.DYE, ModBlocks.COLORED_POTATO_PEELS_BLOCK.map(DeferredBlock::asItem), null,
                "potato_peels_block_dye", RecipeCategory.MISC);
        colorWithDye(Items.DYE, ModItems.COLORED_POTATO_PEELS.map(DeferredItem::get), Items.POTATO,
                "potato_peels_dye", RecipeCategory.MISC);
        for (DyeColor color : COLORS) {
            nineBlockStorageRecipes(
                    RecipeCategory.MISC,
                    ModItems.COLORED_POTATO_PEELS.pick(color),
                    RecipeCategory.MISC,
                    ModBlocks.COLORED_POTATO_PEELS_BLOCK.pick(color),
                    WorldOfColor.MODID + ":" + getItemName(ModBlocks.COLORED_POTATO_PEELS_BLOCK.pick(color)),
                    null,
                    WorldOfColor.MODID + ":" + getItemName(ModItems.COLORED_POTATO_PEELS.pick(color)),
                    null
            );
        }

        shapeless(RecipeCategory.MISC, ModItems.RGB_DYE, 7)
                .requires(Items.DYE.black())
                .requires(Items.DYE.blue())
                .requires(Items.DYE.brown())
                .requires(Items.DYE.green())
                .requires(Items.DYE.red())
                .requires(Items.DYE.white())
                .requires(Items.DYE.yellow())
                .unlockedBy(getHasName(Items.DYE.black()), has(Items.DYE.black()))
                .unlockedBy(getHasName(Items.DYE.blue()), has(Items.DYE.blue()))
                .unlockedBy(getHasName(Items.DYE.brown()), has(Items.DYE.brown()))
                .unlockedBy(getHasName(Items.DYE.green()), has(Items.DYE.green()))
                .unlockedBy(getHasName(Items.DYE.red()), has(Items.DYE.red()))
                .unlockedBy(getHasName(Items.DYE.white()), has(Items.DYE.white()))
                .unlockedBy(getHasName(Items.DYE.yellow()), has(Items.DYE.yellow()))
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
        banner(ModBlocks.RGB_BANNER, ModBlocks.RGB_WOOL);
    }

    protected void generateForEnabledBlockFamilies(FeatureFlagSet set) {
        ModBlockFamilies.getAllFamilies().forEach(family -> generateRecipes(family, set));
    }

    protected void stonecutterResultFromBase(RecipeCategory category, ItemLike result, ItemLike material) {
        stonecutterResultFromBase(category, result, material, 1);
    }

    protected <T extends ItemLike, U extends ItemLike> void stonecutterResultFromBase(RecipeCategory category, ColorCollection<T> result, ColorCollection<U> material) {
        ColorCollection.zipApply(result, material, (res, mat) -> stonecutterResultFromBase(category, res, mat, 1));
    }

    protected void stonecutterResultFromBase(RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
                .unlockedBy(getHasName(material), has(material))
                .save(output, WorldOfColor.MODID + ":" + getConversionRecipeName(result, material) + "_stonecutting");
    }

    protected void colorWithDye(ColorCollection<Item> dyes, ColorCollection<Item> dyedItems, @Nullable Item uncoloredItem, String groupName, RecipeCategory category) {
        for (DyeColor color : COLORS) {
            Item dye = dyes.pick(color);
            Item dyedItem = dyedItems.pick(color);
            Stream<Item> sourceItems = dyedItems.asList().stream().filter(b -> !b.equals(dyedItem));
            if (uncoloredItem != null) {
                sourceItems = Stream.concat(sourceItems, Stream.of(uncoloredItem));
            }

            this.shapeless(category, dyedItem)
                    .requires(dye)
                    .requires(Ingredient.of(sourceItems))
                    .group(groupName)
                    .unlockedBy("has_needed_dye", this.has(dye))
                    .save(this.output, WorldOfColor.MODID + ":dye_" + getItemName(dyedItem));
        }
    }

    protected void colorBlockWithDye(ColorCollection<DeferredBlock<Block>> results, ColorCollection<Item> dyes, TagKey<Item> dyeableItems, String group) {
        for (DyeColor color : COLORS) {
            Item dye = dyes.pick(color);
            Item dyedItem = results.pick(color).asItem();
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

    protected void waxRecipes(ColorCollection<DeferredBlock<Block>> block, ColorCollection<DeferredBlock<Block>> waxedBlock) {
        ColorCollection.zipApply(block, waxedBlock, (weathering, waxed) ->
                shapeless(RecipeCategory.BUILDING_BLOCKS, waxed)
                        .requires(weathering)
                        .requires(Items.HONEYCOMB)
                        .group(getItemName(waxed))
                        .unlockedBy(getHasName(weathering), has(weathering))
                        .save(output, WorldOfColor.MODID + ":" + getConversionRecipeName(waxed, Items.HONEYCOMB)));
    }

    protected void colorCopper(RecipeCategory category, ItemLike result, ItemLike base) {
        shapeless(category, result)
                .requires(base)
                .requires(Items.DYE.white())
                .unlockedBy(getHasName(Items.DYE.white()), has(Items.DYE.white()))
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
