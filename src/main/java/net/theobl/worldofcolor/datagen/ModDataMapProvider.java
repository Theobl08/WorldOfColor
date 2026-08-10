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
        final Builder<Compostable, Item> compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        ModBlocks.COLORED_LEAVES.forEach(block -> compostables.add(block.getId(), new Compostable(0.3F), false));
        ModBlocks.COLORED_SAPLINGS.forEach(block -> compostables.add(block.getId(), new Compostable(0.3F), false));
        ModItems.COLORED_POTATO_PEELS.forEach(block -> compostables.add(block.getId(), new Compostable(0.65F), false));

        final var oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        final var waxables = builder(NeoForgeDataMaps.WAXABLES);
        final var strippable = builder(NeoForgeDataMaps.STRIPPABLES);
        for (DyeColor color : ModUtil.COLORS) {
            strippable.add(ModBlocks.COLORED_LOGS.pick(color), new Strippable(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()), false);
            strippable.add(ModBlocks.COLORED_WOODS.pick(color), new Strippable(ModBlocks.COLORED_STRIPPED_WOODS.pick(color).get()), false);
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
    }

    private <T, R> void add(Builder<T, R> builder, Holder<R> object, Holder<R> other, Function<R, T> value) {
        builder.add(object, value.apply(other.value()), false);
    }
}
