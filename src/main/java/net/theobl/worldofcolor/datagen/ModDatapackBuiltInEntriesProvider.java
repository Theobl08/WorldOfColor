package net.theobl.worldofcolor.datagen;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.theobl.worldofcolor.block.entity.ModBannerPatterns;
import net.theobl.worldofcolor.worldgen.ModBiomeModifiers;
import net.theobl.worldofcolor.worldgen.ModTreeFeatures;
import net.theobl.worldofcolor.worldgen.ModVegetationFeatures;
import net.theobl.worldofcolor.worldgen.ModVegetationPlacements;

import java.util.Collections;
import java.util.List;

public class ModDatapackBuiltInEntriesProvider {
    protected static final RegistrySetBuilder WORLD_BUILDER = new RegistrySetBuilder()
            .add(Registries.FEATURE, context -> {
                ModTreeFeatures.bootstrap(context);
                ModVegetationFeatures.bootstrap(context);
            })
            .add(Registries.PLACED_FEATURE, ModVegetationPlacements::bootstrap)
            .add(Registries.BANNER_PATTERN, ModBannerPatterns::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
    protected static final RegistrySetBuilder RELOADABLE_BUILDER = new RegistrySetBuilder()
            .add(Registries.LOOT_TABLE, new LootTableProvider(Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK))))
            .add(ModRecipeProvider.create());
}
