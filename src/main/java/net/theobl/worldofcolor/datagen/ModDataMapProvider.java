package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.datamaps.builtin.*;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

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
        ModBlocks.COLORED_LEAVES.values().forEach(block -> compostables.add(block.getId(), new Compostable(0.3F), false));
        final var oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BLOCKS.values()) {
            int index = ModBlocks.COLORED_COPPER_BLOCKS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_BLOCKS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_BLOCKS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CHISELED_COPPER.values()) {
            int index = ModBlocks.COLORED_CHISELED_COPPER.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_CHISELED_COPPER.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_CHISELED_COPPER.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_GRATES.values()) {
            int index = ModBlocks.COLORED_COPPER_GRATES.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_GRATES.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_GRATES.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER.values()) {
            int index = ModBlocks.COLORED_CUT_COPPER.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_CUT_COPPER.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_CUT_COPPER.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_STAIRS.values()) {
            int index = ModBlocks.COLORED_CUT_COPPER_STAIRS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_CUT_COPPER_STAIRS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_CUT_COPPER_STAIRS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_SLABS.values()) {
            int index = ModBlocks.COLORED_CUT_COPPER_SLABS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_CUT_COPPER_SLABS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_CUT_COPPER_SLABS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_DOORS.values()) {
            int index = ModBlocks.COLORED_COPPER_DOORS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_DOORS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_DOORS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_TRAPDOORS.values()) {
            int index = ModBlocks.COLORED_COPPER_TRAPDOORS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_TRAPDOORS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_TRAPDOORS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BULBS.values()) {
            int index = ModBlocks.COLORED_COPPER_BULBS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_BULBS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_BULBS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BARS.values()) {
            int index = ModBlocks.COLORED_COPPER_BARS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_BARS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_BARS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_CHAINS.values()) {
            int index = ModBlocks.COLORED_COPPER_CHAINS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_CHAINS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_CHAINS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_LANTERNS.values()) {
            int index = ModBlocks.COLORED_COPPER_LANTERNS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_LANTERNS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_LANTERNS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_CHESTS.values()) {
            int index = ModBlocks.COLORED_COPPER_CHESTS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_CHESTS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_CHESTS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_GOLEM_STATUES.values()) {
            int index = ModBlocks.COLORED_COPPER_GOLEM_STATUES.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_COPPER_GOLEM_STATUES.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_COPPER_GOLEM_STATUES.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_LIGHTNING_RODS.values()) {
            int index = ModBlocks.COLORED_LIGHTNING_RODS.values().stream().toList().indexOf(block);
            if(index == ModBlocks.COLORED_LIGHTNING_RODS.size() - 1) {
                break;
            }
            oxidizables.add(block, new Oxidizable(ModBlocks.COLORED_LIGHTNING_RODS.get(ModUtil.COLORS.get(index + 1)).get()), false);
        }

        final var waxables = builder(NeoForgeDataMaps.WAXABLES);
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BLOCKS.values()) {
            int index = ModBlocks.COLORED_COPPER_BLOCKS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CHISELED_COPPER.values()) {
            int index = ModBlocks.COLORED_CHISELED_COPPER.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_GRATES.values()) {
            int index = ModBlocks.COLORED_COPPER_GRATES.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_GRATES.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER.values()) {
            int index = ModBlocks.COLORED_CUT_COPPER.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_STAIRS.values()) {
            int index = ModBlocks.COLORED_CUT_COPPER_STAIRS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_SLABS.values()) {
            int index = ModBlocks.COLORED_CUT_COPPER_SLABS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_DOORS.values()) {
            int index = ModBlocks.COLORED_COPPER_DOORS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_DOORS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_TRAPDOORS.values()) {
            int index = ModBlocks.COLORED_COPPER_TRAPDOORS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BULBS.values()) {
            int index = ModBlocks.COLORED_COPPER_BULBS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_BULBS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_BARS.values()) {
            int index = ModBlocks.COLORED_COPPER_BARS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_BARS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_CHAINS.values()) {
            int index = ModBlocks.COLORED_COPPER_CHAINS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_CHAINS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_LANTERNS.values()) {
            int index = ModBlocks.COLORED_COPPER_LANTERNS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_LANTERNS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_CHESTS.values()) {
            int index = ModBlocks.COLORED_COPPER_CHESTS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_CHESTS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_COPPER_GOLEM_STATUES.values()) {
            int index = ModBlocks.COLORED_COPPER_GOLEM_STATUES.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for(DeferredBlock<Block> block : ModBlocks.COLORED_LIGHTNING_RODS.values()) {
            int index = ModBlocks.COLORED_LIGHTNING_RODS.values().stream().toList().indexOf(block);
            waxables.add(block, new Waxable(ModBlocks.COLORED_WAXED_LIGHTNING_RODS.get(ModUtil.COLORS.get(index)).get()), false);
        }

        final var strippable = builder(NeoForgeDataMaps.STRIPPABLES);
        for (DeferredBlock<Block> log : ModBlocks.COLORED_LOGS.values()) {
            int index = ModBlocks.COLORED_LOGS.values().stream().toList().indexOf(log);
            strippable.add(log, new Strippable(ModBlocks.COLORED_STRIPPED_LOGS.get(ModUtil.COLORS.get(index)).get()), false);
        }
        for (DeferredBlock<Block> wood : ModBlocks.COLORED_WOODS.values()) {
            int index = ModBlocks.COLORED_WOODS.values().stream().toList().indexOf(wood);
            strippable.add(wood, new Strippable(ModBlocks.COLORED_STRIPPED_WOODS.get(ModUtil.COLORS.get(index)).get()), false);
        }
    }
}
