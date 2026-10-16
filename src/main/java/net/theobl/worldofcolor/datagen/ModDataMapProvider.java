package net.theobl.worldofcolor.datagen;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.datamaps.builtin.*;
import net.theobl.worldofcolor.block.ColoringColorCollection;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ColorCollectionUtil;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

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
        Builder<Oxidizable, Block> oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        Builder<Waxable, Block> waxables = builder(NeoForgeDataMaps.WAXABLES);
        Builder<Transformable, Block> transformable = builder(NeoForgeDataMaps.TRANSFORMABLES);
        Builder<Flammable, Block> flammable = builder(NeoForgeDataMaps.FLAMMABLE);
        for (DyeColor color : ModUtil.COLORS) {
            transformable.add(ModBlocks.COLORED_LOGS.pick(color), Transformable.stripping(ModBlocks.COLORED_LOGS.pick(color).get(), ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()), false);
            transformable.add(ModBlocks.COLORED_WOODS.pick(color), Transformable.stripping(ModBlocks.COLORED_WOODS.pick(color).get(), ModBlocks.COLORED_STRIPPED_WOODS.pick(color).get()), false);
        }
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
        ImmutableBiMap.Builder<DeferredBlock<Block>, DeferredBlock<Block>> waxedBuilder =  ImmutableBiMap.builder(), coloringBuilder =  ImmutableBiMap.builder();
        coloringBlocks.forEach(collection -> collection.zipUnwaxedWaxed(waxedBuilder::put));
        coloringBlocks.forEach(collection -> ColorCollectionUtil.progressMapping(collection.coloring(), coloringBuilder::put));
        waxedBuilder.build().forEach((now, after) -> waxables.add(now, new Waxable(after.get()), false));
//        coloringBuilder.build().forEach((now, after) -> oxidizables.add(now, new Oxidizable(after.get()), false));
        coloringBuilder.build().forEach((now, after) -> add(oxidizables, now, after, Oxidizable::new));

        ModBlocks.COLORED_PLANKS.forEach(block -> registerFlammable(flammable, block, 5, 20));
        ModBlocks.COLORED_SLABS.forEach(block -> registerFlammable(flammable, block, 5, 20));
        ModBlocks.COLORED_FENCE_GATES.forEach(block -> registerFlammable(flammable, block, 5, 20));
        ModBlocks.COLORED_FENCES.forEach(block -> registerFlammable(flammable, block, 5, 20));
        ModBlocks.COLORED_STAIRS.forEach(block -> registerFlammable(flammable, block, 5, 20));
        ModBlocks.COLORED_LOGS.forEach(block -> registerFlammable(flammable, block, 5, 5));
        ModBlocks.COLORED_STRIPPED_LOGS.forEach(block -> registerFlammable(flammable, block, 5, 5));
        ModBlocks.COLORED_STRIPPED_WOODS.forEach(block -> registerFlammable(flammable, block, 5, 5));
        ModBlocks.COLORED_WOODS.forEach(block -> registerFlammable(flammable, block, 5, 5));
        ModBlocks.COLORED_LEAVES.forEach(block -> registerFlammable(flammable, block, 30, 60));
        ModBlocks.CLASSIC_WOOLS.forEach(block -> registerFlammable(flammable, block, 30, 60));
        ModBlocks.CLASSIC_CARPETS.forEach(block -> registerFlammable(flammable, block, 60, 20));
        ModBlocks.COLORED_SHELVES.forEach(block -> registerFlammable(flammable, block, 30, 20));
        registerFlammable(flammable, ModBlocks.LIGHT_GRAY_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.GRAY_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.BLACK_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.BROWN_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.YELLOW_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.LIME_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.GREEN_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.CYAN_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.LIGHT_BLUE_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.BLUE_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.PURPLE_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.MAGENTA_TULIP, 60, 100);
        registerFlammable(flammable, ModBlocks.RGB_WOOL, 30, 60);
        registerFlammable(flammable, ModBlocks.MISSINGNO_WOOL, 30, 60);
        registerFlammable(flammable, ModBlocks.MISSINGNO_CARPET, 60, 20);
    }

    private <T, R> void add(Builder<T, R> builder, Holder<R> object, Holder<R> other, Function<R, T> value) {
        builder.add(object, value.apply(other.value()), false);
    }

    private void registerFlammable(Builder<Flammable, Block> builder, Holder<Block> object, int igniteOdds, int burnOdds) {
        builder.add(object, new Flammable(igniteOdds, burnOdds), false);
    }
}
