package net.theobl.worldofcolor.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BlockStateProviders;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

public class ModTreeFeatures {
    public static final ColorCollection<ResourceKey<Feature>> COLORED_TREES = ColorCollection.NAMES
            .map(ModTreeFeatures::registerKey);

    public static void bootstrap(BootstrapContext<Feature> context) {
        HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
        Holder<BlockStateProvider> belowTrunkProvider = blockStateProviders.getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE);
        for (DyeColor color : ModUtil.COLORS) {
            context.register(COLORED_TREES.pick(color), new TreeFeature.Builder(
                    BlockStateProvider.of(ModBlocks.COLORED_LOGS.pick(color).get()),
                    new StraightTrunkPlacer(4, 2, 0),

                    BlockStateProvider.of(ModBlocks.COLORED_LEAVES.pick(color).get()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),

                    new TwoLayersFeatureSize(1, 0, 1),
                    belowTrunkProvider).build());
        }
    }

    public static ResourceKey<Feature> registerKey(String name) {
        return ResourceKey.create(Registries.FEATURE, WorldOfColor.asResource(name));
    }
}
