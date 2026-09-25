package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.*;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.tags.ModTags;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, WorldOfColor.MODID);
    }

    @Override
    protected IntrinsicHolderTagAppender<Item> tag(TagKey<Item> tag) {
        return new IntrinsicHolderTagAppender<>(super.tag(tag));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.copy(BlockTags.WOOL, ItemTags.WOOL);
        this.copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        this.copy(BlockItemTags.SAPLINGS.block(), ItemTags.SAPLINGS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockItemTags.LOGS_THAT_BURN.block(), ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
        this.copy(BlockTags.WOODEN_SHELVES, ItemTags.WOODEN_SHELVES);

        this.copy(BlockTags.STAIRS, BlockItemTags.STAIRS.item());
        this.copy(BlockTags.SLABS, BlockItemTags.SLABS.item());
        this.copy(BlockTags.WALLS, ItemTags.WALLS);
        this.copy(BlockTags.DOORS, BlockItemTags.DOORS.item());
        this.copy(BlockTags.TRAPDOORS, BlockItemTags.TRAPDOORS.item());

        this.copy(BlockTags.BARS, BlockItemTags.BARS.item());
        this.copy(BlockTags.CHAINS, BlockItemTags.CHAINS.item());
        this.copy(BlockTags.LANTERNS, BlockItemTags.LANTERNS.item());
        this.copy(BlockTags.LIGHTNING_RODS, BlockItemTags.LIGHTNING_RODS.item());
        this.copy(BlockTags.COPPER_CHESTS, BlockItemTags.COPPER_CHESTS.item());
        this.copy(BlockTags.COPPER_GOLEM_STATUES, BlockItemTags.COPPER_GOLEM_STATUES.item());

        this.copy(BlockTags.SMALL_FLOWERS, BlockItemTags.SMALL_FLOWERS.item());
        this.copy(BlockItemTags.BEE_FOOD.block(), BlockItemTags.BEE_FOOD.item());

        this.copy(BlockTags.TERRACOTTA, ItemTags.TERRACOTTA);
        this.copy(BlockTags.CANDLES, ItemTags.CANDLES);
        this.copy(BlockTags.BEDS, ItemTags.BEDS);
        this.tag(ItemTags.BANNERS).add(ModItems.RGB_BANNER);
        this.copy(BlockTags.SHULKER_BOXES, ItemTags.SHULKER_BOXES);
        this.tag(Tags.Items.SHULKER_BOXES).add(ModItems.RGB_SHULKER_BOX).add(ModItems.MISSINGNO_SHULKER_BOX);
        this.copy(BlockTags.CONCRETE_POWDERS, ItemTags.CONCRETE_POWDERS);
        this.tag(Tags.Items.CONCRETE_POWDERS).add(ModBlocks.RGB_CONCRETE_POWDER.asItem().builtInRegistryHolder().key()).add(ModBlocks.MISSINGNO_CONCRETE_POWDER.asItem().builtInRegistryHolder().key());

        this.copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
        this.copy(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);
        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        this.copy(Tags.Blocks.STORAGE_BLOCKS_SLIME, Tags.Items.STORAGE_BLOCKS_SLIME);
        this.copy(Tags.Blocks.CONCRETES, Tags.Items.CONCRETES);
        this.copy(Tags.Blocks.GLAZED_TERRACOTTAS, Tags.Items.GLAZED_TERRACOTTAS);
        this.copy(Tags.Blocks.GLASS_BLOCKS_CHEAP, Tags.Items.GLASS_BLOCKS_CHEAP);
        this.copy(Tags.Blocks.GLASS_PANES, Tags.Items.GLASS_PANES);

        for(DyeColor color : ModUtil.COLORS) {
            this.copy(ModTags.Blocks.COLORED_LOGS.pick(color), ModTags.Items.COLORED_LOGS.pick(color));
        }

        this.tag(ItemTags.CUSHIONS).add(ModItems.RGB_CUSHION).add(ModItems.MISSINGNO_CUSHION);
        this.tag(ItemTags.BUNDLES).add(ModItems.RGB_BUNDLE).add(ModItems.MISSINGNO_BUNDLE);
        this.tag(ItemTags.HARNESSES).add(ModItems.RGB_HARNESS).add(ModItems.MISSINGNO_HARNESS);
        this.tag(Tags.Items.DYES).add(ModItems.RGB_DYE);
        this.tag(ModTags.Items.CAULDRONS).add(BlockItemIds.CAULDRON.item());
        this.tag(ModTags.Items.CAULDRONS).addAll(ModItems.COLORED_CAULDRONS);

        tag(ItemTags.BOATS).addAll(ModItems.COLORED_BOATS);
        tag(ItemTags.CHEST_BOATS).addAll(ModItems.COLORED_CHEST_BOATS);

        addColored(ModBlocks.SIMPLE_COLORED_BLOCKS);
        addColored(ModBlocks.COLORED_BRICKS);
        addColored(ModBlocks.COLORED_BRICK_STAIRS);
        addColored(ModBlocks.COLORED_BRICK_SLABS);
        addColored(ModBlocks.COLORED_BRICK_WALLS);
        addColored(ModBlocks.COLORED_COPPER_BLOCKS.coloring());
        addColored(ModBlocks.COLORED_CHISELED_COPPER.coloring());
        addColored(ModBlocks.COLORED_COPPER_GRATES.coloring());
        addColored(ModBlocks.COLORED_CUT_COPPER.coloring());
        addColored(ModBlocks.COLORED_CUT_COPPER_STAIRS.coloring());
        addColored(ModBlocks.COLORED_CUT_COPPER_SLABS.coloring());
        addColored(ModBlocks.COLORED_COPPER_DOORS.coloring());
        addColored(ModBlocks.COLORED_COPPER_TRAPDOORS.coloring());
        addColored(ModBlocks.COLORED_COPPER_BULBS.coloring());
        addColored(ModBlocks.COLORED_COPPER_BLOCKS.waxed());
        addColored(ModBlocks.COLORED_CHISELED_COPPER.waxed());
        addColored(ModBlocks.COLORED_COPPER_GRATES.waxed());
        addColored(ModBlocks.COLORED_CUT_COPPER.waxed());
        addColored(ModBlocks.COLORED_CUT_COPPER_STAIRS.waxed());
        addColored(ModBlocks.COLORED_CUT_COPPER_SLABS.waxed());
        addColored(ModBlocks.COLORED_COPPER_DOORS.waxed());
        addColored(ModBlocks.COLORED_COPPER_TRAPDOORS.waxed());
        addColored(ModBlocks.COLORED_COPPER_BULBS.waxed());
        addColored(ModBlocks.COLORED_LIGHTNING_RODS.coloring());
        addColored(ModItems.COLORED_CAULDRONS);
        addColored(ModBlocks.GLAZED_CONCRETES);
        addColored(ModBlocks.QUILTED_CONCRETES);
        addColored(ModBlocks.COLORED_SLIME_BLOCKS);
        addColored(ModBlocks.COLORED_SAPLINGS);
        addColored(ModBlocks.COLORED_LEAVES);
        addColored(ModBlocks.COLORED_LOGS);
        addColored(ModBlocks.COLORED_STRIPPED_LOGS);
        addColored(ModBlocks.COLORED_WOODS);
        addColored(ModBlocks.COLORED_STRIPPED_WOODS);
        addColored(ModBlocks.COLORED_PLANKS);
        addColored(ModBlocks.COLORED_STAIRS);
        addColored(ModBlocks.COLORED_SLABS);
        addColored(ModBlocks.COLORED_FENCES);
        addColored(ModBlocks.COLORED_FENCE_GATES);
        addColored(ModBlocks.COLORED_DOORS);
        addColored(ModBlocks.COLORED_TRAPDOORS);
        addColored(ModBlocks.COLORED_PRESSURE_PLATES);
        addColored(ModBlocks.COLORED_BUTTONS);
        addColored(ModItems.COLORED_SIGNS);
        addColored(ModItems.COLORED_HANGING_SIGNS);
        addColored(ModItems.COLORED_BOATS);
        addColored(ModItems.COLORED_CHEST_BOATS);
    }

    private <T extends DeferredHolder<?, ?>> void addColored(ColorCollection<T> collection) {
        ColorCollection.zipApply(Tags.Items.DYED_COLORS.map(this::tag), collection, (appender, holder) ->
                appender.add(TagEntry.element(holder.getId()))
        );
    }

    @SuppressWarnings("unchecked")
    private TagKey<Item> getForgeItemTag(String name) {
        try {
            name = name.toUpperCase(Locale.ENGLISH);
            return (TagKey<Item>) Tags.Items.class.getDeclaredField(name).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(Tags.Items.class.getName() + " is missing tag name: " + name);
        }
    }
}
