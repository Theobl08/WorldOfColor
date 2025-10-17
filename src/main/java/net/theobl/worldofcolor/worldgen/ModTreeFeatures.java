package net.theobl.worldofcolor.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.ArrayList;
import java.util.List;

public class ModTreeFeatures {
    public static final List<ResourceKey<ConfiguredFeature<?, ?>>> COLORED_TREES = registerColoredKeys();

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            register(context, COLORED_TREES.get(index), Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.COLORED_LOGS.get(index).get()),
                    new StraightTrunkPlacer(4, 2, 0),

                    BlockStateProvider.simple(ModBlocks.COLORED_LEAVES.get(index).get()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),

                    new TwoLayersFeatureSize(1, 0, 1)).build());
        }
    }

    public static List<ResourceKey<ConfiguredFeature<?, ?>>> registerColoredKeys() {
        List<ResourceKey<ConfiguredFeature<?, ?>>> keys = new ArrayList<>();
        ModUtil.COLORS.forEach(color -> keys.add(registerKey(color.getName())));
        return keys;
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, WorldOfColor.asResource(name));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
