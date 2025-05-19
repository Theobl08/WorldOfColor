package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
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
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_DOORS.get(index + 1).get()), false);
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
    }
}
