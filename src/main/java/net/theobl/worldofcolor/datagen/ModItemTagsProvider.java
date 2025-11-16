package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.tags.ModTags;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, WorldOfColor.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.copy(BlockTags.WOOL, ItemTags.WOOL);
        this.copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
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

        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.WALLS, ItemTags.WALLS);
        this.copy(BlockTags.DOORS, ItemTags.DOORS);
        this.copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);

        this.copy(BlockTags.BARS, ItemTags.BARS);
        this.copy(BlockTags.CHAINS, ItemTags.CHAINS);
        this.copy(BlockTags.LANTERNS, ItemTags.LANTERNS);
        this.copy(BlockTags.LIGHTNING_RODS, ItemTags.LIGHTNING_RODS);
        this.copy(BlockTags.COPPER_CHESTS, ItemTags.COPPER_CHESTS);
        this.copy(BlockTags.COPPER_GOLEM_STATUES, ItemTags.COPPER_GOLEM_STATUES);

        this.copy(BlockTags.TERRACOTTA, ItemTags.TERRACOTTA);
        this.copy(BlockTags.CANDLES, ItemTags.CANDLES);
        this.copy(BlockTags.CONCRETE_POWDER, Tags.Items.CONCRETE_POWDERS);

        this.copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
        this.copy(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);
        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        this.copy(Tags.Blocks.STORAGE_BLOCKS_SLIME, Tags.Items.STORAGE_BLOCKS_SLIME);
        this.copy(Tags.Blocks.CONCRETES, Tags.Items.CONCRETES);
        this.copy(Tags.Blocks.GLAZED_TERRACOTTAS, Tags.Items.GLAZED_TERRACOTTAS);
        this.copy(Tags.Blocks.GLASS_BLOCKS_CHEAP, Tags.Items.GLASS_BLOCKS_CHEAP);
        this.copy(Tags.Blocks.GLASS_PANES, Tags.Items.GLASS_PANES);

        for(TagKey<Item> tag : ModTags.Items.COLORED_LOGS) {
            int index = ModTags.Items.COLORED_LOGS.indexOf(tag);
            this.copy(ModTags.Blocks.COLORED_LOGS.get(index), tag);
        }

        this.tag(ModTags.Items.CAULDRONS).add(Items.CAULDRON);
        ModBlocks.COLORED_CAULDRONS.forEach(block -> this.tag(ModTags.Items.CAULDRONS).add(block.asItem()));

    ModItems.COLORED_BOATS.forEach(item -> tag(ItemTags.BOATS).add(item.asItem()));
        ModItems.COLORED_CHEST_BOATS.forEach(item -> tag(ItemTags.CHEST_BOATS).add(item.asItem()));

        addColored(Tags.Items.DYED, "{color}_block");
        addColored(Tags.Items.DYED, "{color}_bricks");
        addColored(Tags.Items.DYED, "{color}_brick_stairs");
        addColored(Tags.Items.DYED, "{color}_brick_slab");
        addColored(Tags.Items.DYED, "{color}_brick_wall");
        addColored(Tags.Items.DYED, "{color}_copper_block");
        addColored(Tags.Items.DYED, "{color}_chiseled_copper");
        addColored(Tags.Items.DYED, "{color}_copper_grate");
        addColored(Tags.Items.DYED, "{color}_cut_copper");
        addColored(Tags.Items.DYED, "{color}_cut_copper_stairs");
        addColored(Tags.Items.DYED, "{color}_cut_copper_slab");
        addColored(Tags.Items.DYED, "{color}_copper_door");
        addColored(Tags.Items.DYED, "{color}_copper_trapdoor");
        addColored(Tags.Items.DYED, "{color}_copper_bulb");
        addColored(Tags.Items.DYED, "waxed_{color}_copper_block");
        addColored(Tags.Items.DYED, "waxed_{color}_chiseled_copper");
        addColored(Tags.Items.DYED, "waxed_{color}_copper_grate");
        addColored(Tags.Items.DYED, "waxed_{color}_cut_copper");
        addColored(Tags.Items.DYED, "waxed_{color}_cut_copper_stairs");
        addColored(Tags.Items.DYED, "waxed_{color}_cut_copper_slab");
        addColored(Tags.Items.DYED, "waxed_{color}_copper_door");
        addColored(Tags.Items.DYED, "waxed_{color}_copper_trapdoor");
        addColored(Tags.Items.DYED, "waxed_{color}_copper_bulb");
        addColored(Tags.Items.DYED, "{color}_lightning_rod");
        addColored(Tags.Items.DYED, "{color}_cauldron");
        addColored(Tags.Items.DYED, "{color}_glazed_concrete");
        addColored(Tags.Items.DYED, "{color}_quilted_concrete");
        addColored(Tags.Items.DYED, "{color}_slime_block");
        addColored(Tags.Items.DYED, "{color}_sapling");
        addColored(Tags.Items.DYED, "{color}_leaves");
        addColored(Tags.Items.DYED, "{color}_log");
        addColored(Tags.Items.DYED, "stripped_{color}_log");
        addColored(Tags.Items.DYED, "{color}_wood");
        addColored(Tags.Items.DYED, "stripped_{color}_wood");
        addColored(Tags.Items.DYED, "{color}_planks");
        addColored(Tags.Items.DYED, "{color}_stairs");
        addColored(Tags.Items.DYED, "{color}_slab");
        addColored(Tags.Items.DYED, "{color}_fence");
        addColored(Tags.Items.DYED, "{color}_fence_gate");
        addColored(Tags.Items.DYED, "{color}_door");
        addColored(Tags.Items.DYED, "{color}_trapdoor");
        addColored(Tags.Items.DYED, "{color}_pressure_plate");
        addColored(Tags.Items.DYED, "{color}_button");
        addColored(Tags.Items.DYED, "{color}_sign");
        addColored(Tags.Items.DYED, "{color}_hanging_sign");
        addColored(Tags.Items.DYED, "{color}_boat");
        addColored(Tags.Items.DYED, "{color}_chest_boat");
//        addColoredTags(tag(Tags.Items.DYED)::addTag, Tags.Items.DYED);
    }

    private void addColored(TagKey<Item> group, String pattern) {
        String prefix = group.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
        for (DyeColor color : DyeColor.values()) {
            ResourceLocation key = WorldOfColor.asResource(pattern.replace("{color}", color.getName()));
            TagKey<Item> tag = getForgeItemTag(prefix + color.getName());
            Item item = BuiltInRegistries.ITEM.getValue(key);
            if (item == null || item == Items.AIR)
                throw new IllegalStateException("Unknown vanilla item: " + key);
            tag(tag).add(item);
        }
    }

//    private void addColoredTags(Consumer<TagKey<Item>> consumer, TagKey<Item> group) {
//        String prefix = group.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
//        for (DyeColor color : DyeColor.values()) {
//            TagKey<Item> tag = getForgeItemTag(prefix + color.getName());
//            consumer.accept(tag);
//        }
//    }

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
