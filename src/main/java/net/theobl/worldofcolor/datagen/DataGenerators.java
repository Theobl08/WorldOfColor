package net.theobl.worldofcolor.datagen;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = WorldOfColor.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createBlockAndItemTags(ModBlockTagsProvider::new, ModItemTagsProvider::new);
        event.createProvider(ModEntityTypeTagsProvider::new);
        event.createProvider((output, lookupProvider) -> new LootTableProvider(output, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        event.createProvider(ModDataMapProvider::new);
        event.createProvider(ModDatapackBuiltInEntriesProvider::new);

        event.createProvider(ModModelProvider::new);
        event.createProvider(ModSpriteSourceProvider::new);
        event.createProvider(ModLanguageProvider::new);
    }
}
