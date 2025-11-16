package net.theobl.worldofcolor.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.block.ColoredDecoratedPotBlock;
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
                int index = ModBlocks.COLORED_LEAVES.indexOf(block);
                if(index != -1)
                    this.add(block.get(), createLeavesDrops(block.get(), ModBlocks.COLORED_SAPLINGS.get(index).get(), NORMAL_LEAVES_SAPLING_CHANCES));

            } else if(block.get() instanceof FlowerPotBlock flowerPotBlock && block.get().defaultBlockState() != flowerPotBlock.getEmptyPot().defaultBlockState()) {
                this.dropColoredPottedContents(block.get());
            } else if (block.get() instanceof DecoratedPotBlock) {
                this.add(block.get(), this::createDecoratedPotTable);
            } else if (block.is(ModBlocks.RGB_STAINED_GLASS.getId()) || block.is(ModBlocks.RGB_STAINED_GLASS_PANE.getId())) {
                this.dropWhenSilkTouch(block.get());
            } else if(!(block.get() instanceof AbstractCandleBlock))
                this.dropSelf(block.get());
        }
        this.add(ModBlocks.RGB_CANDLE.get(), this::createCandleDrops);
        this.add(ModBlocks.RGB_CANDLE_CAKE.get(), createCandleCakeDrops(ModBlocks.RGB_CANDLE.get()));
    }

    private LootTable.Builder createDecoratedPotTable(Block block) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        DynamicLoot.dynamicEntry(DecoratedPotBlock.SHERDS_DYNAMIC_DROP_ID)
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DecoratedPotBlock.CRACKED, true))
                                                )
                                                .otherwise(
                                                        LootItem.lootTableItem(block)
                                                                .apply(
                                                                        CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                                                                .include(DataComponents.POT_DECORATIONS)
                                                                )
                                                )
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(DyeItem.byColor(((ColoredDecoratedPotBlock) block).getColor()))
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DecoratedPotBlock.CRACKED, true))
                                                )
                                )
                );
    }

    protected LootTable.Builder createColoredPotFlowerItemTable(FlowerPotBlock fullPot) {
        ItemLike item = fullPot.getPotted();
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                fullPot.getEmptyPot(), LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(fullPot.getEmptyPot()))
                        )
                )
                .withPool(this.applyExplosionCondition(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item))));
    }

    protected void dropColoredPottedContents(Block flowerPot) {
        this.add(flowerPot, block -> this.createColoredPotFlowerItemTable((FlowerPotBlock)block));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
