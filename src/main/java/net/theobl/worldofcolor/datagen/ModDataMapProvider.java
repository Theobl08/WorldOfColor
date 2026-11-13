package net.theobl.worldofcolor.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.*;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ColorCollectionUtil;

import java.util.List;
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
        ColorCollection.zipApply(ModBlocks.COLORED_LOGS, ModBlocks.COLORED_STRIPPED_LOGS, this::registerStrippable);
        ColorCollection.zipApply(ModBlocks.COLORED_WOODS, ModBlocks.COLORED_STRIPPED_WOODS, this::registerStrippable);
        var coloringBlocks = List.of(
                ModBlocks.COLORED_COPPER_BLOCKS,
                ModBlocks.COLORED_CHISELED_COPPER,
                ModBlocks.COLORED_COPPER_GRATES,
                ModBlocks.COLORED_CUT_COPPER,
                ModBlocks.COLORED_CUT_COPPER_STAIRS,
                ModBlocks.COLORED_CUT_COPPER_SLABS,
                ModBlocks.COLORED_COPPER_DOORS,
                ModBlocks.COLORED_COPPER_TRAPDOORS,
                ModBlocks.COLORED_COPPER_BULBS,
                ModBlocks.COLORED_COPPER_BARS,
                ModBlocks.COLORED_COPPER_CHAINS,
                ModBlocks.COLORED_COPPER_LANTERNS,
                ModBlocks.COLORED_COPPER_CHESTS,
                ModBlocks.COLORED_COPPER_GOLEM_STATUES,
                ModBlocks.COLORED_LIGHTNING_RODS
        );
        coloringBlocks.forEach(collection -> collection.zipUnwaxedWaxed(this::registerWaxable));
        coloringBlocks.forEach(collection -> ColorCollectionUtil.progressMapping(collection.coloring(), this::registerOxidizable));

        ModBlocks.COLORED_PLANKS.forEach(block -> registerFlammable(block, 5, 20));
        ModBlocks.COLORED_SLABS.forEach(block -> registerFlammable(block, 5, 20));
        ModBlocks.COLORED_FENCE_GATES.forEach(block -> registerFlammable(block, 5, 20));
        ModBlocks.COLORED_FENCES.forEach(block -> registerFlammable(block, 5, 20));
        ModBlocks.COLORED_STAIRS.forEach(block -> registerFlammable(block, 5, 20));
        ModBlocks.COLORED_LOGS.forEach(block -> registerFlammable(block, 5, 5));
        ModBlocks.COLORED_STRIPPED_LOGS.forEach(block -> registerFlammable(block, 5, 5));
        ModBlocks.COLORED_STRIPPED_WOODS.forEach(block -> registerFlammable(block, 5, 5));
        ModBlocks.COLORED_WOODS.forEach(block -> registerFlammable(block, 5, 5));
        ModBlocks.COLORED_LEAVES.forEach(block -> registerFlammable(block, 30, 60));
        ModBlocks.CLASSIC_WOOLS.forEach(block -> registerFlammable(block, 30, 60));
        ModBlocks.CLASSIC_CARPETS.forEach(block -> registerFlammable(block, 60, 20));
        ModBlocks.COLORED_SHELVES.forEach(block -> registerFlammable(block, 30, 20));
        registerFlammable(ModBlocks.LIGHT_GRAY_TULIP, 60, 100);
        registerFlammable(ModBlocks.GRAY_TULIP, 60, 100);
        registerFlammable(ModBlocks.BLACK_TULIP, 60, 100);
        registerFlammable(ModBlocks.BROWN_TULIP, 60, 100);
        registerFlammable(ModBlocks.YELLOW_TULIP, 60, 100);
        registerFlammable(ModBlocks.LIME_TULIP, 60, 100);
        registerFlammable(ModBlocks.GREEN_TULIP, 60, 100);
        registerFlammable(ModBlocks.CYAN_TULIP, 60, 100);
        registerFlammable(ModBlocks.LIGHT_BLUE_TULIP, 60, 100);
        registerFlammable(ModBlocks.BLUE_TULIP, 60, 100);
        registerFlammable(ModBlocks.PURPLE_TULIP, 60, 100);
        registerFlammable(ModBlocks.MAGENTA_TULIP, 60, 100);
        registerFlammable(ModBlocks.RGB_WOOL, 30, 60);
        registerFlammable(ModBlocks.MISSINGNO_WOOL, 30, 60);
        registerFlammable(ModBlocks.MISSINGNO_CARPET, 60, 20);
    }

    private void registerStrippable(Holder<Block> original, Holder<Block> stripped) {
        Builder<Transformable, Block> builder = builder(NeoForgeDataMaps.TRANSFORMABLES);
        builder.add(original, Transformable.stripping(original.value(), stripped.value()), false);
    }

    private void registerFlammable(Holder<Block> object, int igniteOdds, int burnOdds) {
        Builder<Flammable, Block> builder = builder(NeoForgeDataMaps.FLAMMABLE);
        builder.add(object, new Flammable(igniteOdds, burnOdds), false);
    }

    private void registerWaxable(Holder<Block> original, Holder<Block> waxed) {
        Builder<Waxable, Block> builder = builder(NeoForgeDataMaps.WAXABLES);
        builder.add(original, new Waxable(waxed.value()), false);
    }

    private void registerOxidizable(Holder<Block> now, Holder<Block> after) {
        Builder<Oxidizable, Block> builder = builder(NeoForgeDataMaps.OXIDIZABLES);
        builder.add(now, new Oxidizable(after.value()), false);
    }
}
