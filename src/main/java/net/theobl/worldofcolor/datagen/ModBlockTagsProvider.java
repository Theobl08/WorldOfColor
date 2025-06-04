package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.tags.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, WorldOfColor.MODID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider provider) {
        for (DeferredHolder<Block, ? extends Block> block : ModBlocks.BLOCKS.getEntries()) {
            if(mineableWithPickaxe((DeferredBlock<Block>) block)) {
                this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
            }
            else if(ModBlocks.CLASSIC_WOOLS.contains(block)) {
                this.tag(BlockTags.WOOL).add(block.get());
            }
            else if(ModBlocks.CLASSIC_CARPETS.contains(block)) {
                this.tag(BlockTags.WOOL_CARPETS).add(block.get());
            }
            else if (ModBlocks.COLORED_STRIPPED_LOGS.contains(block)) {
                this.tag(Tags.Blocks.STRIPPED_LOGS).add(block.get());
            }
            else if (ModBlocks.COLORED_STRIPPED_WOODS.contains(block)) {
                this.tag(Tags.Blocks.STRIPPED_WOODS).add(block.get());
            }
            else if (ModBlocks.COLORED_PLANKS.contains(block)) {
                this.tag(BlockTags.PLANKS).add(block.get());
            }
            else if (ModBlocks.COLORED_STAIRS.contains(block)) {
                this.tag(BlockTags.WOODEN_STAIRS).add(block.get());
            }
            else if (ModBlocks.COLORED_SLABS.contains(block)) {
                this.tag(BlockTags.WOODEN_SLABS).add(block.get());
            }
            else if (ModBlocks.COLORED_FENCES.contains(block)) {
                this.tag(BlockTags.WOODEN_FENCES).add(block.get());
            }
            else if (ModBlocks.COLORED_FENCE_GATES.contains(block)) {
                this.tag(BlockTags.FENCE_GATES).add(block.get());
                this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(block.get());
            }
            else if (ModBlocks.COLORED_DOORS.contains(block)) {
                this.tag(BlockTags.WOODEN_DOORS).add(block.get());
            }
            else if (ModBlocks.COLORED_TRAPDOORS.contains(block)) {
                this.tag(BlockTags.WOODEN_TRAPDOORS).add(block.get());
            }
            else if (ModBlocks.COLORED_PRESSURE_PLATES.contains(block)) {
                this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(block.get());
            }
            else if (ModBlocks.COLORED_BUTTONS.contains(block)) {
                this.tag(BlockTags.WOODEN_BUTTONS).add(block.get());
            }

            if (block.get().toString().contains("copper")){
                this.tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL).add(block.get());
                this.tag(BlockTags.INCORRECT_FOR_GOLD_TOOL).add(block.get());
                this.tag(BlockTags.NEEDS_STONE_TOOL).add(block.get());
            }

            if(block.get() instanceof LeavesBlock) {
                this.tag(BlockTags.LEAVES).add(block.get());
            }
            else if (block.get() instanceof StairBlock) {
                if(!ModBlocks.COLORED_STAIRS.contains(block)) this.tag(BlockTags.STAIRS).add(block.get());
            }
            else if (block.get() instanceof SlabBlock) {
                if(!ModBlocks.COLORED_SLABS.contains(block)) this.tag(BlockTags.SLABS).add(block.get());
            }
            else if (block.get() instanceof WallBlock) {
                this.tag(BlockTags.WALLS).add(block.get());
            }
            else if (block.get() instanceof DoorBlock) {
                if(!ModBlocks.COLORED_DOORS.contains(block)) this.tag(BlockTags.DOORS).add(block.get());
            }
            else if (block.get() instanceof TrapDoorBlock) {
                if(!ModBlocks.COLORED_TRAPDOORS.contains(block)) this.tag(BlockTags.TRAPDOORS).add(block.get());
            }
            else if (block.get() instanceof StandingSignBlock) {
                this.tag(BlockTags.STANDING_SIGNS).add(block.get());
            }
            else if (block.get() instanceof WallSignBlock) {
                this.tag(BlockTags.WALL_SIGNS).add(block.get());
            }
            else if (block.get() instanceof CeilingHangingSignBlock) {
                this.tag(BlockTags.CEILING_HANGING_SIGNS).add(block.get());
            }
            else if (block.get() instanceof WallHangingSignBlock) {
                this.tag(BlockTags.WALL_HANGING_SIGNS).add(block.get());
            }
        }

        for (TagKey<Block> tagKey : ModTags.Blocks.COLORED_LOGS) {
            int index = ModTags.Blocks.COLORED_LOGS.indexOf(tagKey);
            this.tag(tagKey).add(ModBlocks.COLORED_LOGS.get(index).get(),
                    ModBlocks.COLORED_WOODS.get(index).get(),
                    ModBlocks.COLORED_STRIPPED_LOGS.get(index).get(),
                    ModBlocks.COLORED_STRIPPED_WOODS.get(index).get());
            this.tag(BlockTags.LOGS_THAT_BURN).addTag(tagKey);
        }

        addColored(Tags.Blocks.DYED, "{color}_block");
        addColored(Tags.Blocks.DYED, "{color}_bricks");
        addColored(Tags.Blocks.DYED, "{color}_brick_stairs");
        addColored(Tags.Blocks.DYED, "{color}_brick_slab");
        addColored(Tags.Blocks.DYED, "{color}_brick_wall");
        addColored(Tags.Blocks.DYED, "{color}_copper_block");
        addColored(Tags.Blocks.DYED, "{color}_chiseled_copper");
        addColored(Tags.Blocks.DYED, "{color}_copper_grate");
        addColored(Tags.Blocks.DYED, "{color}_cut_copper");
        addColored(Tags.Blocks.DYED, "{color}_cut_copper_stairs");
        addColored(Tags.Blocks.DYED, "{color}_cut_copper_slab");
        addColored(Tags.Blocks.DYED, "{color}_copper_door");
        addColored(Tags.Blocks.DYED, "{color}_copper_trapdoor");
        addColored(Tags.Blocks.DYED, "{color}_copper_bulb");
        addColored(Tags.Blocks.DYED, "waxed_{color}_copper_block");
        addColored(Tags.Blocks.DYED, "waxed_{color}_chiseled_copper");
        addColored(Tags.Blocks.DYED, "waxed_{color}_copper_grate");
        addColored(Tags.Blocks.DYED, "waxed_{color}_cut_copper");
        addColored(Tags.Blocks.DYED, "waxed_{color}_cut_copper_stairs");
        addColored(Tags.Blocks.DYED, "waxed_{color}_cut_copper_slab");
        addColored(Tags.Blocks.DYED, "waxed_{color}_copper_door");
        addColored(Tags.Blocks.DYED, "waxed_{color}_copper_trapdoor");
        addColored(Tags.Blocks.DYED, "waxed_{color}_copper_bulb");
        addColored(Tags.Blocks.DYED, "{color}_glazed_concrete");
        addColored(Tags.Blocks.DYED, "{color}_quilted_concrete");
        addColored(Tags.Blocks.DYED, "{color}_leaves");
        addColored(Tags.Blocks.DYED, "{color}_log");
        addColored(Tags.Blocks.DYED, "stripped_{color}_log");
        addColored(Tags.Blocks.DYED, "{color}_wood");
        addColored(Tags.Blocks.DYED, "stripped_{color}_wood");
        addColored(Tags.Blocks.DYED, "{color}_planks");
        addColored(Tags.Blocks.DYED, "{color}_stairs");
        addColored(Tags.Blocks.DYED, "{color}_slab");
        addColored(Tags.Blocks.DYED, "{color}_fence");
        addColored(Tags.Blocks.DYED, "{color}_fence_gate");
        addColored(Tags.Blocks.DYED, "{color}_door");
        addColored(Tags.Blocks.DYED, "{color}_trapdoor");
        addColored(Tags.Blocks.DYED, "{color}_pressure_plate");
        addColored(Tags.Blocks.DYED, "{color}_button");
        addColored(Tags.Blocks.DYED, "{color}_sign");
        addColored(Tags.Blocks.DYED, "{color}_wall_sign");
        addColored(Tags.Blocks.DYED, "{color}_hanging_sign");
        addColored(Tags.Blocks.DYED, "{color}_wall_hanging_sign");
