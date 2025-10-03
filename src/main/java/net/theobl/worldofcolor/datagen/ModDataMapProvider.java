package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.datamaps.builtin.*;
import net.theobl.worldofcolor.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    /**
     * Create a new provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        final var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        ModBlocks.COLORED_LEAVES.forEach(block -> compostables.add(block.getId(), new Compostable(0.3F), false));
        final var oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BLOCKS) {
            int index = ModBlocks.COLORED_COPPER_BLOCKS.indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_BLOCKS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_BLOCKS.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CHISELED_COPPER) {
            int index = ModBlocks.COLORED_CHISELED_COPPER.indexOf(block);
            if(index == ModBlocks.COLORED_CHISELED_COPPER.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_CHISELED_COPPER.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_GRATES) {
            int index = ModBlocks.COLORED_COPPER_GRATES.indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_GRATES.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_GRATES.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER) {
            int index = ModBlocks.COLORED_CUT_COPPER.indexOf(block);
            if(index == ModBlocks.COLORED_CUT_COPPER.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_CUT_COPPER.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_STAIRS) {
            int index = ModBlocks.COLORED_CUT_COPPER_STAIRS.indexOf(block);
            if(index == ModBlocks.COLORED_CUT_COPPER_STAIRS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_SLABS) {
            int index = ModBlocks.COLORED_CUT_COPPER_SLABS.indexOf(block);
            if(index == ModBlocks.COLORED_CUT_COPPER_SLABS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_CUT_COPPER_SLABS.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_DOORS) {
            int index = ModBlocks.COLORED_COPPER_DOORS.indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_DOORS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_DOORS.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_TRAPDOORS) {
            int index = ModBlocks.COLORED_COPPER_TRAPDOORS.indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_TRAPDOORS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_TRAPDOORS.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BULBS) {
            int index = ModBlocks.COLORED_COPPER_BULBS.indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_BULBS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_BULBS.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BARS) {
            int index = ModBlocks.COLORED_COPPER_BARS.indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_BARS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_BARS.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_CHAINS) {
            int index = ModBlocks.COLORED_COPPER_CHAINS.indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_CHAINS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_CHAINS.get(index + 1).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_LIGHTNING_RODS) {
            int index = ModBlocks.COLORED_LIGHTNING_RODS.indexOf(block);
            if(index == ModBlocks.COLORED_LIGHTNING_RODS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_LIGHTNING_RODS.get(index + 1).get()), false);
        }

        final var waxables = builder(NeoForgeDataMaps.WAXABLES);
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BLOCKS) {
            int index = ModBlocks.COLORED_COPPER_BLOCKS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CHISELED_COPPER) {
            int index = ModBlocks.COLORED_CHISELED_COPPER.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_GRATES) {
            int index = ModBlocks.COLORED_COPPER_GRATES.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER) {
            int index = ModBlocks.COLORED_CUT_COPPER.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_STAIRS) {
            int index = ModBlocks.COLORED_CUT_COPPER_STAIRS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_SLABS) {
            int index = ModBlocks.COLORED_CUT_COPPER_SLABS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_DOORS) {
            int index = ModBlocks.COLORED_COPPER_DOORS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_DOORS.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_TRAPDOORS) {
            int index = ModBlocks.COLORED_COPPER_TRAPDOORS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BULBS) {
            int index = ModBlocks.COLORED_COPPER_BULBS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_BULBS.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BARS) {
            int index = ModBlocks.COLORED_COPPER_BARS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_BARS.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_CHAINS) {
            int index = ModBlocks.COLORED_COPPER_CHAINS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_CHAINS.get(index).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_LIGHTNING_RODS) {
            int index = ModBlocks.COLORED_LIGHTNING_RODS.indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_LIGHTNING_RODS.get(index).get()), false);
        }

        final var strippable = builder(NeoForgeDataMaps.STRIPPABLES);
        for (DeferredBlock<Block> log : ModBlocks.COLORED_LOGS) {
            int index = ModBlocks.COLORED_LOGS.indexOf(log);
            strippable.add(log, new Strippable(ModBlocks.COLORED_STRIPPED_LOGS.get(index).get()), false);
        }
        for (DeferredBlock<Block> wood : ModBlocks.COLORED_WOODS) {
            int index = ModBlocks.COLORED_WOODS.indexOf(wood);
            strippable.add(wood, new Strippable(ModBlocks.COLORED_STRIPPED_WOODS.get(index).get()), false);
        }
    }
}
