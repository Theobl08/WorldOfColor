package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.tags.BannerPatternTags;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.entity.ModBannerPatterns;

import java.util.concurrent.CompletableFuture;

public class ModBannerPatternTagsProvider extends BannerPatternTagsProvider {
    public ModBannerPatternTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(BannerPatternTags.NO_ITEM_REQUIRED)
                .add(ModBannerPatterns.BOTTOM_LEFT)
                .add(ModBannerPatterns.BOTTOM_RIGHT)
                .add(ModBannerPatterns.TOP_LEFT)
                .add(ModBannerPatterns.TOP_RIGHT);
    }
}
