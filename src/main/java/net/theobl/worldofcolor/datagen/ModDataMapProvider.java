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
        ModBlocks.COLORED_LEAVES.forEach(block -> compostables.add(block.getId(), new Compostable(0.3F), false));
        ModBlocks.COLORED_SAPLINGS.forEach(block -> compostables.add(block.getId(), new Compostable(0.3F), false));
        ModItems.COLORED_POTATO_PEELS.forEach(block -> compostables.add(block.getId(), new Compostable(0.65F), false));

        final var oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        final var waxables = builder(NeoForgeDataMaps.WAXABLES);
        final var strippable = builder(NeoForgeDataMaps.STRIPPABLES);
        for (DyeColor color : ModUtil.COLORS) {
            waxables.add(ModBlocks.COLORED_COPPER_BLOCKS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_CHISELED_COPPER.pick(color), new Waxable(ModBlocks.COLORED_WAXED_CHISELED_COPPER.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_GRATES.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_GRATES.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_CUT_COPPER.pick(color), new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_CUT_COPPER_STAIRS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_CUT_COPPER_SLABS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_DOORS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_DOORS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_TRAPDOORS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_BULBS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_BULBS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_BARS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_BARS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_CHAINS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_CHAINS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_LANTERNS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_LANTERNS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_CHESTS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_CHESTS.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_COPPER_GOLEM_STATUES.pick(color), new Waxable(ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES.pick(color).get()), false);
            waxables.add(ModBlocks.COLORED_LIGHTNING_RODS.pick(color), new Waxable(ModBlocks.COLORED_WAXED_LIGHTNING_RODS.pick(color).get()), false);

            strippable.add(ModBlocks.COLORED_LOGS.pick(color), new Strippable(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()), false);
            strippable.add(ModBlocks.COLORED_WOODS.pick(color), new Strippable(ModBlocks.COLORED_STRIPPED_WOODS.pick(color).get()), false);

            if (color == DyeColor.PINK) {
                continue;
            }
            int colorIndex = ModUtil.COLORS.indexOf(color);
            DyeColor nextColor = ModUtil.COLORS.get(colorIndex + 1);
            oxidizables.add(ModBlocks.COLORED_COPPER_BLOCKS.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_BLOCKS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_CHISELED_COPPER.pick(color), new Oxidizable(ModBlocks.COLORED_CHISELED_COPPER.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_GRATES.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_GRATES.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_CUT_COPPER.pick(color), new Oxidizable(ModBlocks.COLORED_CUT_COPPER.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_CUT_COPPER_STAIRS.pick(color), new Oxidizable(ModBlocks.COLORED_CUT_COPPER_STAIRS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_CUT_COPPER_SLABS.pick(color), new Oxidizable(ModBlocks.COLORED_CUT_COPPER_SLABS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_DOORS.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_DOORS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_TRAPDOORS.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_TRAPDOORS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_BULBS.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_BULBS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_BARS.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_BARS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_CHAINS.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_CHAINS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_LANTERNS.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_LANTERNS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_CHESTS.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_CHESTS.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_COPPER_GOLEM_STATUES.pick(color), new Oxidizable(ModBlocks.COLORED_COPPER_GOLEM_STATUES.pick(nextColor).get()), false);
            oxidizables.add(ModBlocks.COLORED_LIGHTNING_RODS.pick(color), new Oxidizable(ModBlocks.COLORED_LIGHTNING_RODS.pick(nextColor).get()), false);
        }
    }

    private <T, R> void add(Builder<T, R> builder, Holder<R> object, Holder<R> other, Function<R, T> value) {
        builder.add(object, value.apply(other.value()), false);
    }
}
