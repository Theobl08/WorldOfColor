package net.theobl.worldofcolor.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.theobl.worldofcolor.WorldOfColor;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> TULIP_FLOWER_FOREST_MODIFIER =
            ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, WorldOfColor.asResource("tulip_flower_forest_modifier"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        context.register(
                TULIP_FLOWER_FOREST_MODIFIER,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.FLOWER_FOREST)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.TULIP_FLOWER_FOREST)),
                        GenerationStep.Decoration.VEGETAL_DECORATION)
        );
    }
}
