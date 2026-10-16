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

    private IntrinsicHolderTagAppender<Item> tag(BlockItemTagId tag) {
        return tag(tag.item());
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.copy(BlockItemTags.WOOL);
        this.copy(BlockItemTags.WOOL_CARPETS);
        this.copy(BlockItemTags.SAPLINGS);
        this.copy(BlockItemTags.LEAVES);
        this.copy(BlockItemTags.LOGS_THAT_BURN);
        this.copy(BlockItemTags.PLANKS);
        this.copy(BlockItemTags.WOODEN_STAIRS);
        this.copy(BlockItemTags.WOODEN_SLABS);
        this.copy(BlockItemTags.WOODEN_FENCES);
        this.copy(BlockItemTags.FENCE_GATES);
        this.copy(BlockItemTags.WOODEN_DOORS);
        this.copy(BlockItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockItemTags.WOODEN_BUTTONS);
        this.copy(BlockItemTags.SIGNS);
        this.copy(BlockItemTags.HANGING_SIGNS);
        this.copy(BlockItemTags.WOODEN_SHELVES);

        this.copy(BlockItemTags.STAIRS);
        this.copy(BlockItemTags.SLABS);
        this.copy(BlockItemTags.WALLS);
        this.copy(BlockItemTags.DOORS);
        this.copy(BlockItemTags.TRAPDOORS);

        this.copy(BlockItemTags.BARS);
        this.copy(BlockItemTags.CHAINS);
        this.copy(BlockItemTags.LANTERNS);
        this.copy(BlockItemTags.LIGHTNING_RODS);
        this.copy(BlockItemTags.COPPER_CHESTS);
        this.copy(BlockItemTags.COPPER_GOLEM_STATUES);

        this.copy(BlockItemTags.SMALL_FLOWERS);
        this.copy(BlockItemTags.BEE_FOOD);

        this.copy(BlockItemTags.TERRACOTTA);
        this.copy(BlockItemTags.CANDLES);
        this.copy(BlockItemTags.BEDS);
        this.tag(ItemTags.BANNERS).add(ModItems.RGB_BANNER);
        this.copy(BlockItemTags.SHULKER_BOXES);
        this.tag(Tags.Items.SHULKER_BOXES).add(ModItems.RGB_SHULKER_BOX).add(ModItems.MISSINGNO_SHULKER_BOX);
        this.copy(BlockItemTags.CONCRETE_POWDERS);
        this.tag(Tags.Items.CONCRETE_POWDERS).add(ModBlocks.RGB_CONCRETE_POWDER.asItem().builtInRegistryHolder().key()).add(ModBlocks.MISSINGNO_CONCRETE_POWDER.asItem().builtInRegistryHolder().key());

        this.copy(Tags.BlockItems.STRIPPED_LOGS);
        this.copy(Tags.BlockItems.STRIPPED_WOODS);
        this.copy(Tags.BlockItems.FENCE_GATES_WOODEN);
        this.copy(Tags.BlockItems.STORAGE_BLOCKS_SLIME);
        this.copy(Tags.BlockItems.CONCRETES);
        this.copy(Tags.BlockItems.GLAZED_TERRACOTTAS);
        this.copy(Tags.BlockItems.GLASS_BLOCKS_CHEAP);
        this.copy(Tags.BlockItems.GLASS_PANES);

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
        ColorCollection.zipApply(Tags.BlockItems.DYED_COLORS.map(this::tag), collection, (appender, holder) ->
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