//        addColoredTags(tag(Tags.Blocks.DYED)::addTag, Tags.Blocks.DYED);
    }
    private boolean mineableWithPickaxe(DeferredBlock<Block> block) {
        return block.get().toString().contains("concrete") ||
                block.get().toString().contains("copper") ||
                block.get().toString().contains("brick") ||
                ModBlocks.SIMPLE_COLORED_BLOCKS.contains(block);
    }

    private void addColored(TagKey<Block> group, String pattern) {
        String prefix = group.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
        for (DyeColor color : DyeColor.values()) {
            ResourceLocation key = ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID, pattern.replace("{color}", color.getName()));
            TagKey<Block> tag = getForgeTag(prefix + color.getName());
            Block block = BuiltInRegistries.BLOCK.get(key);
            if (block == null || block == Blocks.AIR)
                throw new IllegalStateException("Unknown block: " + key);
            tag(tag).add(block);
        }
    }

//    private void addColoredTags(Consumer<TagKey<Block>> consumer, TagKey<Block> group) {
//        String prefix = group.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
//        for (DyeColor color : DyeColor.values()) {
//            TagKey<Block> tag = getForgeTag(prefix + color.getName());
//            consumer.accept(tag);
//        }
//    }

    @SuppressWarnings("unchecked")
    private TagKey<Block> getForgeTag(String name) {
        try {
            name = name.toUpperCase(Locale.ENGLISH);
            return (TagKey<Block>) Tags.Blocks.class.getDeclaredField(name).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(Tags.Blocks.class.getName() + " is missing tag name: " + name);
        }
    }
}
