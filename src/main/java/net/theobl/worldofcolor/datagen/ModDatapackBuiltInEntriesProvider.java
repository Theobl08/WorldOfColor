package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.worldgen.ModTreeFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackBuiltInEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModTreeFeatures::bootstrap);

    public ModDatapackBuiltInEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(WorldOfColor.MODID));
    }
}
