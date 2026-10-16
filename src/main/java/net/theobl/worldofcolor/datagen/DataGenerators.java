package net.theobl.worldofcolor.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.entity.ModBannerPatterns;
import net.theobl.worldofcolor.worldgen.ModBiomeModifiers;
import net.theobl.worldofcolor.worldgen.ModTreeFeatures;
import net.theobl.worldofcolor.worldgen.ModVegetationFeatures;
import net.theobl.worldofcolor.worldgen.ModVegetationPlacements;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = WorldOfColor.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherDataRegistryEntries(GatherDataRegistryEntriesEvent event) {
        event.add(Registries.FEATURE, context -> {
            ModTreeFeatures.bootstrap(context);
            ModVegetationFeatures.bootstrap(context);
        })
                .add(Registries.PLACED_FEATURE, ModVegetationPlacements::bootstrap)
                .add(Registries.BANNER_PATTERN, ModBannerPatterns::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
                .lootTable(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK))
                .recipe(ModRecipeProvider::new);
    }
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createBlockAndItemTags(ModBlockTagsProvider::new, ModItemTagsProvider::new);
        event.createProvider(ModBannerPatternTagsProvider::new);
        event.createProvider(ModEntityTypeTagsProvider::new);
        event.createProvider(ModFluidTagsProvider::new);
        event.createProvider(ModFeatureTagsProvider::new);
        event.createProvider(ModDataMapProvider::new);

        event.createProvider(ModModelProvider::new);
        event.createProvider(ModSpriteSourceProvider::new);
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModSoundDefinitionsProvider::new);
        event.createProvider(ModEquipmentAssetProvider::new);
        event.createProvider(ResourceMetadataProvider::new);
    }
}
