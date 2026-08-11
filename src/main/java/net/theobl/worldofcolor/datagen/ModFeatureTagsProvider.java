package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FeatureTagsProvider;
import net.minecraft.tags.FeatureTags;
import net.theobl.worldofcolor.worldgen.ModVegetationFeatures;

import java.util.concurrent.CompletableFuture;

public class ModFeatureTagsProvider extends FeatureTagsProvider {
    public ModFeatureTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(FeatureTags.CAN_SPAWN_FROM_BONE_MEAL).add(ModVegetationFeatures.TULIP_FLOWER_FOREST);
    }
}
