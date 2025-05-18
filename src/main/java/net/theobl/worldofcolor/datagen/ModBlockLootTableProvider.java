package net.theobl.worldofcolor.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.block.ModBlocks;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        for (DeferredHolder<Block, ? extends Block> block : ModBlocks.BLOCKS.getEntries()) {
            if(block.get() instanceof SlabBlock)
                this.add(block.get(), this::createSlabItemTable);
            else if (block.get() instanceof DoorBlock)
                this.add(block.get(), this::createDoorTable);
            else if (block.get() instanceof LeavesBlock) {
                this.add(block.get(), createLeavesDrops(block.get(), block.get(), NORMAL_LEAVES_SAPLING_CHANCES));

            } else
                this.dropSelf(block.get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
