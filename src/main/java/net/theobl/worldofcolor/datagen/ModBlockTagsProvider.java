package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ColoringColorCollection;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.tags.ModTags;
import net.theobl.worldofcolor.util.ModUtil;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    protected static final ColorCollection<TagKey<Block>> DYED_TAGS = ColorCollection.NAMES
            .map(name -> BlockTags.create(Identifier.fromNamespaceAndPath("c", "dyed/" + name)));

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, WorldOfColor.MODID);
    }

    @Override
    protected IntrinsicHolderTagAppender<Block> tag(TagKey<Block> tag) {
        return new IntrinsicHolderTagAppender<>(super.tag(tag));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider provider) {
        for (DeferredHolder<Block, ? extends Block> block : ModBlocks.BLOCKS.getEntries()) {
            if(mineableWithPickaxe((DeferredBlock<Block>) block) || block == ModBlocks.RGB_TERRACOTTA) {
                this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            }

            if (ModUtil.name(block).contains("copper") && !(block.get() instanceof LanternBlock)){
                this.tag(BlockTags.NEEDS_STONE_TOOL).add(block);
            }

            if(block.get() instanceof SaplingBlock) {
                this.tag(BlockItemTags.SAPLINGS.block()).add(block);
            }
            else if(block.get() instanceof LeavesBlock) {
                this.tag(BlockTags.LEAVES).add(block);
            }
            else if (block.get() instanceof StairBlock) {
                if(!ModBlocks.COLORED_STAIRS.asList().contains(block)) this.tag(BlockTags.STAIRS).add(block);
            }
            else if (block.get() instanceof SlabBlock) {
                if(!ModBlocks.COLORED_SLABS.asList().contains(block)) this.tag(BlockTags.SLABS).add(block);
            }
            else if (block.get() instanceof WallBlock) {
                this.tag(BlockTags.WALLS).add(block);
            }
            else if (block.get() instanceof DoorBlock && !ModBlocks.COLORED_DOORS.asList().contains(block)) {
                this.tag(BlockTags.DOORS).add(block);
            }
            else if (block.get() instanceof TrapDoorBlock && !ModBlocks.COLORED_TRAPDOORS.asList().contains(block)) {
                this.tag(BlockTags.TRAPDOORS).add(block);
            }
            else if (block.get() instanceof StandingSignBlock) {
                this.tag(BlockTags.STANDING_SIGNS).add(block);
            }
            else if (block.get() instanceof WallSignBlock) {
                this.tag(BlockTags.WALL_SIGNS).add(block);
            }
            else if (block.get() instanceof CeilingHangingSignBlock) {
                this.tag(BlockTags.CEILING_HANGING_SIGNS).add(block);
            }
            else if (block.get() instanceof WallHangingSignBlock) {
                this.tag(BlockTags.WALL_HANGING_SIGNS).add(block);
            }
            else if (block.get() instanceof ShelfBlock) {
                this.tag(BlockTags.WOODEN_SHELVES).add(block);
            }
            else if (block.get() instanceof SlimeBlock) {
                this.tag(Tags.Blocks.STORAGE_BLOCKS_SLIME).add(block);
            }
            else if (block.get() instanceof FlowerPotBlock) {
                this.tag(BlockTags.FLOWER_POTS).add(block);
            }
            else if (block.get() instanceof IronBarsBlock && !block.getId().getPath().contains("glass")) {
                this.tag(BlockTags.BARS).add(block);
            }
            else if (block.get() instanceof ChainBlock) {
                this.tag(BlockTags.CHAINS).add(block);
            }
            else if (block.get() instanceof LanternBlock) {
                this.tag(BlockTags.LANTERNS).add(block);
            }
            else if(block.get() instanceof LightningRodBlock) {
                this.tag(BlockTags.LIGHTNING_RODS).add(block);
            }
            else if(block.get() instanceof CopperChestBlock) {
                this.tag(BlockTags.COPPER_CHESTS).add(block);
            }
            else if(block.get() instanceof CopperGolemStatueBlock) {
                this.tag(BlockTags.COPPER_GOLEM_STATUES).add(block);
            }
            else if(block.get() instanceof FlowerBlock) {
                this.tag(BlockTags.SMALL_FLOWERS).add(block);
                this.tag(BlockItemTags.BEE_FOOD.block()).add(block);
            }
        }
        this.tag(BlockTags.WOOL).addAll(ModBlocks.CLASSIC_WOOLS).add(ModBlocks.RGB_WOOL);
        this.tag(BlockTags.WOOL_CARPETS).addAll(ModBlocks.CLASSIC_CARPETS).add(ModBlocks.RGB_CARPET);
        this.tag(Tags.Blocks.STRIPPED_LOGS).addAll(ModBlocks.COLORED_STRIPPED_LOGS);
        this.tag(Tags.Blocks.STRIPPED_WOODS).addAll(ModBlocks.COLORED_STRIPPED_WOODS);
        this.tag(BlockTags.PLANKS).addAll(ModBlocks.COLORED_PLANKS);
        this.tag(BlockTags.WOODEN_STAIRS).addAll(ModBlocks.COLORED_STAIRS);
        this.tag(BlockTags.WOODEN_SLABS).addAll(ModBlocks.COLORED_SLABS);
        this.tag(BlockTags.WOODEN_FENCES).addAll(ModBlocks.COLORED_FENCES);
        this.tag(BlockTags.FENCE_GATES).addAll(ModBlocks.COLORED_FENCE_GATES);
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).addAll(ModBlocks.COLORED_FENCE_GATES);
        this.tag(BlockTags.WOODEN_DOORS).addAll(ModBlocks.COLORED_DOORS);
        this.tag(BlockTags.WOODEN_TRAPDOORS).addAll(ModBlocks.COLORED_TRAPDOORS);
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).addAll(ModBlocks.COLORED_PRESSURE_PLATES);
        this.tag(BlockTags.WOODEN_BUTTONS).addAll(ModBlocks.COLORED_BUTTONS);
        this.tag(BlockTags.TERRACOTTA).add(ModBlocks.RGB_TERRACOTTA);
        this.tag(BlockTags.CONCRETE_POWDERS).add(ModBlocks.RGB_CONCRETE_POWDER);
        this.tag(BlockTags.IMPERMEABLE).add(ModBlocks.RGB_STAINED_GLASS);
        this.tag(BlockTags.SHULKER_BOXES).add(ModBlocks.RGB_SHULKER_BOX);
        this.tag(BlockTags.CANDLES).add(ModBlocks.RGB_CANDLE);
        this.tag(BlockTags.CANDLE_CAKES).add(ModBlocks.RGB_CANDLE_CAKE);
        this.tag(BlockTags.BEDS).add(ModBlocks.RGB_BED);
        this.tag(BlockTags.BANNERS).add(ModBlocks.RGB_BANNER, ModBlocks.RGB_WALL_BANNER);
        this.tag(Tags.Blocks.CONCRETES).add(ModBlocks.RGB_CONCRETE);
        this.tag(Tags.Blocks.GLASS_BLOCKS_CHEAP).add(ModBlocks.RGB_STAINED_GLASS);
        this.tag(Tags.Blocks.GLASS_PANES).add(ModBlocks.RGB_STAINED_GLASS_PANE);
        this.tag(Tags.Blocks.GLAZED_TERRACOTTAS).add(ModBlocks.RGB_GLAZED_TERRACOTTA);

        for (DyeColor color : ModUtil.COLORS) {
            this.tag(BlockTags.CAULDRONS)
                    .add(ModBlocks.COLORED_CAULDRONS.pick(color))
                    .add(ModBlocks.COLORED_WATER_CAULDRONS.pick(color))
                    .add(ModBlocks.COLORED_LAVA_CAULDRONS.pick(color))
                    .add(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.pick(color));

            TagKey<Block> tagKey = ModTags.Blocks.COLORED_LOGS.pick(color);
            this.tag(tagKey).add(ModBlocks.COLORED_LOGS.pick(color),
                    ModBlocks.COLORED_WOODS.pick(color),
                    ModBlocks.COLORED_STRIPPED_LOGS.pick(color),
                    ModBlocks.COLORED_STRIPPED_WOODS.pick(color));
            this.tag(BlockItemTags.LOGS_THAT_BURN.block()).addTag(tagKey);
        }

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
        addColored(ModBlocks.COLORED_CAULDRONS);
        addColored(ModBlocks.COLORED_WATER_CAULDRONS);
        addColored(ModBlocks.COLORED_LAVA_CAULDRONS);
        addColored(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS);
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
        addColored(ModBlocks.COLORED_SIGNS);
        addColored(ModBlocks.COLORED_WALL_SIGNS);
        addColored(ModBlocks.COLORED_HANGING_SIGNS);
        addColored(ModBlocks.COLORED_WALL_HANGING_SIGNS);
//        addColoredTags(tag(Tags.Blocks.DYED)::addTag, Tags.Blocks.DYED);
    }
    private boolean mineableWithPickaxe(DeferredBlock<Block> block) {
        return (ModUtil.name(block).contains("concrete") && !(block.get() instanceof ConcretePowderBlock)) ||
                ModUtil.name(block).contains("copper") ||
                ModUtil.name(block).contains("brick") ||
                block.get() instanceof LightningRodBlock ||
                block.get() instanceof GlazedTerracottaBlock ||
                ModBlocks.SIMPLE_COLORED_BLOCKS.asList().contains(block);
    }

    private void addColored(ColorCollection<DeferredBlock<Block>> collection) {
        ColorCollection.zipApply(DYED_TAGS, collection, (tagKey, block) -> tag(tagKey).add(block));
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
