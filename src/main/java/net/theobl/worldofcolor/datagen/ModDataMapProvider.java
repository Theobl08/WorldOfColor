package net.theobl.worldofcolor.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.datamaps.builtin.*;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

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
        ModBlocks.COLORED_LEAVES.values().forEach(block -> compostables.add(block.getId(), new Compostable(0.3F), false));
        ModBlocks.COLORED_SAPLINGS.values().forEach(block -> compostables.add(block.getId(), new Compostable(0.3F), false));
        ModItems.COLORED_POTATO_PEELS.values().forEach(block -> compostables.add(block.getId(), new Compostable(0.65F), false));

        final var oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        final var waxables = builder(NeoForgeDataMaps.WAXABLES);
        final var strippable = builder(NeoForgeDataMaps.STRIPPABLES);
        for (DyeColor color : ModUtil.COLORS) {
            waxables.add(ModBlocks.COLORED_COPPER_BLOCKS.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_CHISELED_COPPER.get(color), new Waxable(ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_GRATES.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_GRATES.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_CUT_COPPER.get(color), new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_CUT_COPPER_STAIRS.get(color), new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_CUT_COPPER_SLABS.get(color), new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_DOORS.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_DOORS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_TRAPDOORS.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_BULBS.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_BULBS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_BARS.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_BARS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_CHAINS.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_CHAINS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_LANTERNS.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_LANTERNS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_CHESTS.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_CHESTS.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_GOLEM_STATUES.get(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES.get(color).get()), false);
            waxables.add(ModBlocks.COLORED_LIGHTNING_RODS.get(color), new Waxable(ModBlocks.COLORED_WAXED_LIGHTNING_RODS.get(color).get()), false);

            strippable.add(ModBlocks.COLORED_LOGS.get(color), new Strippable(ModBlocks.COLORED_STRIPPED_LOGS.get(color).get()), false);
            strippable.add(ModBlocks.COLORED_WOODS.get(color), new Strippable(ModBlocks.COLORED_STRIPPED_WOODS.get(color).get()), false);

            if (color == DyeColor.PINK) {
                continue;
            }
            int colorIndex = ModUtil.COLORS.indexOf(color);
            DyeColor nextColor = ModUtil.COLORS.get(colorIndex + 1);
            oxidizables.add(ModBlocks.COLORED_COPPER_BLOCKS.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_BLOCKS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_CHISELED_COPPER.get(color), new Oxidizable(ModBlocks.COLORED_CHISELED_COPPER.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_GRATES.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_GRATES.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_CUT_COPPER.get(color), new Oxidizable(ModBlocks.COLORED_CUT_COPPER.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_CUT_COPPER_STAIRS.get(color), new Oxidizable(ModBlocks.COLORED_CUT_COPPER_STAIRS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_CUT_COPPER_SLABS.get(color), new Oxidizable(ModBlocks.COLORED_CUT_COPPER_SLABS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_DOORS.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_DOORS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_TRAPDOORS.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_TRAPDOORS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_BULBS.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_BULBS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_BARS.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_BARS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_CHAINS.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_CHAINS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_LANTERNS.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_LANTERNS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_CHESTS.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_CHESTS.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_GOLEM_STATUES.get(color), new Oxidizable(ModBlocks.COLORED_COPPER_GOLEM_STATUES.get(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_LIGHTNING_RODS.get(color), new Oxidizable(ModBlocks.COLORED_LIGHTNING_RODS.get(nextColor).get()), false);
        }
    }

    private <T, R> void add(Builder<T, R> builder, Holder<R> object, Holder<R> other, Function<R, T> value) {
        builder.add(object, value.apply(other.value()), false);
    }
}
