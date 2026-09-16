package net.theobl.worldofcolor.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.theobl.worldofcolor.WorldOfColor;

public class ModVegetationPlacements {
    public static final ResourceKey<PlacedFeature> TULIP_FLOWER_FOREST =
            ResourceKey.create(Registries.PLACED_FEATURE, WorldOfColor.asResource("tulip_flower_forest"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> configuredFeatures = context.lookup(Registries.FEATURE);
        Holder<Feature> flowerFlowerForest = configuredFeatures.getOrThrow(ModVegetationFeatures.TULIP_FLOWER_FOREST);
        PlacementUtils.register(
                context,
                TULIP_FLOWER_FOREST,
                flowerFlowerForest,
                CountPlacement.of(3),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(96),
                OffsetPlacement.ofTriangle(6, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        );
    }
}
