package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.worldgen.ModBiomeModifiers;
import net.theobl.worldofcolor.worldgen.ModTreeFeatures;
import net.theobl.worldofcolor.worldgen.ModVegetationFeatures;
import net.theobl.worldofcolor.worldgen.ModVegetationPlacements;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackBuiltInEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, context -> {
                ModTreeFeatures.bootstrap(context);
                ModVegetationFeatures.bootstrap(context);
            })
            .add(Registries.PLACED_FEATURE, ModVegetationPlacements::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public ModDatapackBuiltInEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(WorldOfColor.MODID));
    }
}
