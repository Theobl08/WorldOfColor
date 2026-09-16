package net.theobl.worldofcolor.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import  net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;

import java.util.List;

public class ModVegetationFeatures {
    /// A complementary of {@link VegetationFeatures#FLOWER_FLOWER_FOREST}
    public static final ResourceKey<Feature> TULIP_FLOWER_FOREST =
            ResourceKey.create(Registries.FEATURE, WorldOfColor.asResource("tulip_flower_forest"));

    public static void bootstrap(BootstrapContext<Feature> context) {
        context.register(
                TULIP_FLOWER_FOREST,
                new SimpleBlockFeature(
                        new NoiseProvider(
                                2345L,
                                NormalNoise.createParity(0, 1.0),
                                0.020833334F,
                                List.of(
                                        ModBlocks.LIGHT_GRAY_TULIP.get().defaultBlockState(),
                                        ModBlocks.GRAY_TULIP.get().defaultBlockState(),
                                        ModBlocks.BLACK_TULIP.get().defaultBlockState(),
                                        ModBlocks.BROWN_TULIP.get().defaultBlockState(),
                                        ModBlocks.YELLOW_TULIP.get().defaultBlockState(),
                                        ModBlocks.LIME_TULIP.get().defaultBlockState(),
                                        ModBlocks.GREEN_TULIP.get().defaultBlockState(),
                                        ModBlocks.CYAN_TULIP.get().defaultBlockState(),
                                        ModBlocks.LIGHT_BLUE_TULIP.get().defaultBlockState(),
                                        ModBlocks.BLUE_TULIP.get().defaultBlockState(),
                                        ModBlocks.PURPLE_TULIP.get().defaultBlockState(),
                                        ModBlocks.MAGENTA_TULIP.get().defaultBlockState()
                                )
                        )
                )
        );
    }
}
